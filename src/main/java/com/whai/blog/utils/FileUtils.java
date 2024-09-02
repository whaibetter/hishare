package com.whai.blog.utils;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.FileExistsException;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

@Slf4j
public class FileUtils {


    /**
     *
     * @param path 完整上传路径
     * @param multipartFile 文件
     * @return 完整的文件路径
     * @throws IOException
     */
    public static String uploadUtils(String path ,MultipartFile multipartFile) throws IOException {

        log.info("上传文件目录："+ path + "  ----------  文件名称：" + multipartFile.getOriginalFilename());

        File file = new File(path);

        if (!file.exists()){
            file.mkdirs();
        }



        //原生名称，包括后缀
        String originalFilename = multipartFile.getOriginalFilename();
        //文件上传位置+文件名称
        String pathname = path +"/" +  originalFilename;

        if (new File(pathname).exists()){
            throw new FileExistsException("存在相同文件");
        }

        File output = new File(pathname);
        //拷贝到服务器
        FileCopyUtils.copy(multipartFile.getInputStream(), new FileOutputStream(output));

        return pathname;
    }

    /**
     * 删除绝对路径下的文件
     * @param path
     * @return
     */
    public static AjaxResult deleteFile(String path){
        File file = new File(path);
        if (file.exists()) {
            //文件存在
            file.delete();
            return AjaxResult.success("删除成功");
        }else {
            return new AjaxResult(404, "文件不存在，请刷新重试！");
        }
    }

}
