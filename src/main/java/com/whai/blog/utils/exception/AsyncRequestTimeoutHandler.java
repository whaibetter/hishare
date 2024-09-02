//package com.whai.blog.utils.exception;
//
//import lombok.extern.slf4j.Slf4j;
//import org.springframework.http.HttpStatus;
//import org.springframework.web.bind.annotation.ExceptionHandler;
//import org.springframework.web.bind.annotation.ResponseStatus;
//import org.springframework.web.bind.annotation.RestControllerAdvice;
//import org.springframework.web.context.request.async.AsyncRequestTimeoutException;
//
//@RestControllerAdvice
//@Slf4j
//public class AsyncRequestTimeoutHandler {
//
//    @ResponseStatus(HttpStatus.NOT_MODIFIED)
//    @ExceptionHandler(AsyncRequestTimeoutException.class)
//    public String asyncRequestTimeoutHandler(AsyncRequestTimeoutException e) {
//        log.error("异步请求超时:" + e.getMessage());
//        return "304";
//    }
//}
