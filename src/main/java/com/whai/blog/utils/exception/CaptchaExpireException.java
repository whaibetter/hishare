package com.whai.blog.utils.exception;

/**
 * 验证码失效异常类
 *
 * @author ruoyi
 */
public class CaptchaExpireException extends Exception
{
    private static final long serialVersionUID = 1L;

    public CaptchaExpireException()
    {
        super("过期了");
    }
}
