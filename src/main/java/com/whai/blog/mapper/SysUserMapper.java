package com.whai.blog.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.whai.blog.model.SysUser;
import org.springframework.stereotype.Repository;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author whai
 * @since 2022-10-28
 */
@Repository
public interface SysUserMapper extends BaseMapper<SysUser> {

    SysUser getUser(String userId);
}
