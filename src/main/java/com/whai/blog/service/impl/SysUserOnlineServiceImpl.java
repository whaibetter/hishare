package com.whai.blog.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.whai.blog.component.redis.RedisCache;
import com.whai.blog.constant.CacheConstants;
import com.whai.blog.mapper.SysUserOnlineMapper;
import com.whai.blog.model.LoginUser;
import com.whai.blog.model.SysUserOnline;
import com.whai.blog.service.ISysUserOnlineService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * <p>
 *  在线用户，登陆记录服务实现类
 * </p>
 *
 * @author whai
 * @since 2022-10-28
 */
@Service
public class SysUserOnlineServiceImpl extends ServiceImpl<SysUserOnlineMapper, SysUserOnline> implements ISysUserOnlineService {


    @Autowired
    private RedisCache redisCache;

    @Autowired
    private SysUserOnlineMapper sysUserOnlineMapper;


    /**
     * 查询出全部的在线用户
     * @return
     */
    public List<LoginUser> onlineUser(){
        //获取key
        Collection<String> keys = redisCache.keys(CacheConstants.LOGIN_TOKEN_KEY + "*");

        List<LoginUser> userOnlineList = new ArrayList<LoginUser>();

        LoginUser loginUser = null;
        for (String key : keys) {
            JSONObject cacheUser = redisCache.getCacheObject(key);
            loginUser = cacheUser.toJavaObject(LoginUser.class);
            userOnlineList.add(loginUser);
        }

        return userOnlineList;
    }


    @Override
    public boolean offline(String username) {
        return redisCache.deleteObject(CacheConstants.LOGIN_TOKEN_KEY + username);
    }
}
