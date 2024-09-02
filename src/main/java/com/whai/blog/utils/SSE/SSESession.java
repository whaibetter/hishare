package com.whai.blog.utils.SSE;


import lombok.extern.slf4j.Slf4j;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

import java.io.IOException;
import java.util.Date;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ScheduledFuture;

@Slf4j
public class SSESession {

    // 缓存id 和 对应的SseEmitter
    private static Map<String,SseEmitterUTF8> sseEmitterMap = new ConcurrentHashMap<>();

    public static Map<String, SseEmitterUTF8> getSseEmitterMap() {
        return sseEmitterMap;
    }


    //    /**
//     * 建立SSE连接
//     * @param timeout
//     * @return
//     */
//    public SseEmitterUTF8 connect(Long timeout , String id) {
//        try {
//            // 关闭已经存在的SSE
//            if (sseEmitterMap.containsKey(id)) {
//                log.info("已经存在该SSE:{},关闭！", id);
//                over(id);
//            }
//            // 设置超时时间，0表示不过期。默认30秒
//            SseEmitterUTF8 sseEmitter = new SseEmitterUTF8(timeout);
//            // 注册回调
//            sseEmitter.onCompletion(() -> {
//                log.info("完成！");
//            });
//
//            sseEmitter.onError(throwable -> {
//                log.warn("连接错误！" + throwable.getMessage());
//            });
//
//
//            sseEmitter.onTimeout(() -> {
//                // 超时重连
//                over(id);
//                connect(timeout, id);
//                log.warn("连接超时");
//                // 超时重试，或退出
//            });
//
////            // 设置前端的重试时间为1s
////            sseEmitter.send(SseEmitterUTF8.event().name("msg").reconnectTime(1000).data());
//
//            sseEmitterMap.put(id, sseEmitter);
//            return sseEmitter;
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//        return null;
//    }
//
//
//    public void send(String id, AjaxResult messagePojo, MediaType mediaType) throws Exception {
//        if (sseEmitterMap.containsKey(id)) {
//            try {
//                SseEmitterUTF8 sseEmitter = sseEmitterMap.get(id);
//                // 这里 event name的msg对应前端的msg监听器 设置编码，返回格式
//                // 这里可以写数据发生变化时的逻辑，直接推送到前端
//                sseEmitter.send(SseEmitterUTF8.event().name("msg").reconnectTime(10000L).data(JSONObject.toJSON(messagePojo).toString(), mediaType));
//                log.info("后端发送{}到{}!", messagePojo, id);
//            } catch (IOException e) {
//                log.warn("推送到 {} 异常:{}", id, e.getMessage());
//            }
//        } else {
//            log.warn("不存在的id：" + id);
//            throw new RuntimeException("不存在的id：" + id);
//        }
//    }
//
//    public void send(String id, AjaxResult messagePojo) throws Exception {
//        send(id, messagePojo, MediaType.TEXT_EVENT_STREAM);
//    }


    public String over(String id) {
        SseEmitterUTF8 sseEmitter = sseEmitterMap.get(id);
        if (sseEmitter != null) {
            log.info("关闭SSE连接:" + id);
            sseEmitter.complete();
            sseEmitterMap.remove(id);
        }
        return "over";
    }

    /**
     * 是否存在该线程
     * @param id
     * @return
     */
    public static boolean exist(String id) {
        return sseEmitterMap.get(id) == null;
    }



    /**
     * 增加Session
     *
     * @param id      客户端ID
     * @param emitter SseEmitter
     */
    public static void add(String id, SseEmitterUTF8 emitter) {
        final SseEmitterUTF8 oldEmitter = sseEmitterMap.get(id);
        if (oldEmitter != null) {
            // 重复连接
            log.warn("RepeatConnect(Id:" + id + ")");
            del(id);
//            oldEmitter.completeWithError(new Exception("RepeatConnect(Id:" + id + ")"));
        }
        sseEmitterMap.put(id, emitter);
    }

    /**
     * 删除Session
     *
     * @param id 客户端ID
     * @return
     */
    public static boolean del(String id) {
        final SseEmitter emitter = sseEmitterMap.remove(id);
        if (emitter != null) {
            emitter.complete();
            return true;
        }
        return false;
    }


    /**
     * 发送消息
     *
     * @param id  客户端ID
     * @param msg 发送的消息
     * @return
     */
    public static boolean send(String id, Object msg) {
        final SseEmitter emitter = sseEmitterMap.get(id);
        if (emitter != null) {
            try {
                emitter.send(msg);
                return true;
            } catch (IOException e) {
                log.error("MSG: SendMessageError-IOException | ID: " + id + " | Date: " + new Date() + " |", e);
                return false;
            }
        }
        return false;
    }


    /**
     * SseEmitter onCompletion 后执行的逻辑
     *
     * @param id     客户端ID
     * @param future
     */
    public static void onCompletion(String id, ScheduledFuture<?> future) {

        sseEmitterMap.remove(id);
        if (future != null) {
            // SseEmitter断开后需要中断心跳发送
            if (future.cancel(true)) {
                log.info("成功撤销任务！");
            } else {
                log.warn("撤销失败！");
            }
        }
    }

    /**
     * SseEmitter onTimeout 或 onError 后执行的逻辑
     *
     * @param id
     * @param e
     */
    public static void onError(String id, Exception e) {
        final SseEmitter emitter = sseEmitterMap.get(id);
        if (emitter != null) {
            emitter.completeWithError(e);
        }
    }





}

