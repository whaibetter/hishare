package com.whai.blog.service.impl;


import com.alibaba.fastjson.JSONObject;
import com.whai.blog.component.redis.RedisCache;
import com.whai.blog.constant.CacheConstants;
import com.whai.blog.model.LoginUser;
import com.whai.blog.utils.ServletUtils;
import com.whai.blog.utils.StringUtils;
import com.whai.blog.utils.authenticate.JwtUtils;
import io.jsonwebtoken.Claims;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.concurrent.TimeUnit;

@Service
public class TokenServiceImpl {


    @Value("${whai.jwt.expire}")
    private int expireTime;

    @Autowired
    private RedisCache redisCache;

    @Autowired
    JwtUtils jwtUtils;


    /**
     * 刷新令牌有效期
     *
     * @param loginUser 登录信息
     */
    public void refreshToken(LoginUser loginUser)
    {
        loginUser.setLoginTime(System.currentTimeMillis());
        loginUser.setExpireTime(loginUser.getLoginTime() + expireTime * 1000L);
        loginUser.setBrowserAndOs(ServletUtils.getOsAndBrowserInfo());
        loginUser.setIpaddr(ServletUtils.getIpAddress());
        loginUser.setLoginLocation(ServletUtils.getAddressByIP(ServletUtils.getIpAddress()));
        loginUser.getUser().setUserPassword(null);

        // 根据uuid将loginUser缓存
        redisCache.setCacheObject(CacheConstants.LOGIN_TOKEN_KEY + loginUser.getUsername(), loginUser, 30 , TimeUnit.MINUTES);
    }

    /**
     * 设置用户身份信息
     */
    public void setLoginUser(LoginUser loginUser)
    {
        if (StringUtils.isNotNull(loginUser) && StringUtils.isNotEmpty(loginUser.getToken()))
        {
            refreshToken(loginUser);
        }
    }


    /**
     * 验证令牌有效期，相差不足20分钟，自动刷新缓存
     * @param loginUser
     * @return 令牌
     */
    public void verifyToken(LoginUser loginUser)
    {
        long expireTime = loginUser.getExpireTime();
        long currentTime = System.currentTimeMillis();
        if (expireTime - currentTime <= 20 * 60 * 1000L)
        {
            // 过期就刷新jwt
            refreshToken(loginUser);
        }
    }

    /**
     * 获取用户身份信息
     *
     * @return 用户信息
     */
    public LoginUser getLoginUser(String token) {

        // 获取请求携带的令牌
        if (StringUtils.isNotEmpty(token)) {

            JSONObject user = null;
            try {
                Claims claims = jwtUtils.getClaimsByToken(token);
                // 解析对应的权限以及用户信息
                user = redisCache.getCacheObject(CacheConstants.LOGIN_TOKEN_KEY + claims.getSubject());

                LoginUser loginUser = user == null ? null : user.toJavaObject(LoginUser.class);
                return loginUser;
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return null;
    }

}
