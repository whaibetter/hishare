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
 * @since 2022-10-30
 */
@TableName("blog_comment")
@ApiModel(value = "BlogComment对象", description = "")
public class BlogComment implements Serializable {

    private static final long serialVersionUID = 1L;


    @TableId(type = IdType.ASSIGN_UUID)
    @ApiModelProperty("评论id")
    private String blogCommentId;

    @ApiModelProperty("评论session")
    private String blogCommentSession;

    @ApiModelProperty("评论ip")
    private String blogCommentIp;

    @ApiModelProperty("评论的blog")
    private String blogCommentBlogId;

    @ApiModelProperty("评论时间")
    private Date blogCommentTime;

    @ApiModelProperty("评论人位置")
    private String blogCommentLocation;

    @ApiModelProperty("评论内容")
    private String blogCommentContent;

    @ApiModelProperty(value = "逻辑删除")
    @TableLogic(value = "0", delval = "1")
    private Integer blogCommentDelete;

    @ApiModelProperty("评论like")
    private String blogCommentLike;

    @Override
    public String toString() {
        return "BlogComment{" +
                "blogCommentId='" + blogCommentId + '\'' +
                ", blogCommentSession='" + blogCommentSession + '\'' +
                ", blogCommentIp='" + blogCommentIp + '\'' +
                ", blogCommentBlogId='" + blogCommentBlogId + '\'' +
                ", blogCommentTime=" + blogCommentTime +
                ", blogCommentLocation='" + blogCommentLocation + '\'' +
                ", blogCommentContent='" + blogCommentContent + '\'' +
                ", blogCommentDelete=" + blogCommentDelete +
                ", blogCommentLike='" + blogCommentLike + '\'' +
                '}';
    }

    public String getBlogCommentId() {
        return blogCommentId;
    }

    public void setBlogCommentId(String blogCommentId) {
        this.blogCommentId = blogCommentId;
    }

    public String getBlogCommentSession() {
        return blogCommentSession;
    }

    public void setBlogCommentSession(String blogCommentSession) {
        this.blogCommentSession = blogCommentSession;
    }

    public String getBlogCommentIp() {
        return blogCommentIp;
    }

    public void setBlogCommentIp(String blogCommentIp) {
        this.blogCommentIp = blogCommentIp;
    }

    public String getBlogCommentBlogId() {
        return blogCommentBlogId;
    }

    public void setBlogCommentBlogId(String blogCommentBlogId) {
        this.blogCommentBlogId = blogCommentBlogId;
    }

    public Date getBlogCommentTime() {
        return blogCommentTime;
    }

    public void setBlogCommentTime(Date blogCommentTime) {
        this.blogCommentTime = blogCommentTime;
    }

    public String getBlogCommentLocation() {
        return blogCommentLocation;
    }

    public void setBlogCommentLocation(String blogCommentLocation) {
        this.blogCommentLocation = blogCommentLocation;
    }

    public String getBlogCommentContent() {
        return blogCommentContent;
    }

    public void setBlogCommentContent(String blogCommentContent) {
        this.blogCommentContent = blogCommentContent;
    }

    public Integer getBlogCommentDelete() {
        return blogCommentDelete;
    }

    public void setBlogCommentDelete(Integer blogCommentDelete) {
        this.blogCommentDelete = blogCommentDelete;
    }

    public String getBlogCommentLike() {
        return blogCommentLike;
    }

    public void setBlogCommentLike(String blogCommentLike) {
        this.blogCommentLike = blogCommentLike;
    }
}
