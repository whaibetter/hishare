package com.whai.blog.controller.admin;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.whai.blog.annotation.Log;
import com.whai.blog.model.BlogMain;
import com.whai.blog.model.Tag;
import com.whai.blog.service.IBlogMainService;
import com.whai.blog.service.ITagService;
import com.whai.blog.utils.AjaxResult;
import com.whai.blog.utils.HttpStatus;
import com.whai.blog.utils.StringUtils;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 标签管理
 */
@Controller
@RequestMapping("/admin/tag")
public class TagController {



    private static Logger logger = Logger.getLogger(TagController.class);

    @Autowired
    ITagService tagService;
    @Autowired
    IBlogMainService blogMainService;

    /**
     * 跳转tag页面
     * @return
     */
    @GetMapping("toTagList")
    public String tagList()
    {
        return  "/admin/tag";
    }


    /**
     * 博客列表
     * @return
     */
    @GetMapping("tagList")
    @ResponseBody
    @ApiOperation("所有标签列表")
    public AjaxResult getTagList() {
        //条件筛选

        List<Tag> tags = tagService.list();

        return new AjaxResult(HttpStatus.SUCCESS, "success", tags);
    }

    /**
     * 博客列表
     * @return
     */
    @GetMapping("tagList/logic")
    @ResponseBody
    @ApiOperation("所有标签列表")
    public AjaxResult getTagListLogic() {
        //条件筛选
        List<Tag> tags = tagService.list();

        return new AjaxResult(HttpStatus.SUCCESS, "success", tags);
    }

    /**
     * 博客列表
     * @param page 第几页
     * @return
     */
    @GetMapping("tagList/{page}")
    @ResponseBody
    @ApiOperation("标签列表")
//    @Log(value = "获取tag",BUSINESS_TYPE = Log.BusinessType.SELECT)
    public AjaxResult getTagList(@ApiParam("页码") @PathVariable Integer page) {

        //分页 每页显示8条数据
        Page<Tag> tagIPage = new Page<Tag>(page, 15);
        //条件筛选
        QueryWrapper<Tag> select = new QueryWrapper<Tag>();
        Page<Tag> tag = tagService.page(tagIPage, select);


        return new AjaxResult(HttpStatus.SUCCESS, "success", tag);
    }




    @ApiOperation("新增标签")
    @PostMapping("addTag")
    @ResponseBody
//    @ApiImplicitParams({
//            @ApiImplicitParam(name = "tagId", value = "标签id", dataType = "Integer", dataTypeClass = Integer.class , readOnly = true),
//            @ApiImplicitParam(name = "tagName", value = "标签名称", required = true),
//    })
    @Log(value = "新增tag",BUSINESS_TYPE = Log.BusinessType.ADD)
    public AjaxResult addTag(@ApiParam("tag标签") @RequestBody Tag tag) {
        if (StringUtils.isNull(tag)){
            return new AjaxResult(HttpStatus.ERROR, "请输入标签内容");
        }

        boolean save = tagService.save(tag);
        AjaxResult result;
        if (save){
            result = new AjaxResult(HttpStatus.SUCCESS, "新建Tag成功");
            return result;
        }else {
            result = new AjaxResult(HttpStatus.ERROR, "新建Tag失败");
            logger.error(result.toString());
            return result;
        }

    }


    /**
     * 逻辑删除
     * @param tagId
     * @return
     */
    @ApiOperation("删除Tag标签")
    @DeleteMapping("deleteTag")
    @ResponseBody
    @Log(value = "删除tag",BUSINESS_TYPE = Log.BusinessType.DELETE)
    public AjaxResult deleteTag(@RequestParam("tagId") Integer tagId) {

        AjaxResult result;

        //如果有blog涉及到这个tag则不能删除
        QueryWrapper<BlogMain> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("blog_tag", tagId);
        List<BlogMain> list = blogMainService.list(queryWrapper);
        if (!list.isEmpty()){
            result = new AjaxResult(HttpStatus.ERROR, "删除Tag失败，有Blog使用了该Tag！");
            logger.error(result.toString());
            return result;
        }

        boolean delete = tagService.removeById(tagId);

        if (delete){
            result = new AjaxResult(HttpStatus.SUCCESS, "删除Tag成功");
        }else {
            result = new AjaxResult(HttpStatus.ERROR, "删除Tag失败");
            logger.error(result.toString());
        }
        return result;
    }



    @ApiOperation("更新Tag")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "tagId", value = "标签id", dataType = "Integer", dataTypeClass = Integer.class , readOnly = true),
            @ApiImplicitParam(name = "tagName", value = "标签名称")
    })
    @PostMapping("updateTag")
    @ResponseBody
    @Log(value = "更新tag",BUSINESS_TYPE = Log.BusinessType.UPDATE)
    public AjaxResult updateTag(@ApiParam("标签内容") @RequestBody Tag tag) {
        if (StringUtils.isNull(tag)){
            return new AjaxResult(HttpStatus.ERROR, "请输入博客内容");
        }

        boolean updateById = tagService.updateById(tag);
        AjaxResult result;
        if (updateById){
            result = new AjaxResult(HttpStatus.SUCCESS, "更新Tag成功");
        }else {
            result = new AjaxResult(HttpStatus.ERROR, "更新Tag失败");
            logger.error(result.toString());
        }
        return result;

    }

}
