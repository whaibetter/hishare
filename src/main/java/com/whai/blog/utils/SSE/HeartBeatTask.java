package com.whai.blog.utils.SSE;

import lombok.extern.slf4j.Slf4j;

import java.util.Date;


/**
 * 心跳线程
 */
@Slf4j
public class HeartBeatTask implements Runnable {


    Integer heardCount = 0;

    private final String clientId;

    public HeartBeatTask(String clientId) {
        // 这里可以按照业务传入需要的数据
        this.clientId = clientId;
    }

    @Override
    public void run() {
        log.info("MSG: SseHeartbeat 心跳发送 | ID: {} | Date: {}", clientId, new Date());
        if (!SSESession.send(clientId, "ping")) {
            log.warn("心跳发送失败！");
        }
    }
}

