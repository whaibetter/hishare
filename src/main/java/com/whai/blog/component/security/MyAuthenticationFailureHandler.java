package com.whai.blog.component.security;

import com.alibaba.druid.support.json.JSONUtils;
import com.whai.blog.utils.AjaxResult;
import com.whai.blog.utils.exception.CaptchaException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationFailureHandler;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.nio.charset.StandardCharsets;

@Component
public class MyAuthenticationFailureHandler extends SimpleUrlAuthenticationFailureHandler {
    private static final Logger logger = LoggerFactory.getLogger(MyAuthenticationFailureHandler.class);
    @Override
    public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response, AuthenticationException exception) throws IOException, ServletException {
        logger.info("登录失败");
        response.setContentType("application/json;charset=UTF-8");


        ServletOutputStream outputStream = response.getOutputStream();


        String errorMessage = "用户名或密码错误";
        AjaxResult result;
        if (exception instanceof CaptchaException) {
            errorMessage = "验证码错误";
            result = AjaxResult.error(errorMessage);
        } else {
            result = AjaxResult.error(errorMessage);

        }
        outputStream.write(JSONUtils.toJSONString(result).getBytes(StandardCharsets.UTF_8));
        outputStream.flush();
        outputStream.close();

    }
}
