package com.whai.blog.model;

import com.alibaba.fastjson.annotation.JSONField;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;

public class LoginUser implements UserDetails{
    private static final long serialVersionUID = 1L;

    /**
     * 用户信息
     */
    private SysUser user;

    /**
     * 登录时间
     */
    private Long loginTime;

    /**
     * 过期时间
     */
    private Long expireTime;

    /**
     * 登录IP地址
     */
    private String ipaddr;

    /**
     * 登录地点
     */
    private String loginLocation;

    /**
     * 浏览器类型 和操作系统
     */
    private String browserAndOs;


    public LoginUser(SysUser user) {
        this.user = user;
    }

    public LoginUser(SysUser user, Long loginTime, Long expireTime, String ipaddr, String loginLocation, String browserAndOs, String token) {
        this.user = user;
        this.loginTime = loginTime;
        this.expireTime = expireTime;
        this.ipaddr = ipaddr;
        this.loginLocation = loginLocation;
        this.browserAndOs = browserAndOs;
        this.token = token;
    }

    public LoginUser() {
    }

    public Long getLoginTime() {
        return loginTime;
    }

    public void setLoginTime(Long loginTime) {
        this.loginTime = loginTime;
    }

    public String getIpaddr() {
        return ipaddr;
    }

    public void setIpaddr(String ipaddr) {
        this.ipaddr = ipaddr;
    }

    public String getLoginLocation() {
        return loginLocation;
    }

    public void setLoginLocation(String loginLocation) {
        this.loginLocation = loginLocation;
    }

    public String getBrowserAndOs() {
        return browserAndOs;
    }

    public void setBrowserAndOs(String browserAndOs) {
        this.browserAndOs = browserAndOs;
    }

    public static long getSerialVersionUID() {
        return serialVersionUID;
    }

    public SysUser getUser() {
        return user;
    }

    public Long getExpireTime() {
        return expireTime;
    }

    public void setExpireTime(Long expireTime) {
        this.expireTime = expireTime;
    }

    public void setUser(SysUser user) {
        this.user = user;
    }

    private String token;

    public Integer getUserId()
    {
        return user.getUserId();
    }

    public String getToken()
    {
        return token;
    }

    public void setToken(String token)
    {
        this.token = token;
    }

    @JSONField(serialize = false)
    @Override
    public String getPassword()
    {
        return user.getUserPassword();
    }

    @Override
    public String getUsername()
    {
        return user.getUserLoginName();
    }

    /**
     * 账户是否未过期,过期无法验证
     */
    @JSONField(serialize = false)
    @Override
    public boolean isAccountNonExpired()
    {
        return true;
    }

    /**
     * 指定用户是否解锁,锁定的用户无法进行身份验证
     *
     * @return
     */
    @JSONField(serialize = false)
    @Override
    public boolean isAccountNonLocked()
    {
        return true;
    }

    /**
     * 指示是否已过期的用户的凭据(密码),过期的凭据防止认证
     *
     * @return
     */
    @JSONField(serialize = false)
    @Override
    public boolean isCredentialsNonExpired()
    {
        return true;
    }

    /**
     * 是否可用 ,禁用的用户不能身份验证
     *
     * @return
     */
    @JSONField(serialize = false)
    @Override
    public boolean isEnabled()
    {
        return true;
    }


    @Override
    public Collection<? extends GrantedAuthority> getAuthorities()
    {
        return null;
    }

    @Override
    public String toString() {
        return "LoginUser{" +
                "user=" + user +
                ", loginTime=" + loginTime +
                ", expireTime=" + expireTime +
                ", ipaddr='" + ipaddr + '\'' +
                ", loginLocation='" + loginLocation + '\'' +
                ", browserAndOs='" + browserAndOs + '\'' +
                ", token='" + token + '\'' +
                '}';
    }
}
