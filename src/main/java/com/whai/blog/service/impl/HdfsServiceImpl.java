package com.whai.blog.service.impl;

import com.whai.blog.service.IHdfsService;
import com.whai.blog.utils.exception.HdfsDisableException;
import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.*;
import org.apache.hadoop.io.IOUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.net.ConnectException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.*;
import java.util.regex.Pattern;

@Service
public class HdfsServiceImpl implements IHdfsService {

    @Value("${hdfs.enable}")
    private boolean hdfsEnable;
    @Value("${hdfs.path}")
    private String path;
    @Value("${hdfs.username}")
    private String username;

    private final int bufferSize = 1024*1024*64;//缓存

    //获取HDFS配置信息
    public Configuration getConfiguration() throws IOException {
        Configuration configuration = new Configuration();

        configuration.set("fs.defaultFS",path);
//        configuration.set("dfs.replication", "1");
        ClassPathResource coreSite = new ClassPathResource("core-site.xml");
        ClassPathResource hdfsSite = new ClassPathResource("hdfs-site.xml");

        configuration.addResource(coreSite.getURL());
        configuration.addResource(hdfsSite.getURL());

        //其他参数
        return configuration;
    }

    //获取HDFS文件系统对象
    private FileSystem getFileSystem() throws URISyntaxException, IOException, InterruptedException {
        //客户端去操作hdfs时是有一个用户身份的，默认情况下hdfs客户端api会从
        //jvm中获取一个参数作为自己的用户身份
        //也可以在构造客户端fs对象时，通过参数传递进去
        //路径，配置项，用户名
        if (!hdfsEnable){
            throw new HdfsDisableException("hdfs未被允许使用，请启动hdfs后重试");
        }
        FileSystem fileSystem = null;

        fileSystem = FileSystem.get(new URI(path), getConfiguration(), username);

        return fileSystem;
    }

    @Override
    public boolean switchHdfs() throws IOException, URISyntaxException, InterruptedException {
        try {
            //测试可以开关
            FileSystem fileSystem = FileSystem.get(new URI(path), getConfiguration(), username);
            fileSystem.exists(new Path("/"));
        } catch (ConnectException e) {
            this.hdfsEnable = false;
            e.printStackTrace();
            throw new HdfsDisableException("hdfs远程服务未能连接：" + e.getMessage());
        }
        hdfsEnable = !hdfsEnable;
        return hdfsEnable;
    }

    /**
     * path下文件状态
     * @param path
     * @return  [
     * {owner=root, duplicates=1, fileName=README.txt, modifyTime=1681640268613, isFile=true, size=1366, relativePath=/README.txt, filePath=hdfs://42.192.130.83:9000/README.txt, parentPath=/, rights=rw-r--r--, group=supergroup}
     * ,
     * {owner=root, duplicates=0, fileName=hadoop-2.7.4, modifyTime=1681649670546, isFile=false, size=0, relativePath=/hadoop-2.7.4, filePath=hdfs://42.192.130.83:9000/hadoop-2.7.4, parentPath=/, rights=rwxr-xr-x, group=supergroup}
     * ]
     * @throws InterruptedException
     * @throws IOException
     * @throws URISyntaxException
     */
    @Override
    public List<Map<String, String>> listStatus(String path) throws InterruptedException, IOException, URISyntaxException {
        FileSystem fs = getFileSystem();
        //目标路径
        Path srcPath = new Path(path);

        List<Map<String,String>> returnList = new ArrayList<>();
        try {
            FileStatus[] fileStatuses = fs.listStatus(srcPath); //调用listStatus方法

            if (fileStatuses == null || fileStatuses.length <= 0) {
                return null;
            }

            for (FileStatus file : fileStatuses) {
                Map<String, String> map = fileStatusToMap(file);
                returnList.add(map);
            }
            fs.close();
        } catch (ConnectException e) {
            this.hdfsEnable = false;
            e.printStackTrace();
            throw new HdfsDisableException("hdfs远程服务未能连接：" + e.getMessage());
        }

        return returnList;
    }

    //封装Map->fileStatus
    private Map<String, String> fileStatusToMap(FileStatus file) {
        Map<String, String> map = new HashMap<>();
        Path p = file.getPath();
        map.put("fileName", p.getName());
        String filePath = p.toUri().toString();
        map.put("filePath", filePath);                   //hdfs://node1:9000/idea/a.txt
        String relativePath = filePath.substring(this.path.length());   // /idea/a.txt

        map.put("relativePath", relativePath); //相对路径 ->当前路径

        map.put("parentPath", p.getParent().toUri().toString().substring(this.path.length()));      // /idea

        map.put("owner", file.getOwner());
        map.put("group", file.getGroup());
        map.put("isFile", file.isFile() + "");
        map.put("duplicates", file.getReplication() + ""); //副本
        map.put("size", String.valueOf(file.getLen()));
        map.put("rights", file.getPermission().toString());
        map.put("modifyTime", String.valueOf(file.getModificationTime()));
        return map;
    }



