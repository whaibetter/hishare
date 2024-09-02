package com.whai.blog.model;

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
@TableName("file_download")
@ApiModel(value = "FileDownload对象", description = "")
public class FileDownload implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty("下载操作的id")
    @TableId
    private Long fileDownloadId;

    @ApiModelProperty("下载的ip")
    private String fileDownloadIp;

    @ApiModelProperty("下载者的地址")
    private String fileDownloadLocation;

    @ApiModelProperty("下载文件的id")
    private String fileDownloadFile;

    @ApiModelProperty("下载时间")
    private Date fileDownloadTime;

    @ApiModelProperty("请求url")
    private String fileDownloadUrl;

    @ApiModelProperty("响应情况")
    private String fileDownloadRespStatus;

    public Long getFileDownloadId() {
        return fileDownloadId;
    }

    public void setFileDownloadId(Long fileDownloadId) {
        this.fileDownloadId = fileDownloadId;
    }
    public String getFileDownloadIp() {
        return fileDownloadIp;
    }

    public void setFileDownloadIp(String fileDownloadIp) {
        this.fileDownloadIp = fileDownloadIp;
    }
    public String getFileDownloadLocation() {
        return fileDownloadLocation;
    }

    public void setFileDownloadLocation(String fileDownloadLocation) {
        this.fileDownloadLocation = fileDownloadLocation;
    }
    public String getFileDownloadFile() {
        return fileDownloadFile;
    }

    public void setFileDownloadFile(String fileDownloadFile) {
        this.fileDownloadFile = fileDownloadFile;
    }
    public Date getFileDownloadTime() {
        return fileDownloadTime;
    }

    public void setFileDownloadTime(Date fileDownloadTime) {
        this.fileDownloadTime = fileDownloadTime;
    }
    public String getFileDownloadUrl() {
        return fileDownloadUrl;
    }

    public void setFileDownloadUrl(String fileDownloadUrl) {
        this.fileDownloadUrl = fileDownloadUrl;
    }
    public String getFileDownloadRespStatus() {
        return fileDownloadRespStatus;
    }

    public void setFileDownloadRespStatus(String fileDownloadRespStatus) {
        this.fileDownloadRespStatus = fileDownloadRespStatus;
    }

    @Override
    public String toString() {
        return "FileDownload{" +
            "fileDownloadId=" + fileDownloadId +
            ", fileDownloadIp=" + fileDownloadIp +
            ", fileDownloadLocation=" + fileDownloadLocation +
            ", fileDownloadFile=" + fileDownloadFile +
            ", fileDownloadTime=" + fileDownloadTime +
            ", fileDownloadUrl=" + fileDownloadUrl +
            ", fileDownloadRespStatus=" + fileDownloadRespStatus +
        "}";
    }
}
