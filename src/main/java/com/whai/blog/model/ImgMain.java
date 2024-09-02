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
@TableName("img_main")
@ApiModel(value = "ImgMain对象", description = "")
public class ImgMain implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(type = IdType.ASSIGN_UUID)
    @ApiModelProperty("图片id")
    private String imgId;


    @ApiModelProperty("图片标题")
    private String imgTitle;

    @ApiModelProperty("上传图片的位置")
    private String imgLocation;

    @ApiModelProperty("上传时间")
//    @TableField(fill = FieldFill.INSERT)//创建与修改时自动填充
    private Date imgUploadTime;

    @ApiModelProperty("上传者")
    private String imgUploadUser;

    @ApiModelProperty("逻辑删除")
    @TableLogic
    private Integer imgDelete;

    @ApiModelProperty("img的tag类型")
    private String imgTagId;

    public String getImgId() {
        return imgId;
    }

    public void setImgId(String imgId) {
        this.imgId = imgId;
    }
    public String getImgTitle() {
        return imgTitle;
    }

    public void setImgTitle(String imgTitle) {
        this.imgTitle = imgTitle;
    }
    public String getImgLocation() {
        return imgLocation;
    }

    public void setImgLocation(String imgLocation) {
        this.imgLocation = imgLocation;
    }
    public Date getImgUploadTime() {
        return imgUploadTime;
    }

    public void setImgUploadTime(Date imgUploadTime) {
        this.imgUploadTime = imgUploadTime;
    }
    public String getImgUploadUser() {
        return imgUploadUser;
    }

    public void setImgUploadUser(String imgUploadUser) {
        this.imgUploadUser = imgUploadUser;
    }
    public Integer getImgDelete() {
        return imgDelete;
    }

    public void setImgDelete(Integer imgDelete) {
        this.imgDelete = imgDelete;
    }
    public String getImgTagId() {
        return imgTagId;
    }

    public void setImgTagId(String imgTagId) {
        this.imgTagId = imgTagId;
    }

    @Override
    public String toString() {
        return "ImgMain{" +
            "imgId=" + imgId +
            ", imgTitle=" + imgTitle +
            ", imgLocation=" + imgLocation +
            ", imgUploadTime=" + imgUploadTime +
            ", imgUploadUser=" + imgUploadUser +
            ", imgDelete=" + imgDelete +
            ", imgTagId=" + imgTagId +
        "}";
    }
}
