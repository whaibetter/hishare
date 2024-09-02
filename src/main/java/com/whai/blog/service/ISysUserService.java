package com.whai.blog.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.whai.blog.model.SysUser;

/**
 * <p>
 * 用户管理 服务类
 * </p>
 *
 * @author whai
 * @since 2022-10-28
 */
public interface ISysUserService extends IService<SysUser> {

    boolean checkOldPassword(String oldPassword, String username);


    Integer updatePassword(String newPassword, SysUser sysUser);


    SysUser getUser(String userId);
}
