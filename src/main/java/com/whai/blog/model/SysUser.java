package com.whai.blog.model;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import javax.validation.constraints.Email;
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
@TableName("sys_user")
@ApiModel(value = "SysUser对象", description = "")
public class SysUser implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty("用户id")
    @TableId(value = "user_id", type = IdType.AUTO)
    private Integer userId;

    @ApiModelProperty("登陆账号，登陆id")
    private String userLoginName;

    @ApiModelProperty("用户昵称")
    private String userNickname;

    @Email(message = "请输入正确的邮箱")
    private String userEmail;

    private String userPhone;

    @ApiModelProperty("头像路径")
    private String userAvatar;

    @ApiModelProperty("密码")
    private String userPassword;

    @ApiModelProperty("账号状态")
    private String userStatus;

    @ApiModelProperty("最后登陆ip")
    private String userLoginIp;

    @ApiModelProperty("最后登陆时间")
    private Date userLoginDate;

    @ApiModelProperty("最后一次修改密码的时间")
    private Date userPwdUpdateDate;

    @ApiModelProperty("用户更新时间")
    private Date userInfoUpdateTime;

    @ApiModelProperty("用户技能表")
    private String userSkill;


    public String getUserSkill() {
        return userSkill;
    }

    public void setUserSkill(String userSkill) {
        this.userSkill = userSkill;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }
    public String getUserLoginName() {
        return userLoginName;
    }

    public void setUserLoginName(String userLoginName) {
        this.userLoginName = userLoginName;
    }
    public String getUserNickname() {
        return userNickname;
    }

    public void setUserNickname(String userNickname) {
        this.userNickname = userNickname;
    }
    public String getUserEmail() {
        return userEmail;
    }

    public void setUserEmail(String userEmail) {
        this.userEmail = userEmail;
    }
    public String getUserPhone() {
        return userPhone;
    }

    public void setUserPhone(String userPhone) {
        this.userPhone = userPhone;
    }
    public String getUserAvatar() {
        return userAvatar;
    }

    public void setUserAvatar(String userAvatar) {
        this.userAvatar = userAvatar;
    }
    public String getUserPassword() {
        return userPassword;
    }

    public void setUserPassword(String userPassword) {
        this.userPassword = userPassword;
    }

    public String getUserStatus() {
        return userStatus;
    }

    public void setUserStatus(String userStatus) {
        this.userStatus = userStatus;
    }
    public String getUserLoginIp() {
        return userLoginIp;
    }

    public void setUserLoginIp(String userLoginIp) {
        this.userLoginIp = userLoginIp;
    }
    public Date getUserLoginDate() {
        return userLoginDate;
    }

    public void setUserLoginDate(Date userLoginDate) {
        this.userLoginDate = userLoginDate;
    }
    public Date getUserPwdUpdateDate() {
        return userPwdUpdateDate;
    }

    public void setUserPwdUpdateDate(Date userPwdUpdateDate) {
        this.userPwdUpdateDate = userPwdUpdateDate;
    }
    public Date getUserInfoUpdateTime() {
        return userInfoUpdateTime;
    }

    public void setUserInfoUpdateTime(Date userInfoUpdateTime) {
        this.userInfoUpdateTime = userInfoUpdateTime;
    }

    public SysUser() {
    }

    public SysUser(SysUser sysUser) {
        this.userId = sysUser.userId;
        this.userLoginName = sysUser.userLoginName;
        this.userNickname = sysUser.userNickname;
        this.userEmail = sysUser.userEmail;
        this.userPhone = sysUser.userPhone;
        this.userAvatar = sysUser.userAvatar;
        this.userPassword = sysUser.userPassword;
        this.userStatus = sysUser.userStatus;
        this.userLoginIp = sysUser.userLoginIp;
        this.userLoginDate = sysUser.userLoginDate;
        this.userPwdUpdateDate = sysUser.userPwdUpdateDate;
        this.userInfoUpdateTime = sysUser.userInfoUpdateTime;
        this.userSkill = sysUser.userSkill;
    }



    @Override
    public String toString() {
        return "SysUser{" +
                "userId=" + userId +
                ", userLoginName='" + userLoginName + '\'' +
                ", userNickname='" + userNickname + '\'' +
                ", userEmail='" + userEmail + '\'' +
                ", userPhone='" + userPhone + '\'' +
                ", userAvatar='" + userAvatar + '\'' +
                ", userPassword='" + userPassword + '\'' +
                ", userStatus='" + userStatus + '\'' +
                ", userLoginIp='" + userLoginIp + '\'' +
                ", userLoginDate=" + userLoginDate +
                ", userPwdUpdateDate=" + userPwdUpdateDate +
                ", userInfoUpdateTime=" + userInfoUpdateTime +
                ", userSkill='" + userSkill + '\'' +
                '}';
    }


}
