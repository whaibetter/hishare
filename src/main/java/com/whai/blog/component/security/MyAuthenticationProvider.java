package com.whai.blog.component.security;

import com.whai.blog.model.LoginUser;
import com.whai.blog.service.impl.SysUserServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;

/**
 * 指示一个类可以处理特定的authentication实现。
 * 处理认证逻辑的接口标准, 我们可以实现这个类以便实现自己的认证逻辑.
 */
@Component
public class MyAuthenticationProvider implements AuthenticationProvider {




    @Autowired
    private SysUserServiceImpl sysUserService;
    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;



    /**
     * 使用与AuthenticationManager.authenticate(身份验证)相同的契约执行身份验证。
     *
     * 对用户进行效验用户名密码
     * @param authentication
     * @return
     * @throws AuthenticationException
     */
    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {


        final String username = authentication.getName();
        //前方传来的密码
        final String pwd = authentication.getCredentials().toString();


        /**
         * 从数据库中获取密码
         */
        LoginUser loginUser = (LoginUser) sysUserService.loadUserByUsername(username);
        boolean matches = bCryptPasswordEncoder.matches(pwd, loginUser.getPassword());
        if (matches){
            UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken = new UsernamePasswordAuthenticationToken(loginUser, pwd, sysUserService.getAuthorities(null));
            return usernamePasswordAuthenticationToken;
        }

        throw new AuthenticationException("用户密码错误") {
        };
    }

//    /**
//     * 这里还没进行权限的增加，还需要补充，一律都是admin
//     * @return
//     */
//    public Collection<GrantedAuthority> getAuthorities(Integer userId) {
//        List<GrantedAuthority> authList = new ArrayList<GrantedAuthority>();
//        //注意：这里每个权限前面都要加ROLE_。否在最后验证不会通过
//        authList.add(new SimpleGrantedAuthority("ROLE_admin"));
//        return authList;
//    }


    /**
     * 如果此AuthenticationProvider支持指定的Authentication对象，则返回true。
     * @param authentication
     * @return
     */
    @Override
    public boolean supports(Class<?> authentication) {
        return true;
    }

}