    @Override
    public boolean existFile(String path) throws InterruptedException, IOException, URISyntaxException {
        FileSystem fileSystem = getFileSystem();
        boolean exists = fileSystem.exists(new Path(path));
        return exists;
    }

    @Override
    public boolean mkdir(String path) throws InterruptedException, IOException, URISyntaxException {
        FileSystem fs = getFileSystem();
        //目标路径
        Path srcPath = new Path(path);
        boolean isOk = fs.mkdirs(srcPath);
        fs.close();
        return isOk;
    }


    @Override
    public List<Map<String, String>> listStatus(int type) throws InterruptedException, IOException, URISyntaxException {
        //查找全盘所有文件
        String path = "/";
        //目标路径
        Path srcPath = new Path(path);
        List<Map<String, String>> returnList = new ArrayList<>();
        String reg = null;
        if (type == 1) {
            reg = ".+(.jpeg|.jpg|.png|.bmp|.gif)$";
        } else if (type == 2) {
            reg = ".+(.txt|.rtf|.doc|.xls|.xlsx|.html|.htm|.xml)$";
        } else if (type == 3) {
            reg = ".+(.mp4|.avi|.wmv)$";
        } else if (type == 4) {
            reg = ".+(.mp3|.wav)$";
        } else if (type == 5) {
            reg = "^\\S+\\.*$";
        }
        Pattern pattern = Pattern.compile(reg, Pattern.CASE_INSENSITIVE);
        search(srcPath, returnList, pattern);
        return returnList;
    }

    /**
     *   递归查找
     * @param srcPath
     * @param returnList
     * @param pattern
     * @throws InterruptedException
     * @throws IOException
     * @throws URISyntaxException
     */
    private void search(Path srcPath, List<Map<String, String>> returnList, Pattern pattern) throws InterruptedException, IOException, URISyntaxException {
        FileSystem fs = getFileSystem();
        FileStatus[] fileStatuses = fs.listStatus(srcPath);
        if (fileStatuses != null && fileStatuses.length > 0) {
            for (FileStatus file : fileStatuses) {
                boolean result = file.isFile();
                if (!result) {
                    //是目录 则递归
                    search(file.getPath(), returnList, pattern);
                } else {
                    //是文件 ,则判断类型
                    boolean b = pattern.matcher(file.getPath().getName()).find();
                    if (b) {
                        Map<String, String> map = this.fileStatusToMap(file);
                        returnList.add(map);
                    }
                }
            }
        }
    }

    @Override
    public boolean renameFile(String oldName, String newName) throws InterruptedException, IOException, URISyntaxException {
        FileSystem fs = getFileSystem();
        //源文件目标路径
        Path oldPath = new Path(oldName);
        //重命名目标路径
        Path newPath = new Path(newName);
        boolean isOk = fs.rename(oldPath,newPath);
        fs.close();
        return isOk;
    }

    @Override
    public boolean deleteFile(String path) throws InterruptedException, IOException, URISyntaxException {
        FileSystem fs = getFileSystem();
        Path srcPath = new Path(path);
        boolean isOk = fs.delete(srcPath,true);//后面的boolean值是否递归删除目录
        fs.close();
        return isOk;
    }

    @Override
    public Map<String, String> getFileInfo(String path) throws InterruptedException, IOException, URISyntaxException {
        FileSystem fs = getFileSystem();
        //目标路径
        Path srcPath = new Path(path);
        FileStatus fileStatus = fs.getFileStatus(srcPath);
        return  fileStatusToMap(fileStatus);
    }

    @Override
    public void copyFile(String sourcePath, String targetPath) throws Exception {
        FileSystem fs = getFileSystem();
        //原文件路径
        Path oldPath = new Path(sourcePath);
        //目标路径
        Path newPath = new Path(targetPath);
        FSDataInputStream inputStream = null;
        FSDataOutputStream outputStream = null;
        try {
            inputStream = fs.open(oldPath);
            outputStream = fs.create(newPath);
            IOUtils.copyBytes(inputStream, outputStream, bufferSize, false);
        } finally {
            inputStream.close();
            outputStream.close();
            fs.close();
        }
    }

