package com.whai.blog.controller.pub;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.whai.blog.model.ImgMain;
import com.whai.blog.service.IImgMainService;
import com.whai.blog.utils.AjaxResult;
import com.whai.blog.utils.HttpStatus;
import io.swagger.annotations.ApiOperation;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;


/**
 * 管理员进行图片管理，用于进行页面图片轮转等
 */
@Controller
@RequestMapping("/img")
public class PubImgController {

    private static Logger logger = Logger.getLogger(PubImgController.class);

    @Autowired
    IImgMainService imgService;



    /**
     * 博客列表
     * @return
     */
    @GetMapping("imgHome")
    @ResponseBody
    @ApiOperation("home图片列表")
//    @Log(value = "获取img",BUSINESS_TYPE = Log.BusinessType.SELECT)
    public AjaxResult getImgHome() {

        //分页 每页显示8条数据
        Page<ImgMain> imgIPage = new Page<ImgMain>(1, 10);
        //条件筛选
        QueryWrapper<ImgMain> select = new QueryWrapper<ImgMain>();
        select.orderByDesc("img_upload_time");
        Page<ImgMain> img = imgService.page(imgIPage, select);


        return new AjaxResult(HttpStatus.SUCCESS, "success", img);
    }




}
