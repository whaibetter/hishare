package com.whai.blog.component.security;

import com.alibaba.druid.support.json.JSONUtils;
import com.whai.blog.utils.AjaxResult;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.nio.charset.StandardCharsets;


/**
 * 拒绝访问 用户权限不足时
 */
@Component
public class JwtAccessDeniedHandler implements AccessDeniedHandler {

    @Override
    public void handle(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, AccessDeniedException e) throws IOException, ServletException {
        httpServletResponse.setContentType("application/json;charset=UTF-8");
        httpServletResponse.setStatus(HttpServletResponse.SC_FORBIDDEN);
        ServletOutputStream outputStream = httpServletResponse.getOutputStream();



        AjaxResult error = new AjaxResult(HttpServletResponse.SC_UNAUTHORIZED, "权限不足", null);
        outputStream.write(JSONUtils.toJSONString(error).getBytes(StandardCharsets.UTF_8));
        outputStream.flush();
        outputStream.close();
    }
}
