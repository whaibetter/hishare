package com.whai.blog.model;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
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
@ApiModel(value = "Tag对象")
public class Tag implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "tag_id", type = IdType.AUTO)
    private Integer tagId;

    @ApiModelProperty("标签名称")
    private String tagName;

    @ApiModelProperty(value = "创建时间")
    private Date tagCreateTime;

    /**
     * 逻辑删除，已经弃用
     */
    private Integer tagDelete;

    @ApiModelProperty("标签的介绍")
    private String tagInfo;

    @ApiModelProperty("父标签id")
    private Integer tagParentsId;

    public Integer getTagId() {
        return tagId;
    }

    public void setTagId(Integer tagId) {
        this.tagId = tagId;
    }
    public String getTagName() {
        return tagName;
    }

    public void setTagName(String tagName) {
        this.tagName = tagName;
    }
    public Date getTagCreateTime() {
        return tagCreateTime;
    }

    public void setTagCreateTime(Date tagCreateTime) {
        this.tagCreateTime = tagCreateTime;
    }
    public Integer getTagDelete() {
        return tagDelete;
    }

    public void setTagDelete(Integer tagDelete) {
        this.tagDelete = tagDelete;
    }
    public String getTagInfo() {
        return tagInfo;
    }

    public void setTagInfo(String tagInfo) {
        this.tagInfo = tagInfo;
    }
    public Integer getTagParentsId() {
        return tagParentsId;
    }

    public void setTagParentsId(Integer tagParentsId) {
        this.tagParentsId = tagParentsId;
    }

    @Override
    public String toString() {
        return "Tag{" +
            "tagId=" + tagId +
            ", tagName=" + tagName +
            ", tagCreateTime=" + tagCreateTime +
            ", tagDelete=" + tagDelete +
            ", tagInfo=" + tagInfo +
            ", tagParentsId=" + tagParentsId +
        "}";
    }
}
