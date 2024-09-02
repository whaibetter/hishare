package com.whai.blog.service;

import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

public interface ISSEService {

    public SseEmitter connect(String clientId, Long timeout);

    public SseEmitter connect(String clientId);

    public String send(String clientId, Object message);

    public String close(String clientId);

    public Integer broadcast(Object message);

}
