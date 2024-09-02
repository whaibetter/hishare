package com.whai.blog.controller.pub;

import com.whai.blog.annotation.Log;
import com.whai.blog.model.Home;
import com.whai.blog.service.*;
import com.whai.blog.utils.AjaxResult;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author whai
 * @since 2022-11-08
 */
@Controller
@RequestMapping("/home")
@Slf4j
public class PubHomeController {

    @Autowired
    IHomeService homeService;
    @Autowired
    IBlogMainService blogService;
    @Autowired
    IBlogBrowseService browseService;
    @Autowired
    IMessageService messageService;
    @Autowired
    IBlogCommentService commentService;


    @GetMapping("/getHomePage/{homeId}")
    @ResponseBody
    @ApiOperation("获取某个Home")
    @Log(value = "获取该页面", BUSINESS_TYPE = Log.BusinessType.SELECT)
    public AjaxResult getHome(@PathVariable @ApiParam("页面的id") Integer homeId) {
        Home home = homeService.getById(homeId);
        return AjaxResult.success("获取主页成功",home);
    }

}
