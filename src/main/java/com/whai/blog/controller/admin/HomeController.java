package com.whai.blog.controller.admin;

import com.whai.blog.annotation.Log;
import com.whai.blog.model.Home;
import com.whai.blog.service.*;
import com.whai.blog.utils.AjaxResult;
import com.whai.blog.utils.HttpStatus;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author whai
 * @since 2022-11-08
 */
@Controller
@RequestMapping("/admin/home")
@Slf4j
public class HomeController {

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

    @GetMapping("/homeList")
    @ApiOperation("Home列表")
    @ResponseBody
    public AjaxResult getHomeList() {
        List<Home> list = homeService.list();
        return AjaxResult.success( "success", list);
    }

    @PostMapping("/updateHomePage")
    @ApiOperation("更新home")
    @ResponseBody
    public AjaxResult updateHome( @ApiParam("激活页面的实体") @RequestBody  Home home){
        boolean updateById = homeService.updateById(home);
        return AjaxResult.success( "主页更新成功", updateById);
    }

    @PostMapping("/activeHomePage/{homeId}")
    @ResponseBody
    @ApiOperation("激活Home")
    @Log(value = "激活该页面", BUSINESS_TYPE = Log.BusinessType.UPDATE)
    public AjaxResult activeHome(@PathVariable @ApiParam("激活页面的id") String homeId) {
        //先把其他的home页面状态都赋值为0表示不激活
        homeService.update().set("home_status", "0");
        //设置home激活
        homeService.update().eq("home_id", homeId).set("home_status", "1");
        return AjaxResult.success( "激活成功");
    }

    @GetMapping("/getHomePage/{homeId}")
    @ResponseBody
    @ApiOperation("获取某个Home")
    @Log(value = "获取该页面", BUSINESS_TYPE = Log.BusinessType.SELECT)
    public AjaxResult getHome(@PathVariable @ApiParam("页面的id") Integer homeId) {
        Home home = homeService.getById(homeId);
        return AjaxResult.success("获取主页成功",home);
    }

    @ResponseBody
    @ApiOperation("添加Home")
    @PostMapping("/addHomePage")
    public AjaxResult addHome(Home home){
        if (home.getHomeTitle()==null){
            return new AjaxResult(HttpStatus.BAD_REQUEST, "标题为空！");
        }
        if (home.getHomeHtml()==null){
            return new AjaxResult(HttpStatus.BAD_REQUEST, "请输入主页主体内容！");
        }
        boolean updateById = homeService.save(home);
        return AjaxResult.success( "主页更新成功", updateById);
    }


    /**
     * 博客列表
     * @return
     */
    @GetMapping("dashboardInfo")
    @ApiOperation("浏览dashboard")
    @ResponseBody
    public AjaxResult dashboardInfo() {

        long browseCount = browseService.count();
        long blogCount = blogService.count();
        long messageCount = messageService.count();
        long commentCount = commentService.count();

        //blog.hasNext用于判断下一页
//        model.addAttribute("blogList", blog);
        AjaxResult result = AjaxResult.success("获取dashboard成功");
        result.put("browseCount", browseCount);
        result.put("blogCount", blogCount);
        result.put("messageCount", messageCount);
        result.put("commentCount", commentCount);

        return result;
    }

}
