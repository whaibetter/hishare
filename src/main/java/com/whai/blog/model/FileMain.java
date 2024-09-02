package com.whai.blog.model;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
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
@TableName("file_main")
@ApiModel(value = "FileMain对象", description = "")
public class FileMain implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty("文件id")
    @TableId(type = IdType.ASSIGN_UUID)
    private String fileId;

    @ApiModelProperty("file上传时间")
    private Date fileUploadTime;

    @ApiModelProperty("上传者")
    private String fileUploadUsername;

    @ApiModelProperty("上传地点")
    private String fileUploadLocation;

    @ApiModelProperty("上传者ip")
    private String fileUploadIp;

    @ApiModelProperty("喜欢这个文件")
    private Integer fileLike;

    @ApiModelProperty("文件标题")
    private String fileTitle;

    @ApiModelProperty("文件上传位置")
    private String fileLocation;

    @ApiModelProperty("上传文件类型")
    private String fileType;

    @ApiModelProperty("上传文件属于什么系统 如hdfs、file...")
    private String fileSystem;

    @ApiModelProperty("远程文件的url，如有必要")
    private String fileRemoteUrl;

    @ApiModelProperty("file介绍信息")
    private String fileInfo;

    @ApiModelProperty("其他信息")
    private String fileOtherIntro;

    @ApiModelProperty("0表示正常，1表示删除，逻辑删除，不在mybatis plus中使用逻辑删除，不然无法显示")
    private Integer fileDelete;

    public String getFileId() {
        return fileId;
    }

    public void setFileId(String fileId) {
        this.fileId = fileId;
    }
    public Date getFileUploadTime() {
        return fileUploadTime;
    }

    public void setFileUploadTime(Date fileUploadTime) {
        this.fileUploadTime = fileUploadTime;
    }
    public String getFileUploadUsername() {
        return fileUploadUsername;
    }

    public void setFileUploadUsername(String fileUploadUsername) {
        this.fileUploadUsername = fileUploadUsername;
    }
    public String getFileUploadLocation() {
        return fileUploadLocation;
    }

    public void setFileUploadLocation(String fileUploadLocation) {
        this.fileUploadLocation = fileUploadLocation;
    }
    public String getFileUploadIp() {
        return fileUploadIp;
    }

    public void setFileUploadIp(String fileUploadIp) {
        this.fileUploadIp = fileUploadIp;
    }
    public Integer getFileLike() {
        return fileLike;
    }

    public void setFileLike(Integer fileLike) {
        this.fileLike = fileLike;
    }
    public String getFileTitle() {
        return fileTitle;
    }

    public void setFileTitle(String fileTitle) {
        this.fileTitle = fileTitle;
    }
    public String getFileLocation() {
        return fileLocation;
    }

    public void setFileLocation(String fileLocation) {
        this.fileLocation = fileLocation;
    }
    public String getFileType() {
        return fileType;
    }

    public void setFileType(String fileType) {
        this.fileType = fileType;
    }
    public String getFileSystem() {
        return fileSystem;
    }

    public void setFileSystem(String fileSystem) {
        this.fileSystem = fileSystem;
    }
    public String getFileRemoteUrl() {
        return fileRemoteUrl;
    }

    public void setFileRemoteUrl(String fileRemoteUrl) {
        this.fileRemoteUrl = fileRemoteUrl;
    }
    public String getFileInfo() {
        return fileInfo;
    }

    public void setFileInfo(String fileInfo) {
        this.fileInfo = fileInfo;
    }
    public String getFileOtherIntro() {
        return fileOtherIntro;
    }

    public void setFileOtherIntro(String fileOtherIntro) {
        this.fileOtherIntro = fileOtherIntro;
    }
    public Integer getFileDelete() {
        return fileDelete;
    }

    public void setFileDelete(Integer fileDelete) {
        this.fileDelete = fileDelete;
    }

    @Override
    public String toString() {
        return "FileMain{" +
            "fileId=" + fileId +
            ", fileUploadTime=" + fileUploadTime +
            ", fileUploadUsername=" + fileUploadUsername +
            ", fileUploadLocation=" + fileUploadLocation +
            ", fileUploadIp=" + fileUploadIp +
            ", fileLike=" + fileLike +
            ", fileTitle=" + fileTitle +
            ", fileLocation=" + fileLocation +
            ", fileType=" + fileType +
            ", fileSystem=" + fileSystem +
            ", fileRemoteUrl=" + fileRemoteUrl +
            ", fileInfo=" + fileInfo +
            ", fileOtherIntro=" + fileOtherIntro +
            ", fileDelete=" + fileDelete +
        "}";
    }
}
