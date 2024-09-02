package com.whai.blog.controller.pub;

import com.whai.blog.model.SysUser;
import com.whai.blog.service.ISysUserService;
import com.whai.blog.utils.AjaxResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


/**
 * 注册
 */
@RestController
public class PubUserController {

    @Autowired
    ISysUserService sysUserService;

    @RequestMapping("/user")
    public AjaxResult userGet(){
        SysUser sysUser = sysUserService.getUser("1");
        return sysUser==null?AjaxResult.error("获取失败"):AjaxResult.success( "success", sysUser);
    }

}
