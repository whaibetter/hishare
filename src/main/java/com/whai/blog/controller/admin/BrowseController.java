package com.whai.blog.controller.admin;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.whai.blog.model.BlogBrowse;
import com.whai.blog.service.IBlogBrowseService;
import com.whai.blog.utils.AjaxResult;
import com.whai.blog.utils.HttpStatus;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/***
 * 用户浏览情况
 */
@Controller
@RequestMapping("/admin/browse")
public class BrowseController {

    @Autowired
    IBlogBrowseService blogBrowseService;

    /**
     * 博客列表
     * @param page 第几页
     * @return
     */
    @GetMapping("browseList/{page}")
    @ApiOperation("博客列表")
    @ResponseBody
    public AjaxResult getBrowseList(@ApiParam("页码") @PathVariable Integer page) {

        //分页 每页显示8条数据
        Page<BlogBrowse> BPage = new Page<BlogBrowse>(page, 15);

        QueryWrapper<BlogBrowse> queryWrapper = new QueryWrapper<>();
        queryWrapper.orderByDesc("browse_time");
        Page<BlogBrowse> browsePage = blogBrowseService.page(BPage, queryWrapper);

        //blog.hasNext用于判断下一页
//        model.addAttribute("blogList", blog);
        return new AjaxResult(HttpStatus.SUCCESS,"获取成功，返回blogList",browsePage);
    }




}
