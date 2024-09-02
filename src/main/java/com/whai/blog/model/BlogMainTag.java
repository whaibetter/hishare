package com.whai.blog.model;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.io.Serializable;

/**
 * <p>
 *
 * </p>
 *
 * @author whai
 * @since 2022-10-29
 */
@TableName("blog_main_tag")
@ApiModel(value = "BlogMainTag对象", description = "")
public class BlogMainTag implements Serializable {

    private static final long serialVersionUID = 1L;


    @ApiModelProperty("tagid")
    @TableId(type = IdType.AUTO)
    private Integer blogTagId;

    private String blogMainId;

    public Integer getBlogTagId() {
        return blogTagId;
    }

    public void setBlogTagId(Integer blogTagId) {
        this.blogTagId = blogTagId;
    }
    public String getBlogMainId() {
        return blogMainId;
    }

    public void setBlogMainId(String blogMainId) {
        this.blogMainId = blogMainId;
    }

    @Override
    public String toString() {
        return "BlogMainTag{" +
            "blogTagId=" + blogTagId +
            ", blogMainId=" + blogMainId +
        "}";
    }
}
