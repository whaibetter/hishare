package com.whai.blog.model;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import javax.validation.constraints.NotNull;
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
@TableName("blog_browse")
@ApiModel(value = "BlogBrowse对象", description = "")
public class BlogBrowse implements Serializable {

    private static final long serialVersionUID = 1L;


    @ApiModelProperty("浏览的id")
    @TableId(type = IdType.ASSIGN_UUID)
    private String browseId;

    @ApiModelProperty("浏览者的ip")
    @NotNull
    private String browseIp;

    @ApiModelProperty("浏览者的位置")
    @NotNull
    private String browseLocation;

    @ApiModelProperty("请求url")
    private String browseReqUrl;

    @ApiModelProperty("方法类型get post delete ...")
    private String browseReqMethod;

    @ApiModelProperty("请求参数")
    private String browseReqParam;

    @ApiModelProperty("响应内容")
    private String browseJsonResp;

    @ApiModelProperty("浏览时间")
    private Date browseTime;

    @ApiModelProperty("浏览器")
    private String browseBrowser;

    @ApiModelProperty("操作系统")
    private String browseOs;

    @ApiModelProperty("存入cookie")
    private String browseCookie;

    public BlogBrowse() {
    }

    public BlogBrowse(String browseId, String browseIp, String browseLocation, String browseReqUrl, String browseReqMethod, String browseReqParam, String browseJsonResp, Date browseTime, String browseBrowser, String browseOs, String browseCookie) {
        this.browseId = browseId;
        this.browseIp = browseIp;
        this.browseLocation = browseLocation;
        this.browseReqUrl = browseReqUrl;
        this.browseReqMethod = browseReqMethod;
        this.browseReqParam = browseReqParam;
        this.browseJsonResp = browseJsonResp;
        this.browseTime = browseTime;
        this.browseBrowser = browseBrowser;
        this.browseOs = browseOs;
        this.browseCookie = browseCookie;
    }

    public String getBrowseId() {
        return browseId;
    }

    public void setBrowseId(String browseId) {
        this.browseId = browseId;
    }
    public String getBrowseIp() {
        return browseIp;
    }

    public void setBrowseIp(String browseIp) {
        this.browseIp = browseIp;
    }
    public String getBrowseLocation() {
        return browseLocation;
    }

    public void setBrowseLocation(String browseLocation) {
        this.browseLocation = browseLocation;
    }
    public String getBrowseReqUrl() {
        return browseReqUrl;
    }

    public void setBrowseReqUrl(String browseReqUrl) {
        this.browseReqUrl = browseReqUrl;
    }
    public String getBrowseReqMethod() {
        return browseReqMethod;
    }

    public void setBrowseReqMethod(String browseReqMethod) {
        this.browseReqMethod = browseReqMethod;
    }
    public String getBrowseReqParam() {
        return browseReqParam;
    }

    public void setBrowseReqParam(String browseReqParam) {
        this.browseReqParam = browseReqParam;
    }
    public String getBrowseJsonResp() {
        return browseJsonResp;
    }

    public void setBrowseJsonResp(String browseJsonResp) {
        this.browseJsonResp = browseJsonResp;
    }
    public Date getBrowseTime() {
        return browseTime;
    }

    public void setBrowseTime(Date browseTime) {
        this.browseTime = browseTime;
    }
    public String getBrowseBrowser() {
        return browseBrowser;
    }

    public void setBrowseBrowser(String browseBrowser) {
        this.browseBrowser = browseBrowser;
    }
    public String getBrowseOs() {
        return browseOs;
    }

    public void setBrowseOs(String browseOs) {
        this.browseOs = browseOs;
    }
    public String getBrowseCookie() {
        return browseCookie;
    }

    public void setBrowseCookie(String browseCookie) {
        this.browseCookie = browseCookie;
    }

    @Override
    public String toString() {
        return "BlogBrowse{" +
            "browseId=" + browseId +
            ", browseIp=" + browseIp +
            ", browseLocation=" + browseLocation +
            ", browseReqUrl=" + browseReqUrl +
            ", browseReqMethod=" + browseReqMethod +
            ", browseReqParam=" + browseReqParam +
            ", browseJsonResp=" + browseJsonResp +
            ", browseTime=" + browseTime +
            ", browseBrowser=" + browseBrowser +
            ", browseOs=" + browseOs +
            ", browseCookie=" + browseCookie +
        "}";
    }
}
