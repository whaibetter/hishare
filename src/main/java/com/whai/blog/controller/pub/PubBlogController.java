package com.whai.blog.controller.pub;


import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.whai.blog.annotation.BrowseLog;
import com.whai.blog.component.redis.RedisCache;
import com.whai.blog.constant.CacheConstants;
import com.whai.blog.model.BlogComment;
import com.whai.blog.model.BlogMain;
import com.whai.blog.service.IBlogCommentService;
import com.whai.blog.service.IBlogMainService;
import com.whai.blog.utils.AjaxResult;
import com.whai.blog.utils.HttpStatus;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.MarkerFactory;
import org.slf4j.impl.Log4jLoggerAdapter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequestMapping("/blog")
@RestController
@Slf4j
public class PubBlogController {


    @Autowired
    IBlogMainService blogMainService;

    @Autowired
    IBlogCommentService commentService;

    @Autowired
    private RedisCache redisCache;




    /**
     * 博客列表
     * @return
     */
    @GetMapping("getHomeBlogs")
    @ApiOperation("博客前9个")
    @BrowseLog("getPreBlogs")
    public AjaxResult getHomeBlogs() {

        log.info(MarkerFactory.getMarker(Log4jLoggerAdapter.class.getName()),"getPreBlogs");

        JSONObject cacheObject = redisCache.getCacheObject(CacheConstants.BLOGS_LIST + "1");
        if (cacheObject!=null){
            return new AjaxResult(HttpStatus.SUCCESS, "success", cacheObject);
        }

        // 去同步第一页
        blogMainService.synchronizedBlogCache(1);

        Page<BlogMain> blogMainIPage = new Page<BlogMain>(1, 9);
        QueryWrapper<BlogMain> select = new QueryWrapper<BlogMain>();
        select.orderByDesc("blog_create_time");
        Page<BlogMain> blog = blogMainService.page(blogMainIPage, select);


        return new AjaxResult(HttpStatus.SUCCESS, "success", blog);
    }


    /**
     * 博客列表
     * @return
     */
    @GetMapping("search")
    @ApiOperation("博客列表")
    public AjaxResult searchByWord(String keyWord) {
        List<BlogMain> blogList = blogMainService.searchByKeyWord(keyWord);
        return blogList.size() == 0 ? AjaxResult.warn("获取不到有关：" + keyWord + "的blog！") : AjaxResult.success("获取成功，返回blogList", blogList);
    }




    /**
     * 博客列表
     * @param page 第几页
     * @return
     */
    @GetMapping("blogList/{page}")
    @ApiOperation("博客列表")
    @BrowseLog("getBlogList")
    public AjaxResult getBlogList(@ApiParam("页码") @PathVariable Integer page) {
        String cacheKey = CacheConstants.BLOGS_LIST + page;

        JSONObject cacheObject = redisCache.getCacheObject( cacheKey + page);
        if (cacheObject!=null){
            return new AjaxResult(HttpStatus.SUCCESS, "success", cacheObject);
        }

        blogMainService.synchronizedBlogCache(page);

        Page<BlogMain> blogMainIPage = new Page<BlogMain>(page, 9);
        QueryWrapper<BlogMain> select = new QueryWrapper<BlogMain>();
        Page<BlogMain> blog = blogMainService.page(blogMainIPage, select);


        return new AjaxResult(HttpStatus.SUCCESS, "success", blog);
    }



    /**
     * 博客列表
     * @return
     */
    @GetMapping("blog/{blogId}")
    @ApiOperation("获取某个blog")
    public AjaxResult getBlog(@ApiParam("博客id") @PathVariable String blogId) {

        BlogMain blog = blogMainService.getBlogById(blogId);

        AjaxResult ans ;
        if (blog==null){
            ans = new AjaxResult(HttpStatus.BAD_REQUEST, "未找到");
            return ans;
        }

        List<BlogComment> blogComments = commentService.queryBlogComment(blogId);


        if (blogComments.isEmpty() ) {
            ans = new AjaxResult(HttpStatus.BAD_REQUEST, "没有相关评论", blog);
            return ans;
        }


        ans = new AjaxResult(HttpStatus.SUCCESS, "success", blog);
        ans.put("comment", blogComments);
        return ans;
    }

    @ApiOperation("按照标题、内容、简介查模糊搜索博客，findBlog/{method}/{condition}/{page}，title、content、intro 三个选择")
    @GetMapping("findBlog/{method}/{condition}")
    public AjaxResult findBlog(@PathVariable("method") @ApiParam("title、content、intro 三个选择") String method,
                               @PathVariable(value = "condition") @ApiParam("条件") String condition
    ) {

//        //分页 每页显示8条数据
//        Page<BlogMain> blogMainIPage = new Page<BlogMain>();
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


        return new AjaxResult(HttpStatus.SUCCESS, "success", list);
    }

    @ApiOperation("模糊搜索博客，findBlogCustom")
    @GetMapping("findBlogCustom/{search}")
    public AjaxResult findBlogCustom(@ApiParam("搜索关键词") @PathVariable("search") String search
    ) {


        List<BlogMain> list = blogMainService.searchByKeyWord(search);

        return new AjaxResult(HttpStatus.SUCCESS, "success", list);
    }

    @ApiOperation("喜欢某个博客")
    @PostMapping("likeBlog")
    public AjaxResult likeBlog(@ApiParam("博客内容") @RequestParam("blogId") String blogId) {
        return new AjaxResult(HttpStatus.SUCCESS, "", blogMainService.likeBlog(blogId));
    }

    /**
     * 博客列表,根据tag进行查询
     * @param page 第几页
     * @return
     */
    @GetMapping("blogList/{tagId}/{page}")
    @ApiOperation("根据tagid查询对应博客列表")
    public AjaxResult getBlogListByTag(@ApiParam("页码") @PathVariable Integer tagId ,@ApiParam("页码") @PathVariable Integer page) {

        //分页 每页显示8条数据
        Page<BlogMain> blogMainIPage = new Page<BlogMain>(page, 8);
        //条件筛选
        QueryWrapper<BlogMain> select = new QueryWrapper<BlogMain>();
        select.eq("tag_id", tagId);

        IPage<BlogMain> blogByTagId = blogMainService.findBlogByTagId(select,blogMainIPage);

        return new AjaxResult(HttpStatus.SUCCESS, "success", blogByTagId);
    }



}
