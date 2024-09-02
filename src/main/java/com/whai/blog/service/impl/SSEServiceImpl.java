package com.whai.blog.service.impl;

import com.whai.blog.service.ISSEService;
import com.whai.blog.utils.SSE.HeartBeatTask;
import com.whai.blog.utils.SSE.SSESession;
import com.whai.blog.utils.SSE.SseEmitterUTF8;
import com.whai.blog.utils.exception.SSEException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

import java.util.Date;
import java.util.Map;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.TimeUnit;

@Service
@Slf4j
public class SSEServiceImpl  implements ISSEService {

    /**
     * 发送心跳线程池
     */
    private static ScheduledExecutorService heartbeatExecutors = Executors.newScheduledThreadPool(8);



    @Override
    public SseEmitter connect(String clientId, Long timeout) {

        // 设置为0L为永不超时
        // 次数设置30秒超时,方便测试 timeout 事件
        SseEmitterUTF8 emitter = new SseEmitterUTF8(timeout);


        log.info("MSG: SseConnect 连接 | EmitterHash: {} | ID: {} | Date: {}", emitter.hashCode(), clientId, new Date());

        SSESession.add(clientId, emitter);


        /**
         * 每隔10秒给客户单发送一个消息，若连续3次未收到心跳，则客户端中断连接
         */
        final ScheduledFuture<?> future = heartbeatExecutors.scheduleAtFixedRate(new HeartBeatTask(clientId), 0, 10, TimeUnit.SECONDS);


        /**
         * 一定要在回调中处理掉Session和之前设置的Task，否则很容易OOM！
         */
        emitter.onCompletion(() -> {
            log.info("MSG: SseConnectCompletion 执行结束 | EmitterHash: {} |ID: {} | Date: {}", emitter.hashCode(), clientId, new Date());
            SSESession.onCompletion(clientId, future);
        });
        emitter.onTimeout(() -> {
            log.error("MSG: SseConnectTimeout 超时 | EmitterHash: {} |ID: {} | Date: {}", emitter.hashCode(), clientId, new Date());
            SSESession.onError(clientId, new SSEException("TimeOut(clientId: " + clientId + ")"));
        });
        emitter.onError(t -> {
            log.error("MSG: SseConnectError 发生错误 | EmitterHash: {} |ID: {} | Date: {}", emitter.hashCode(), clientId, new Date());
            SSESession.onError(clientId, new SSEException("Error(clientId: " + clientId + ")"));
        });

        return emitter;
    }

    @Override
    public SseEmitter connect(String clientId) {
        return connect(clientId, 0L);
    }

    @Override
    public String send(String clientId, Object message) {
        if (SSESession.send(clientId, message)) {
            return "Succeed!";
        }
        return "error";
    }

    @Override
    public String close(String clientId) {
        log.info("MSG: SseConnectClose 关闭连接 | ID: {} | Date: {}", clientId, new Date());
        if (SSESession.del(clientId)) return "Succeed!";
        return "Error!";
    }

    @Override
    public Integer broadcast(Object message) {
        Integer successCount = 0;
        Map<String, SseEmitterUTF8> sseEmitterMap = SSESession.getSseEmitterMap();
        for (String key : sseEmitterMap.keySet()) {
            boolean send = SSESession.send(key, message);
            if (send) {
                successCount++;
            }
        }
        return successCount;
    }
}
