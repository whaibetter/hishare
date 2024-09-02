package com.whai.blog.component.security;

import com.alibaba.druid.support.json.JSONUtils;
import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.whai.blog.model.LoginUser;
import com.whai.blog.model.SysUser;
import com.whai.blog.service.ISysUserService;
import com.whai.blog.service.impl.TokenServiceImpl;
import com.whai.blog.utils.AjaxResult;
import com.whai.blog.utils.SecurityCustomUtils;
import com.whai.blog.utils.ServletUtils;
import com.whai.blog.utils.authenticate.JwtUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.nio.charset.StandardCharsets;


@Component
public class MyAuthenticationSuccessHandler extends SimpleUrlAuthenticationSuccessHandler {

    private static final Logger logger = LoggerFactory.getLogger(MyAuthenticationSuccessHandler.class);


    @Autowired
    JwtUtils jwtUtils;

    @Autowired
    ISysUserService sysUserService;

    @Autowired
    TokenServiceImpl tokenService;

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {
        String currentUser = authentication.getName();
        logger.info("用户"+currentUser+"登录成功");

        response.setContentType("application/json;charset=UTF-8");

        // 生成JWT，并放置到cookie中
        String jwt = jwtUtils.generateToken(authentication.getName());

        ServletOutputStream outputStream = response.getOutputStream();

        AjaxResult result = AjaxResult.success("登陆成功!");
        // 存入token给前端
        result.put(jwtUtils.getHeader(), jwt);

        //封装user给前端
        QueryWrapper<SysUser> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("user_login_name", currentUser);
        SysUser user = sysUserService.getOne(queryWrapper);
        user.setUserLoginIp(ServletUtils.getIpAddress());
        sysUserService.updateById(user);
        user.setUserPassword(null);

        // 登陆成功放入Redis ，等每次请求都需要检查redis中有没有,
        // 有效期只有30分钟，但每次请求都会检查过期时间是否相差20分钟，如果小于20分钟会自动刷新缓存的有效时间  JwtAuthenticationFilter中verifyToken
        // 存入缓存
        LoginUser principalUser = SecurityCustomUtils.getPrincipalUser();
        principalUser.setToken(jwt);
        tokenService.refreshToken(principalUser);






        result.put("userInfo", JSON.toJSON(user));
        outputStream.write(JSONUtils.toJSONString(result).getBytes(StandardCharsets.UTF_8));

    }


}
