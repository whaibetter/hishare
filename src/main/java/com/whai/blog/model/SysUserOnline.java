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
 * 作为登陆的记录
 * </p>
 *
 * @author whai
 * @since 2022-10-29
 */
@TableName("sys_user_online")
@ApiModel(value = "SysUserOnline对象", description = "登陆记录")
public class SysUserOnline implements Serializable {

    private static final long serialVersionUID = 1L;


    @TableId(type = IdType.ASSIGN_UUID)
    @ApiModelProperty(value = "登陆的session id")
    private String sessionId;

    @ApiModelProperty("用户名")
    private String userLoginName;

    @ApiModelProperty("登陆的ip")
    private String userIpaddr;

    @ApiModelProperty("登陆地点")
    private String loginLocation;

    @ApiModelProperty("浏览器类型")
    private String browser;

    @ApiModelProperty("操作系统类型")
    private String os;

    @ApiModelProperty("在线状态")
    private String status;

    @ApiModelProperty("session创建时间")
    private Date sessionStartTime;

    @ApiModelProperty("session最后一次的时间")
    private Date sessionLastTime;

    public String getSessionId() {
        return sessionId;
    }

    public void setSessionId(String sessionId) {
        this.sessionId = sessionId;
    }
    public String getUserLoginName() {
        return userLoginName;
    }

    public void setUserLoginName(String userLoginName) {
        this.userLoginName = userLoginName;
    }
    public String getUserIpaddr() {
        return userIpaddr;
    }

    public void setUserIpaddr(String userIpaddr) {
        this.userIpaddr = userIpaddr;
    }
    public String getLoginLocation() {
        return loginLocation;
    }

    public void setLoginLocation(String loginLocation) {
        this.loginLocation = loginLocation;
    }
    public String getBrowser() {
        return browser;
    }

    public void setBrowser(String browser) {
        this.browser = browser;
    }
    public String getOs() {
        return os;
    }

    public void setOs(String os) {
        this.os = os;
    }
    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
    public Date getSessionStartTime() {
        return sessionStartTime;
    }

    public void setSessionStartTime(Date sessionStartTime) {
        this.sessionStartTime = sessionStartTime;
    }
    public Date getSessionLastTime() {
        return sessionLastTime;
    }

    public void setSessionLastTime(Date sessionLastTime) {
        this.sessionLastTime = sessionLastTime;
    }

    @Override
    public String toString() {
        return "SysUserOnline{" +
            "sessionId=" + sessionId +
            ", userLoginName=" + userLoginName +
            ", userIpaddr=" + userIpaddr +
            ", loginLocation=" + loginLocation +
            ", browser=" + browser +
            ", os=" + os +
            ", status=" + status +
            ", sessionStartTime=" + sessionStartTime +
            ", sessionLastTime=" + sessionLastTime +
        "}";
    }
}
