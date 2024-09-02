package com.whai.blog.controller.admin;


import com.whai.blog.service.ISignInService;
import com.whai.blog.utils.AjaxResult;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;
import java.util.Set;

@Api("管理员签到")
@RestController
@RequestMapping("/signin")
public class SignInController {

    @Autowired
    ISignInService signInService;

    // 签到
    @PostMapping("/")
    @ApiOperation("签到")
    public AjaxResult signIn() {
        signInService.signIn();
        return AjaxResult.success();
    }

    // 补签，还需要晚上一天只能补签一次，补签记录等
    @PostMapping("/replenishSignIn")
    @ApiOperation("补签某天")
    public AjaxResult signInReplenish(Integer day) {
        signInService.signIn(day);
        return AjaxResult.success();
    }

    // 签到
    @GetMapping("/userSignInStatus")
    @ApiOperation("获取所有用户签到情况")
    public AjaxResult signInStatus() {
        Map<String, Set<Integer>> signInStatus = signInService.userSignStatus();
        return AjaxResult.success(signInStatus);
    }


    // 签到
    @GetMapping("/mySignInStatus")
    @ApiOperation("获取我的签到情况")
    public AjaxResult mySignInStatus() {
        Set<Integer> signInStatus = signInService.mySignStatus();
        return AjaxResult.success(signInStatus);
    }
}


