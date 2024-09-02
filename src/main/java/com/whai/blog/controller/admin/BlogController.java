package com.whai.blog.controller.admin;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.whai.blog.annotation.Log;
import com.whai.blog.model.BlogMain;
import com.whai.blog.service.IBlogMainService;
import com.whai.blog.utils.AjaxResult;
import com.whai.blog.utils.HttpStatus;
import com.whai.blog.utils.StringUtils;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;


/**
 * 管理员进行用户管理
 */
@Api("管理员对博客管理控制器")
@RestController
@RequestMapping("/admin/blog")
public class BlogController {


    private static final Logger logger = LoggerFactory.getLogger(BlogController.class);


    @Autowired
    IBlogMainService blogMainService;


    /**
     * 博客列表
     * @return
     */
    @GetMapping("blogList")
    @ApiOperation("博客列表")
    public AjaxResult getBlogs( Model model) {

        List<BlogMain> blogList = blogMainService.list(new QueryWrapper<>());
        //blog.hasNext用于判断下一页
        model.addAttribute("blogList", blogList);
        return AjaxResult.success("", blogList);
    }

    /**
     * 博客列表
     * @param page 第几页
     * @return
     */
    @GetMapping("blogList/{page}")
    @ApiOperation("博客列表")
//    @Log(value = "获取bloglist",BUSINESS_TYPE = Log.BusinessType.SELECT)
    public AjaxResult getBlogList(@ApiParam("页码") @PathVariable Integer page, Model model) {

        //分页 每页显示8条数据
        Page<BlogMain> blogMainIPage = new Page<BlogMain>(page, 8);
        //条件筛选
        QueryWrapper<BlogMain> select = new QueryWrapper<BlogMain>();
        Page<BlogMain> blog = blogMainService.page(blogMainIPage, select);

        return AjaxResult.success("获取成功，返回blogList",blog);
    }

    /**
     * 博客列表
     * @param page 第几页
     * @return
     */
    @GetMapping("blogListWithDelete/{page}")
    @ApiOperation("博客列表")
    public AjaxResult getBlogListWithDelete(@ApiParam("页码") @PathVariable Integer page) {

        //分页 每页显示8条数据
        Page<BlogMain> blogMainIPage = new Page<BlogMain>(page, 8);
        //条件筛选
        QueryWrapper<BlogMain> select = new QueryWrapper<BlogMain>();
        IPage<BlogMain> blogList = blogMainService.pageWithDelete(select, blogMainIPage);


        return AjaxResult.success("获取成功，返回blogList",blogList);
    }



    @ApiOperation("新增博客")
    @PostMapping("addBlog")
    @Log(value = "新增blog",BUSINESS_TYPE = Log.BusinessType.ADD)
    public AjaxResult addBlog(@ApiParam("博客内容") @RequestBody BlogMain blogMain) {
        if (StringUtils.isNull(blogMain)){
            return new AjaxResult(HttpStatus.BAD_REQUEST, "请输入博客内容");
        }
        boolean save = blogMainService.save(blogMain);
        AjaxResult result;
        if (save){
            result = new AjaxResult(HttpStatus.SUCCESS, "新建Blog成功");
        }else {
            result = new AjaxResult(HttpStatus.ERROR, "新建Blog失败");
            logger.error(result.toString());
        }
        return result;

    }


    /**
     * 逻辑删除
     * @param blogId
     * @return
     */
    @ApiOperation("逻辑删除博客")
    @DeleteMapping("deleteBlog/{blogId}")
    @Log(value = "逻辑删除博客",BUSINESS_TYPE = Log.BusinessType.DELETE)
    public AjaxResult deleteBlog(@PathVariable("blogId") String blogId) {
        //逻辑删除
        boolean delete = blogMainService.removeById(blogId);

        AjaxResult result;
        if (delete){
            result = new AjaxResult(HttpStatus.SUCCESS, "删除Blog成功");
            return result;
        }else {
            result = new AjaxResult(HttpStatus.ERROR, "删除Blog失败");
            logger.error(result.toString());
            return result;
        }
    }

    /**
     * 逻辑删除
     * @param blogId
     * @return
     */
    @ApiOperation("撤销逻辑删除博客")
    @DeleteMapping("deleteBlogCancel/{blogId}")
    @Log(value = "撤销逻辑删除博客",BUSINESS_TYPE = Log.BusinessType.DELETE)
    public AjaxResult deleteBlogCancel(@PathVariable("blogId") String blogId) {
        //逻辑删除
        boolean delete = blogMainService.cancelDelete(blogId);


        AjaxResult result;
        if (delete){
            result = new AjaxResult(HttpStatus.SUCCESS, "删除Blog成功");
            return result;
        }else {
            result = new AjaxResult(HttpStatus.ERROR, "删除Blog失败");
            logger.error(result.toString());
            return result;
        }
    }

    @ApiOperation("按照标题、内容、简介查模糊搜索博客，findBlog/{method}/{condition}/{page}，title、content、intro 三个选择")
    @GetMapping("findBlog/{method}/{condition}")
    public AjaxResult findBlog(@PathVariable("method") @ApiParam("title、content、intro 三个选择") String method,
                           @PathVariable(value = "condition") @ApiParam("条件") String condition
    ) {

        //条件筛选
        QueryWrapper<BlogMain> select = new QueryWrapper<BlogMain>();

        switch (method) {
            case "title":
                select.like("blog_title", condition);
                break;
            case "content":
                select.like("blog_content", condition);
                break;
            case "intro":
                select.like("blog_intro", condition);
                break;
        }
        List<BlogMain> list = blogMainService.list(select);
        AjaxResult result;
        if (list.isEmpty()){
            result = AjaxResult.warn("未找到该条件的blog");
        }else {
            result = new AjaxResult(HttpStatus.SUCCESS,"获取blogs成功",list);
        }
        return result;
    }


    /**
     *
     * @param blogMain
     * @return
     */
    @ApiOperation("更新博客")
    @PostMapping("updateBlog")
    @ResponseBody
    @Log(value = "更新blog",BUSINESS_TYPE = Log.BusinessType.UPDATE)
    public AjaxResult updateBlog(@ApiParam("博客内容") @RequestBody BlogMain blogMain) {
        logger.info("更新blog" + blogMain.toString());
        boolean updateById = blogMainService.updateById(blogMain);

        AjaxResult result;
        if (updateById){
            result = new AjaxResult(HttpStatus.SUCCESS, "更新Blog成功");
        }else {
            result = new AjaxResult(HttpStatus.ERROR, "更新Blog失败");
            logger.error(result.toString());
        }
        return result;
    }
















}
