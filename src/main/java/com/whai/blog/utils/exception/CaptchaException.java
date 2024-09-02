package com.whai.blog.utils.exception;

import org.springframework.security.core.AuthenticationException;


/**
 * 验证码错误
 */
public class CaptchaException extends AuthenticationException {

    public CaptchaException(String msg) {
        super(msg);
    }
}
