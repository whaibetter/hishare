package com.whai.blog.controller.admin;

import com.whai.blog.service.IHdfsService;
import com.whai.blog.utils.AjaxResult;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.apache.hadoop.fs.BlockLocation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import javax.validation.constraints.NotNull;
import java.io.IOException;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Map;

/**
 * 专门用于控制hdfs文件系统
 */
@RequestMapping("/admin/hdfs")
@Validated
@RestController
public class HdfsController {

    @Autowired
    IHdfsService hdfsService;

    /**
     * 文件列表
     * @param path 寻找的路径
     * @return
     * @throws IOException
     * @throws URISyntaxException
     * @throws InterruptedException
     */
    @GetMapping("hdfsList")
    @ApiOperation("文件列表")
    public AjaxResult getHdfsFileList(
            @ApiParam(value = "路径", defaultValue = "/")
            @RequestParam(name = "path")
                    String path) throws IOException, URISyntaxException, InterruptedException {

        List<Map<String, String>> maps = hdfsService.listStatus(path);

        return AjaxResult.success("success", maps);
    }

    /**
     * 文件列表
     * @return
     */
    @PostMapping("switchHdfs")
    @ApiOperation("开关hdfs")
    public AjaxResult switchHdfs() throws IOException, URISyntaxException, InterruptedException {
        boolean b = hdfsService.switchHdfs();
        return AjaxResult.success("现在enable Hdfs状态为："+b );
    }


    /**
     *
     * @param path
     * @return
     * @throws IOException
     * @throws URISyntaxException
     * @throws InterruptedException
     */
    @GetMapping("exist")
    @ApiOperation("文件是否存在")
    public AjaxResult existFile(
            @RequestParam(name = "path")
                    String path) throws IOException, URISyntaxException, InterruptedException {

        boolean b = hdfsService.existFile(path);

        return AjaxResult.success(b ? "success" : "failure");

    }

    /**
     *
     * @param path
     * @return
     * @throws IOException
     * @throws URISyntaxException
     * @throws InterruptedException
     */
    @GetMapping("hdfsFile")
    @ApiOperation("文件信息")
    public AjaxResult getFileInfo(
            @NotNull
            @RequestParam(name = "path")
                    String path) throws IOException, URISyntaxException, InterruptedException {

        Map<String, String> fileInfo = hdfsService.getFileInfo(path);

        return AjaxResult.success("success",fileInfo);
    }

    /**
     *
     * @return
     * @throws IOException
     * @throws URISyntaxException
     * @throws InterruptedException
     */
    @GetMapping("hdfsConf")
    @ApiOperation("hdfs系统信息")
    public AjaxResult getHdfsConfiguration() throws IOException, URISyntaxException, InterruptedException {

        Map<String, String> configurationInfoAsMap = hdfsService.getConfigurationInfoAsMap();

        return AjaxResult.success("success",configurationInfoAsMap);
    }


    /**
     *
     * @return
     * @throws IOException
     * @throws URISyntaxException
     * @throws InterruptedException
     */
    @PostMapping("mkdir")
    @ApiOperation("hdfs系统信息")
    public AjaxResult mkdir(
            @NotNull
            @RequestParam(name = "path")
                    String path
    ) throws IOException, URISyntaxException, InterruptedException {

        boolean b = hdfsService.mkdir(path);

        return AjaxResult.success(b ? "success" : "failure");

    }

    /**
     *
     * @return
     * @throws IOException
     * @throws URISyntaxException
     * @throws InterruptedException
     */
    @DeleteMapping("delete")
    @ApiOperation("删除file")
    public AjaxResult deleteFile(
            @NotNull
            @RequestParam(name = "path")
                    String path
    ) throws IOException, URISyntaxException, InterruptedException {

        boolean b = hdfsService.deleteFile(path);

        return AjaxResult.success(b ? "success" : "failure");

    }

    /**
     *
     * @return
     * @throws IOException
     * @throws URISyntaxException
     * @throws InterruptedException
     */
    @GetMapping("fileBlockLocation")
    @ApiOperation("hdfs块信息")
    public AjaxResult getBlockLocation(
            @NotNull
            @RequestParam(name = "path")
                    String path
    ) throws Exception {

        BlockLocation[] fileBlockLocations = hdfsService.getFileBlockLocations(path);

        return AjaxResult.success("success",fileBlockLocations);
    }

    /**
     * @return
     * @throws IOException
     * @throws URISyntaxException
     * @throws InterruptedException
     */
    @GetMapping("downloadFile")
    @ApiOperation("hdfs下载")
    public ResponseEntity<InputStreamResource> downloadFile(
            @NotNull
            @RequestParam(name = "path")
                    String path, HttpServletResponse response
    ) throws Exception {

        ResponseEntity<InputStreamResource> responseEntity = hdfsService.downloadFile(path, response);


        return responseEntity;
    }


    /**
     *
     * @return
     * @throws IOException
     * @throws URISyntaxException
     * @throws InterruptedException
     */
    @PostMapping("copyFile")
    @ApiOperation("复制")
    public AjaxResult copyFile(
            @NotNull
            @RequestParam
                    String sourcePath,
            @NotNull
            @RequestParam
                    String targetPath
    ) throws Exception {
        hdfsService.copyFile(sourcePath, targetPath);
        return AjaxResult.success("success copy："+sourcePath + "====>" + targetPath);
    }

    /**
     *
     * @return
     * @throws IOException
     * @throws URISyntaxException
     * @throws InterruptedException
     */
    @PostMapping("renameFile")
    @ApiOperation("重命名")
    public AjaxResult renameFile(
            @NotNull
            @RequestParam
                    String oldName,
            @NotNull
            @RequestParam
                    String newName
    ) throws Exception {
        boolean b = hdfsService.renameFile(oldName, newName);
        return AjaxResult.success(b ? "success" : "failure");
    }

    /**
     *
     * @return
     * @throws IOException
     * @throws URISyntaxException
     * @throws InterruptedException
     */
    @PostMapping("uploadFile")
    @ApiOperation("上传")
    public AjaxResult upload(
            String uploadFilePath,String destinationFile
    ) throws Exception {
        hdfsService.uploadFile(uploadFilePath, destinationFile);
        return AjaxResult.success("success upload：");
    }

    /**
     *
     * @return
     * @throws IOException
     * @throws URISyntaxException
     * @throws InterruptedException
     */
    @GetMapping("getEnable")
    @ApiOperation("获取启动状态")
    public AjaxResult getEnable(
    ) throws Exception {
        boolean enable = hdfsService.getEnable();
        return AjaxResult.success(enable);
    }



}
