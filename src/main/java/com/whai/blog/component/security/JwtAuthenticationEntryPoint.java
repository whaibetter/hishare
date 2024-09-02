package com.whai.blog.component.security;

import com.whai.blog.utils.AjaxResult;
import com.whai.blog.utils.ServletUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 *  JWT认证失败处理器JwtAuthenticationEntryPoint
 */
@Component
@Slf4j
public class JwtAuthenticationEntryPoint implements AuthenticationEntryPoint {

    @Override
    public void commence(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, AuthenticationException e) throws IOException, ServletException {
        String message = "访问" + httpServletRequest.getRequestURI() +"授权失败，请重新登录！ e.message:" + e.getMessage();
        log.error(message);
        ServletUtils.renderAjax(httpServletResponse, AjaxResult.error(message));
    }
}