    @Override
    public ResponseEntity<InputStreamResource> downloadFile(String path,HttpServletResponse response) throws InterruptedException, IOException, URISyntaxException {

        FileSystem fs = getFileSystem();

        Path inputPath = new Path(path);
        String fileName = path.substring(path.lastIndexOf("/") + 1);
        FSDataInputStream inputStream = fs.open(inputPath);

        //http的响应协议
//            HttpHeaders headers = new HttpHeaders();
//            headers.add("Cache-Control","no-cache,no-store,must-revalidate");//	指定请求和响应遵循的缓存机制
//            //文件下载的方式：Content-Disposition:xxx.txt
//            headers.add("Content-Disposition",String.format("attachment;filename=\"%s\"",fileName));
//            headers.add("Pragma","no-cache");   //	用来包含实现特定的指令
//            headers.add("EExpires","0");
//            headers.add("Content-Language","UTF-8");
        //最终这句：让文件内容以流的形式输出

//        response.setHeader("Cache-Control","no-cache,no-store,must-revalidate");//	指定请求和响应遵循的缓存机制
//        response.setHeader("Content-Disposition", String.format("attachment;filename=\"%s\"", fileName));
//        response.setHeader("Pragma", "no-cache");
//        response.setHeader("EExpires", "0");
//        response.setHeader("Content-Language","UTF-8");

//            return ResponseEntity.ok().headers(headers).contentLength(testBytes.length)
//                    .contentType(MediaType.parseMediaType("application/octet-stream")).body(new InputStreamResource(inputStream));

//        BufferedOutputStream bos = new BufferedOutputStream(response.getOutputStream());
//        IOUtils.copyBytes(inputStream,bos,4096);

        byte[] testBytes = new byte[inputStream.available()];
        //http的响应协议
        HttpHeaders headers = new HttpHeaders();
        headers.add("Cache-Control","no-cache,no-store,must-revalidate");//	指定请求和响应遵循的缓存机制
        //文件下载的方式：Content-Disposition:xxx.txt
        headers.add("Content-Disposition",String.format("attachment;filename=\"%s\"",fileName));
        headers.add("Pragma","no-cache");   //	用来包含实现特定的指令
        headers.add("EExpires","0");
        headers.add("Content-Language","UTF-8");
        //最终这句：让文件内容以流的形式输出

        return ResponseEntity.ok().headers(headers).contentLength(testBytes.length)
                .contentType(MediaType.parseMediaType("application/octet-stream")).body(new InputStreamResource(inputStream));


    }

//    @Override
//    public byte[] openFileToBytes(String pathh) throws InterruptedException, IOException, URISyntaxException {
//        return new byte[0];
//    }

    @Override
    public void uploadFile(String path, String uploadPath) throws InterruptedException, IOException, URISyntaxException {
        FileSystem fs = getFileSystem();
        //上传路径
        Path clientpath = new Path(path);
        //目标路径
        Path serverPath = new Path(uploadPath);
        //调用文件系统的文件赋值方法，第一个参数是否删除源文件
        //true为删除 默认为false

        fs.copyFromLocalFile(false,clientpath,serverPath);
        fs.close();
    }

//    @Override
//    public String readFile(String path) throws InterruptedException, IOException, URISyntaxException {
//        return null;
//    }

//    @Override
//    public void createFile(String path, MultipartFile file) throws Exception {
//
//    }

    @Override
    public BlockLocation[] getFileBlockLocations(String path) throws Exception {
        FileSystem fs = getFileSystem();
        //上传时默认当前路径，后面自动拼接文件的目录
        Path srcPath = new Path(path);
        FileStatus fileStatus =fs.getFileStatus(srcPath);
        return fs.getFileBlockLocations(fileStatus,0,fileStatus.getLen());
    }

//    @Override
//    public ResponseEntity<byte[]> downloadDirectory(String path, String fileName) throws IOException {
//        return null;
//    }

    /**
     * 获取配置信息
     * @return
     * @throws InterruptedException
     * @throws IOException
     * @throws URISyntaxException
     */
    @Override
    public Map<String, String> getConfigurationInfoAsMap() throws InterruptedException, IOException, URISyntaxException {
        FileSystem fileSystem = getFileSystem();
        Configuration conf = fileSystem.getConf();
        Iterator<Map.Entry<String,String>> iterator = (  Iterator<Map.Entry<String,String>>)conf.iterator();
        Map<String,String> map = new HashMap<String, String>();
        while(iterator.hasNext()){
            Map.Entry<String,String> entry = iterator.next();
            map.put(entry.getKey(),entry.getValue());
        }
        return map;
    }

    @Override
    public boolean getEnable() throws IOException, URISyntaxException, InterruptedException {
        try {
            //测试可以开关
            FileSystem fileSystem = FileSystem.get(new URI(path), getConfiguration(), username);
            fileSystem.exists(new Path("/"));
        } catch (ConnectException e) {
            this.hdfsEnable = false;
            e.printStackTrace();
            throw new HdfsDisableException("hdfs远程服务未能连接：" + e.getMessage());
        }
        return hdfsEnable;
    }
}
