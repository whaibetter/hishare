package com.whai.blog.controller.admin;


import com.whai.blog.component.redis.RedisCache;
import com.whai.blog.model.LoginUser;
import com.whai.blog.service.impl.SysUserOnlineServiceImpl;
import com.whai.blog.service.impl.TokenServiceImpl;
import com.whai.blog.utils.AjaxResult;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 用户在线情况
 */
@RestController
@RequestMapping("/admin/online")
public class UserOnlineController {


    @Autowired
    TokenServiceImpl tokenService;

    @Autowired
    RedisCache redisCache;

    @Autowired
    private SysUserOnlineServiceImpl sysUserOnlineService;

    @GetMapping("onlineList")
    @ApiOperation("在线用户")
    public AjaxResult getOnlineList() {

        List<LoginUser> loginUsers = sysUserOnlineService.onlineUser();


        return loginUsers.isEmpty() ?
                AjaxResult.success("未获取到在线用户！")
                :
                AjaxResult.success("发现到" + loginUsers.size() + "个用户在线！", loginUsers);
    }


    @DeleteMapping("offline")
    @ApiOperation("下线用户")
    public AjaxResult offline(@RequestParam String username) {
        boolean offline = sysUserOnlineService.offline(username);
        return offline ? AjaxResult.success() : AjaxResult.error("下线失败！");

    }


}
