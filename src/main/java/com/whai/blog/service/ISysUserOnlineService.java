package com.whai.blog.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.whai.blog.model.SysUserOnline;

/**
 * <p>
 * 用户登陆 服务类
 * </p>
 *
 * @author whai
 * @since 2022-10-28
 */
public interface ISysUserOnlineService extends IService<SysUserOnline> {

    boolean offline(String username);
}
