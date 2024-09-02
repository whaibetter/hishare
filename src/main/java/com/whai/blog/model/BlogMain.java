package com.whai.blog.model;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableLogic;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.io.Serializable;
import java.util.Date;

/**
 * <p>
 *
 * </p>
 *
 * @author whai
 * @since 2022-10-29
 */
@TableName("blog_main")
@ApiModel(value = "BlogMain对象", description = "")
public class BlogMain implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(type = IdType.ASSIGN_UUID)
    @ApiModelProperty(value = "博客id")
    private String blogId;

    @ApiModelProperty(value = "标题")
    private String blogTitle;

    @ApiModelProperty(value = "创建者user_name")
    private String blogCreateUser;

    @ApiModelProperty(value = "文本内容")
    private String blogContent;

    @ApiModelProperty(value = "创建时间")
    private Date blogCreateTime;

    @ApiModelProperty("存储的md文件位置")
    private String blogLocation;

    @ApiModelProperty(value = "blog标签tag")
    private String blogTag;

    @ApiModelProperty(value = "浏览数量")
    private String blogViews;

    @ApiModelProperty(value = "最后一次修改时间")
//    @TableField(fill = FieldFill.INSERT_UPDATE)//创建与修改时自动填充
    private Date blogLastChangeTime;

    @ApiModelProperty(value = "喜欢这个博客")
    private Integer blogLike;

    @ApiModelProperty("博客简介")
    private String blogIntro;

    @ApiModelProperty("封面图片位置地址")
    private String blogCover;

    @ApiModelProperty(value = "逻辑删除")
    @TableLogic(value = "0", delval = "1")
    private Integer blogDelete;

    public String getBlogId() {
        return blogId;
    }

    public void setBlogId(String blogId) {
        this.blogId = blogId;
    }
    public String getBlogTitle() {
        return blogTitle;
    }

    public void setBlogTitle(String blogTitle) {
        this.blogTitle = blogTitle;
    }
    public String getBlogCreateUser() {
        return blogCreateUser;
    }

    public void setBlogCreateUser(String blogCreateUser) {
        this.blogCreateUser = blogCreateUser;
    }
    public String getBlogContent() {
        return blogContent;
    }

    public void setBlogContent(String blogContent) {
        this.blogContent = blogContent;
    }
    public Date getBlogCreateTime() {
        return blogCreateTime;
    }

    public void setBlogCreateTime(Date blogCreateTime) {
        this.blogCreateTime = blogCreateTime;
    }
    public String getBlogLocation() {
        return blogLocation;
    }

    public void setBlogLocation(String blogLocation) {
        this.blogLocation = blogLocation;
    }
    public String getBlogTag() {
        return blogTag;
    }

    public void setBlogTag(String blogTag) {
        this.blogTag = blogTag;
    }
    public String getBlogViews() {
        return blogViews;
    }

    public void setBlogViews(String blogViews) {
        this.blogViews = blogViews;
    }
    public Date getBlogLastChangeTime() {
        return blogLastChangeTime;
    }

    public void setBlogLastChangeTime(Date blogLastChangeTime) {
        this.blogLastChangeTime = blogLastChangeTime;
    }
    public Integer getBlogLike() {
        return blogLike;
    }

    public void setBlogLike(Integer blogLike) {
        this.blogLike = blogLike;
    }
    public String getBlogIntro() {
        return blogIntro;
    }

    public void setBlogIntro(String blogIntro) {
        this.blogIntro = blogIntro;
    }
    public String getBlogCover() {
        return blogCover;
    }

    public void setBlogCover(String blogCover) {
        this.blogCover = blogCover;
    }
    public Integer getBlogDelete() {
        return blogDelete;
    }

    public void setBlogDelete(Integer blogDelete) {
        this.blogDelete = blogDelete;
    }

    @Override
    public String toString() {
        return "BlogMain{" +
            "blogId=" + blogId +
            ", blogTitle=" + blogTitle +
            ", blogCreateUser=" + blogCreateUser +
            ", blogContent=" + blogContent +
            ", blogCreateTime=" + blogCreateTime +
            ", blogLocation=" + blogLocation +
            ", blogTag=" + blogTag +
            ", blogViews=" + blogViews +
            ", blogLastChangeTime=" + blogLastChangeTime +
            ", blogLike=" + blogLike +
            ", blogIntro=" + blogIntro +
            ", blogCover=" + blogCover +
            ", blogDelete=" + blogDelete +
        "}";
    }
}
