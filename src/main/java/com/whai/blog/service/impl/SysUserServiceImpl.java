package com.whai.blog.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.whai.blog.mapper.SysUserMapper;
import com.whai.blog.model.LoginUser;
import com.whai.blog.model.SysUser;
import com.whai.blog.service.ISysUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author whai
 * @since 2022-10-28
 */
@Service
public class SysUserServiceImpl extends ServiceImpl<SysUserMapper, SysUser> implements ISysUserService, UserDetailsService {

    @Autowired
    SysUserMapper userMapper;

    @Autowired
    HttpSession session;

    @Autowired
    BCryptPasswordEncoder encoder;
    /**
     *  UserDetailsService：
     * Core interface which loads user-specific data.
     * <p>
     * It is used throughout the framework as a user DAO and is the strategy used by the
     * {@link org.springframework.security.authentication.dao.DaoAuthenticationProvider
     * DaoAuthenticationProvider}.
     *
     * <p>
     * The interface requires only one read-only method, which simplifies support for new
     * data-access strategies.
     * <link>https://blog.csdn.net/caplike/article/details/105895725</link>
     *
     *
     * 1. UserDetailsService 查找数据库的逻辑，包括role，最后转为Security的UserDetail、User类返回
     * 2. AuthenticationProvider 效验器 认证逻辑的接口标准,即以什么方式认认证，可以在SecurityConfig中配置自定义的校验器
     *
     * @param username
     * @return
     * @throws UsernameNotFoundException
     */
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        //去数据库查找用户
        SysUser sysUser = userMapper.selectOne(new QueryWrapper<SysUser>().eq("user_login_name", username));

        if (sysUser == null){
            throw new UsernameNotFoundException("用户不存在");
        }
        UserDetails userDetails = new LoginUser(sysUser);
        return userDetails;
    }


    /**
     * 这里还没进行权限的增加，还需要补充，一律都是admin
     * @return
     */
    public Collection<GrantedAuthority> getAuthorities(Integer userId) {
        List<GrantedAuthority> authList = new ArrayList<GrantedAuthority>();
        //注意：这里每个权限前面都要加ROLE_。否在最后验证不会通过
        authList.add(new SimpleGrantedAuthority("ROLE_admin"));
        return authList;
    }


    /**
     * 效验旧的密码
     * @param oldPassword
     * @param username
     * @return
     */
    @Override
    public boolean checkOldPassword(String oldPassword, String username) {
        String dataBasePassword = userMapper.selectOne(new QueryWrapper<SysUser>().eq("user_login_name", username)).getUserPassword();
        //验证是否匹配密码
        boolean matches = encoder.matches(oldPassword, dataBasePassword);

        return matches;
    }

    @Override
    public Integer updatePassword(String newPassword, SysUser sysUser) {
        sysUser.setUserPassword(encoder.encode(newPassword));
        int i = userMapper.updateById(sysUser);
        return i;
    }

    @Override
    public SysUser getUser(String userId) {
        return userMapper.getUser(userId);
    }

}
