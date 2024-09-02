package com.whai.blog.controller.admin;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.whai.blog.annotation.Log;
import com.whai.blog.model.FileMain;
import com.whai.blog.service.IFileMainService;
import com.whai.blog.utils.*;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.constraints.NotNull;
import java.io.IOException;
import java.util.List;

/**
 * 管理员进行文件管理
 */
@RestController
@RequestMapping("/admin/file")
@Validated
public class FileController {



    private static Logger logger = Logger.getLogger(FileController.class);


    @Autowired
    IFileMainService fileService;



    @Value("whai.uploadPath")
    private String uploadPath;






    /**
     * 文件列表
     *
     * @param page 第几页
     * @return
     */
    @GetMapping("fileList/{page}")
    @ResponseBody
    @ApiOperation("文件列表")
    public AjaxResult getFileList(
            @ApiParam(value = "页码", defaultValue = "1")
            @PathVariable
            @NotNull(message = "页码不能为空")
                    Integer page
    ) {

        //分页 每页显示8条数据
        Page<FileMain> fileIPage = new Page<FileMain>(page, 15);
        //条件筛选
        QueryWrapper<FileMain> select = new QueryWrapper<FileMain>();
        Page<FileMain> file = fileService.page(fileIPage, select);

        return AjaxResult.success("success", file);
    }

    /**
     * 文件列表  但查询不到逻辑删除

     * @return
     */
    @GetMapping("fileList")
    @ResponseBody
    @ApiOperation("文件列表不包含delete=1的 ")
    public AjaxResult getFileList() {

        //条件筛选
        QueryWrapper<FileMain> select = new QueryWrapper<FileMain>();
        select.eq("file_delete", "0");
        List<FileMain> files = fileService.list(select);

        return AjaxResult.success( "success", files);
    }

    /**
     * 博客列表
     *
     * @return
     */
    @GetMapping("fileListWithDelete")
    @ResponseBody
    @ApiOperation("文件列表")
//    @Log(value = "获取file",BUSINESS_TYPE = Log.BusinessType.SELECT)
    public AjaxResult getFileListWithDelete() {

        //条件筛选
        QueryWrapper<FileMain> select = new QueryWrapper<FileMain>();
        List<FileMain> files = fileService.list(select);

        return AjaxResult.success( "success", files);
    }


    /**
     *  ？？？？？？？？？？还需判断文件类型给予相应的service
     *
     * @param multipartFile  真实文件
     * @param fileMain 需要填写的信息
     * @return
     */
    @ApiOperation("新增文件")
    @PostMapping("uploadFile")
    @ResponseBody
    @Log(value = "更新file",BUSINESS_TYPE = Log.BusinessType.UPDATE)
    public AjaxResult uploadFile(

            @ApiParam(value = "file文件上传",required = true)
            @RequestPart(name = "file",required = true)
                    MultipartFile multipartFile,
            @ApiParam("文件信息填写")
                    FileMain fileMain) throws IOException {

        if (multipartFile.isEmpty()) {
            return new AjaxResult(HttpStatus.BAD_REQUEST, "请导入文件");
        }
        if (StringUtils.isNull(fileMain)) {
            return new AjaxResult(HttpStatus.BAD_REQUEST, "请输入文件信息");
        }



        //获取上传类型
        String contentType = multipartFile.getContentType();

        String path = System.getProperty("user.dir")+"/"+"file";
        //上传工具类 （path 上传路径）
        //pathname为完整路径


        String message = FileUtils.uploadUtils(path, multipartFile);


        //导入类型和用户
        fileMain.setFileType(contentType);
        //当前用户名
        fileMain.setFileUploadUsername(SecurityCustomUtils.getUserName());
        String requestUrl = "/api/file/" + multipartFile.getOriginalFilename();
        fileMain.setFileLocation(requestUrl);
        fileMain.setFileTitle(multipartFile.getOriginalFilename());
        fileMain.setFileUploadIp(ServletUtils.getIpAddress());



        boolean save = fileService.saveOrUpdate(fileMain);


        AjaxResult result;
        if (save) {
            result = AjaxResult.success(message);
            return result;
        } else {
            result = new AjaxResult(HttpStatus.ERROR, "导入文件失败");
            logger.error(result.toString());
            return result;
        }

    }


