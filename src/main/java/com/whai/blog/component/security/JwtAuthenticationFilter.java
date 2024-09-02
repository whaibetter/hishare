package com.whai.blog.component.security;

import com.whai.blog.model.LoginUser;
import com.whai.blog.service.impl.SysUserServiceImpl;
import com.whai.blog.service.impl.TokenServiceImpl;
import com.whai.blog.utils.StringUtils;
import com.whai.blog.utils.authenticate.JwtUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * 在首次登录成功后，MyAuthenticationSuccessHandler 并返回给前端。在之后的所有请求中（包括再次登录请求），都会携带此JWT信息。
 *  需要写一个JWT过滤器JwtAuthenticationFilter，当<b>前端发来的请求有JWT信息时，该过滤器将检验JWT是否正确以及是否过期</b>，若检验成功，则获取JWT中的用户名信息，检索数据库获得用户实体类，并将用户信息告知Spring Security，后续我们就能调用security的接口获取到当前登录的用户信息。
 *
 * 将生成JWT
 */
public class JwtAuthenticationFilter extends BasicAuthenticationFilter {

    @Autowired
    JwtUtils jwtUtils;

    @Autowired
    SysUserServiceImpl userService;

    @Autowired
    TokenServiceImpl tokenService;


    public JwtAuthenticationFilter(AuthenticationManager authenticationManager) {
        super(authenticationManager);
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain) throws IOException, ServletException {
//
//        try {
//            //先从TokenService根据请求头的token获取缓存的loginUser
//            String token = request.getHeader(jwtUtils.getHeader());
//            if (token != null && !"".equals(token)) {
//                // 验证token是否有效
//                boolean expiration = jwtUtils.isTokenExpired(jwtUtils.getClaimsByToken(token));
//                if (expiration) {
//                    // 过期了，拦截访问
//                    chain.doFilter( request, response );
//                    return;
//                }
//            }
//
//            // 通过token获取redis中的缓存信息
//            LoginUser loginUser = tokenService.getLoginUser(token);
//            //使用tokenService验证loginUser是否过期
//            if (StringUtils.isNotNull(loginUser) && StringUtils.isNull(SecurityCustomUtils.getAuthentication())) {
//                // 验证token有效期
//                tokenService.verifyToken(loginUser);
//                // 构建UsernamePasswordAuthenticationToken
//                Collection<GrantedAuthority> authorities = userService.getAuthorities(null);
//                UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(loginUser, null, authorities);
//                authenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
//                SecurityContextHolder.getContext().setAuthentication(authenticationToken);
//            }
//
//        } catch (Exception e) {
//
//        }
//        chain.doFilter(request, response);

        //封装进入SecurityContext



        String jwt = request.getHeader(jwtUtils.getHeader());

        // 这里如果没有jwt，继续往后走，因为后面还有鉴权管理器等去判断是否拥有身份凭证，所以是可以放行的
        // 没有jwt相当于匿名访问，若有一些接口是需要权限的，则不能访问这些接口
        if (StringUtils.isNull(jwt)) {
            chain.doFilter(request, response);
            return;
        }



        LoginUser loginUser = tokenService.getLoginUser(jwt);
        if (StringUtils.isNotNull(loginUser)) {
            tokenService.verifyToken(loginUser);
            // 构建UsernamePasswordAuthenticationToken,这里密码为null，是因为提供了正确的JWT,实现自动登录
            UsernamePasswordAuthenticationToken token = new UsernamePasswordAuthenticationToken(loginUser, null, userService.getAuthorities(null));
            SecurityContextHolder.getContext().setAuthentication(token);
        }

        chain.doFilter(request, response);




//        // 把数据库的user 封装到登陆LoginUser extends UserDetails中
//        QueryWrapper<SysUser> select = new QueryWrapper<SysUser>().eq("user_login_name", username);
//        loginUser.setToken(jwt);
//        loginUser.setUser(userService.getOne(select));



        //将用户存入缓存，在使用在线用户时可以调用查看
//        tokenService.verifyToken(user);

        // 获取用户的权限等信息

//        QueryWrapper<SysUser> queryWrapper = new QueryWrapper<>();
//        queryWrapper.eq("user_login_name", username);
//        SysUser sysUser = userService.getOne(queryWrapper);





        /**
         * 若JWT验证成功，我们构建了一个UsernamePasswordAuthenticationToken对象，
         * 用于保存用户信息，之后将该对象交给SecurityContextHolder，set进它的context中,这样后续我们就能通过
         * 调用SecurityContextHolder.getContext().getAuthentication().getPrincipal()等方法获取到当前登录的用户信息了。

         */


    }
}
