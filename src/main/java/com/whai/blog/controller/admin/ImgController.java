package com.whai.blog.controller.admin;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.whai.blog.annotation.Log;
import com.whai.blog.model.ImgMain;
import com.whai.blog.service.IImgMainService;
import com.whai.blog.utils.*;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;


/**
 * 管理员进行图片管理，用于进行页面图片轮转等
 */
@RestController
@RequestMapping("/admin/img")
@Slf4j
public class ImgController {


    @Autowired
    IImgMainService imgService;


    /**
     * 跳转img页面
     * @return
     */
    @GetMapping("toImgList")
    public String imgList()
    {
        return  "/admin/imgList";
    }

    /**
     * img列表
     * @return
     */
    @GetMapping("imgList")
    @ResponseBody
    @ApiOperation("图片列表，不包含逻辑删除")
//    @Log(value = "获取img",BUSINESS_TYPE = Log.BusinessType.SELECT)
    public AjaxResult getImgList() {


        //条件筛选
        QueryWrapper<ImgMain> select = new QueryWrapper<ImgMain>();
        select.eq("img_delete", "0");
        List<ImgMain> img = imgService.list(select);


        return AjaxResult.success( "success", img);
    }

    /**
     * img列表
     * @return
     */
    @GetMapping("imgListWithDelete")
    @ResponseBody
    @ApiOperation("图片列表，包含逻辑删除")
//    @Log(value = "获取img",BUSINESS_TYPE = Log.BusinessType.SELECT)
    public AjaxResult getImgListWithDelete() {


        //条件筛选
        QueryWrapper<ImgMain> select = new QueryWrapper<ImgMain>();
        List<ImgMain> img = imgService.list(select);


        return AjaxResult.success("success", img);
    }



    /**
     * 博客列表
     * @param page 第几页
     * @return
     */
    @GetMapping("imgList/{page}")
    @ResponseBody
    @ApiOperation("图片列表")
//    @Log(value = "获取img",BUSINESS_TYPE = Log.BusinessType.SELECT)
    public AjaxResult getImgList(@ApiParam("页码") @PathVariable Integer page) {

        //分页 每页显示8条数据
        Page<ImgMain> imgIPage = new Page<ImgMain>(page, 15);
        //条件筛选
        QueryWrapper<ImgMain> select = new QueryWrapper<ImgMain>();
        Page<ImgMain> img = imgService.page(imgIPage, select);


        return AjaxResult.success( "success", img);
    }





    @ApiOperation("新增图片")
    @PostMapping("addImg")
    @ResponseBody
    @Log(value = "获取img",BUSINESS_TYPE = Log.BusinessType.ADD)
    public AjaxResult addImg(
            @ApiParam("img标签") ImgMain img,
            @ApiParam(value = "file文件上传",required = true)
            @RequestPart("file") MultipartFile multipartFile
    ) {
        if (StringUtils.isNull(img)||multipartFile.isEmpty()){
            return new AjaxResult(HttpStatus.BAD_REQUEST, "请导入图片");
        }
        String path = System.getProperty("user.dir")+"/"+"img";

        //上传工具类 （path 上传路径）
        try {
            FileUtils.uploadUtils(path, multipartFile);
            //当前用户名
//        String username = SecurityCustomUtils.getSecurityUser().getUsername();
            String userName = SecurityCustomUtils.getUserName();
            img.setImgUploadUser(StringUtils.isNull(userName) ?null:userName);

            //获取的相对路径名称，便于前端通过代理直接访问
            img.setImgLocation("/api/img/" + multipartFile.getOriginalFilename());
            img.setImgUploadUser(SecurityCustomUtils.getUserName());
            img.setImgTitle(multipartFile.getOriginalFilename());
        } catch (IOException e) {
            e.printStackTrace();
            return new AjaxResult(HttpStatus.ERROR, "上传失败" + e.toString());
        }

        boolean save = imgService.save(img);

        AjaxResult result;
        if (save){
            result = new AjaxResult(HttpStatus.SUCCESS, "插入图片成功",img);
            return result;
        }else {
            result = new AjaxResult(HttpStatus.ERROR, "插入图片失败");
            log.error(result.toString());
            return result;
        }

    }


    /**
     * 逻辑删除
     * @param imgId
     * @return
     */
    @ApiOperation("删除img")
    @DeleteMapping("deleteImg")
    @ResponseBody
    @Log(value = "获取img",BUSINESS_TYPE = Log.BusinessType.DELETE)
    public AjaxResult deleteImg(@ApiParam("图片id") @RequestParam("imgId") String imgId) {
        //逻辑删除
        boolean delete = imgService.removeById(imgId);


        AjaxResult result;
        if (delete){
            result = AjaxResult.success( "删除图片成功");
            return result;
        }else {
            result = new AjaxResult(HttpStatus.ERROR, "删除图片失败");
            log.error(result.toString());
            return result;
        }
    }

    /**
     * 逻辑删除
     * @param imgId
     * @return
     */
    @ApiOperation("删除img")
    @DeleteMapping("deleteImgCancel")
    @ResponseBody
    @Log(value = "撤销删除",BUSINESS_TYPE = Log.BusinessType.UPDATE)
    public AjaxResult deleteImgCancel(@ApiParam("图片id") @RequestParam("imgId") String imgId) {
        //逻辑删除
        boolean delete = imgService.cancelDelete(imgId);

        AjaxResult result;
        if (delete){
            result = AjaxResult.success( "撤销删除图片成功");
            return result;
        }else {
            result = new AjaxResult(HttpStatus.ERROR, "撤销删除图片失败");
            log.error(result.toString());
            return result;
        }
    }



    /**
     * 逻辑删除
     * @param imgId
     * @return
     */
    @ApiOperation("真实 删除img")
    @DeleteMapping("deleteImgAccu")
    @ResponseBody
    @Log(value = "获取img",BUSINESS_TYPE = Log.BusinessType.DELETE)
    public AjaxResult deleteImgAccu(@ApiParam("图片id") @RequestParam("imgId") String imgId) {
        //实际的删除

        //1. 根据id获取路径
        ImgMain img = imgService.getById(imgId);
        //2. 根据路径删除文件
        String property = System.getProperty("user.dir");
        property += img.getImgLocation().substring(4);
        AjaxResult result = FileUtils.deleteFile(property);
        //3. 删除数据库内容
        imgService.deleteImgAccu(imgId);


        return result;
    }



}