    /**
     * 逻辑删除
     * @param fileId
     * @return
     */
    @ApiOperation("删除file文件")
    @DeleteMapping("deleteFile")
    @ResponseBody
    @Log(value = "deletefile",BUSINESS_TYPE = Log.BusinessType.DELETE)
    public AjaxResult deletefile(@ApiParam("文件id") @RequestParam("fileId") String fileId) {
        //逻辑删除
        UpdateWrapper<FileMain> wrapper =
                new UpdateWrapper<FileMain>()
                        .eq("file_id", fileId)
                        .set("file_delete", 1);

        boolean delete = fileService.update(wrapper);


        AjaxResult result;
        if (delete){
            result = AjaxResult.success("删除文件成功");
            return result;
        }else {
            result = new AjaxResult(HttpStatus.WARN, "删除文件失败，文件不存在或者已经删除");
         logger.error(result.toString());
            return result;
        }
    }

    /**
     * 逻辑删除
     * @param fileId
     * @return
     */
    @ApiOperation("撤销删除file文件")
    @DeleteMapping("deleteFileCancel")
    @ResponseBody
    @Log(value = "deletefilecancel",BUSINESS_TYPE = Log.BusinessType.DELETE)
    public AjaxResult deleteFileCancel(@ApiParam("文件id") @RequestParam("fileId") String fileId) {

        boolean delete = fileService.deleteCancel(fileId);


        AjaxResult result;
        if (delete){
            result = AjaxResult.success("删除文件成功");
            return result;
        }else {
            result = new AjaxResult(HttpStatus.WARN, "删除文件失败，文件不存在或者已经删除");
            logger.error(result.toString());
            return result;
        }
    }

    /**
     * 逻辑删除
     * @param fileId
     * @return
     */
    @ApiOperation("确实删除file文件，不是逻辑删除")
    @DeleteMapping("deleteFileAccu")
    @ResponseBody
    @Log(value = "deleteFileAccu",BUSINESS_TYPE = Log.BusinessType.DELETE)
    public AjaxResult deleteFileAccu(@ApiParam("文件id") @RequestParam("fileId") String fileId) {
        //实际的删除

        //1. 根据id获取路径
        FileMain file = fileService.getById(fileId);
        //2. 根据路径删除文件
        String property = System.getProperty("user.dir");
        property += file.getFileLocation().substring(4);
        AjaxResult result = FileUtils.deleteFile(property);

        //3. 删除数据库内容
        fileService.deleteFileAccu(fileId);

        return result;
    }


    @ApiOperation(
            "模糊查询字段：1. title 文件标题；2. info，文件介绍；" +
                    "其他非模糊查询：1. system 文件系统查询；2. username 上传者用户名"
    )
    @GetMapping("findFile/{method}/{condition}")
    @ResponseBody
//    @Log(value = "获取file",BUSINESS_TYPE = Log.BusinessType.SELECT)
    public AjaxResult findFile(@PathVariable("method") @ApiParam("title、info、system、username 选择") String method,
                               @PathVariable(value = "condition") @ApiParam("条件") String condition
//                               @PathVariable("page") Integer page
    ) {

        //分页 每页显示8条数据
//        Page<FileMain> fileMainIPage = new Page<FileMain>();
        //条件筛选
        QueryWrapper<FileMain> select = new QueryWrapper<FileMain>();

        //查找全部File，包括逻辑删除
        switch (method) {
            case "title":
                select.like("file_title", condition);
                break;
            case "info":
                select.like("file_info", condition);
                break;
            case "system":
                select.eq("file_system", condition);
                break;
            case "username":
                select.eq("file_upload_username", condition);
                break;
        }
        List<FileMain> list = fileService.list(select);


        return AjaxResult.success( "success", list);
    }


}
