package com.whai.blog.utils.exception;

/**
 * SSE异常信息
 *
 * @author zuster
 * @date 2021/1/5
 */
public class SSEException extends RuntimeException {
    public SSEException() {
    }

    public SSEException(String message) {
        super(message);
    }

    public SSEException(String message, Throwable cause) {
        super(message, cause);
    }

    public SSEException(Throwable cause) {
        super(cause);
    }

    public SSEException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
