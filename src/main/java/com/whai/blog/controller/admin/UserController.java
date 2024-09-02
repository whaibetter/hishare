package com.whai.blog.controller.admin;


import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.whai.blog.annotation.Log;
import com.whai.blog.component.redis.RedisCache;
import com.whai.blog.constant.CacheConstants;
import com.whai.blog.model.SysUser;
import com.whai.blog.service.ISysUserService;
import com.whai.blog.utils.AjaxResult;
import com.whai.blog.utils.HttpStatus;
import com.whai.blog.utils.SecurityCustomUtils;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.constraints.NotNull;
import java.util.concurrent.TimeUnit;

/**
 * 管理员用户管理
 */
@Controller
@RequestMapping("/admin/user")
@Slf4j
public class UserController {


    private static final Logger logger = LoggerFactory.getLogger(UserController.class);

    private String prefix = "";

    @Autowired
    ISysUserService sysUserService;

    @Autowired
    private RedisCache redisCache;

    /**
     * 跳转blog页面
     * @return
     */
    @GetMapping("toUserList")
    public String userList()
    {
        return prefix + "/admin/userList";
    }

    /**
     * 用户列表
     * @param page 第几页
     * @return
     */
    @GetMapping("userList/{page}")
    @ResponseBody
    @ApiOperation("用户列表")
//    @Log(value = "获取user",BUSINESS_TYPE = Log.BusinessType.SELECT)
    public AjaxResult getBlogList(@ApiParam("页码") @PathVariable  Integer page) {

        //分页 每页显示8条数据
        Page<SysUser> blogMainIPage = new Page<SysUser>(page, 8);
        //条件筛选
        QueryWrapper<SysUser> select = new QueryWrapper<SysUser>();

        Page<SysUser> sysUser = sysUserService.page(blogMainIPage, select);
        //blog.hasNext用于判断下一页

        return AjaxResult.success( "success", sysUser);
    }


    /**
     * 用户列表
     * @return
     */
    @GetMapping("user")
    @ResponseBody
    @ApiOperation("某个用户信息")
    public AjaxResult getUser(@ApiParam("用户id") @RequestParam("userId") String userId) {

        String key = CacheConstants.LOGIN_USER_KEY + userId;

        JSONObject user = redisCache.getCacheObject(key);
        SysUser sysUser;
        if (user!=null){
            sysUser = user.toJavaObject(SysUser.class);
            return AjaxResult.success( "success", sysUser);
        }
        //缓存中没有
        //条件筛选
        QueryWrapper<SysUser> select = new QueryWrapper<SysUser>();
        select.eq("user_id", userId);
        sysUser = sysUserService.getOne(select);

        redisCache.setCacheObject(CacheConstants.LOGIN_USER_KEY + userId, sysUser, 30 , TimeUnit.MINUTES);

        return AjaxResult.success( "success", sysUser);
    }


    @ApiOperation("更新用户")
    @PostMapping("updateUser")
    @ResponseBody
    @Log(value = "更新user",BUSINESS_TYPE = Log.BusinessType.UPDATE)
    public AjaxResult updateUser(@NotNull @Validated @ApiParam("博客内容") @RequestBody SysUser sysUser) {

        boolean updateById = sysUserService.updateById(sysUser);
        SysUser user = sysUserService.getById(sysUser.getUserId());
        redisCache.setCacheObject(CacheConstants.LOGIN_USER_KEY + sysUser.getUserId(), user, 30 , TimeUnit.MINUTES);
        AjaxResult result;
        if (updateById){
            result = AjaxResult.success( "更新用户"+sysUser.getUserLoginName()+"成功");
            return result;
        }else {
            result = AjaxResult.error( "更新用户"+sysUser.getUserLoginName()+"失败");
            logger.error(result.toString());
            return result;
        }

    }


    @ApiOperation("更新密码")
    @PostMapping("updatePassword")
    @ResponseBody
    @Log(value = "更新密码",BUSINESS_TYPE = Log.BusinessType.UPDATE)
    public AjaxResult updatePassword(
            @RequestParam @ApiParam("旧密码") String oldPassword,
            @RequestParam @ApiParam("新密码") String newPassword
    ) {

        //先查找用户
        String name = SecurityCustomUtils.getAuthentication().getName();
        SysUser sysUser = sysUserService.getOne(new QueryWrapper<SysUser>().eq("user_login_name", name));
        if (sysUser==null){
            return new AjaxResult(404, "用户未找到");
        }
        //验证新旧密码
        boolean b = sysUserService.checkOldPassword(oldPassword, name);
        if (!b){
            //密码新旧密码不匹配
            return new AjaxResult(HttpStatus.BAD_REQUEST, "旧密码错误");
        }
        //更新
        Integer integer = sysUserService.updatePassword(newPassword, sysUser);
        if (integer == 0) {
            return new AjaxResult(HttpStatus.ERROR, "更新失败");
        }

        return AjaxResult.success( "更新成功");

    }




}
