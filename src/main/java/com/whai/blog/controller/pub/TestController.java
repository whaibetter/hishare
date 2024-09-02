package com.whai.blog.controller.pub;


import com.google.common.collect.HashMultimap;
import com.google.common.collect.Multimap;
import com.google.common.collect.Multimaps;
import com.whai.blog.utils.AjaxResult;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.context.request.async.DeferredResult;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.Collection;
import java.util.Date;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@RestController
@ApiOperation("")
@RequestMapping("/test")
@Slf4j
public class TestController {


    // 存放监听某个Id的长轮询集合
    // 线程同步结构
    public static Multimap<String, DeferredResult<String>> watchRequests = Multimaps.synchronizedMultimap(HashMultimap.create());

    /**
     * 设置监听
     */
    @GetMapping(path = "watch/{id}")
    public DeferredResult<String> watch(@PathVariable String id) {
        // 延迟对象设置超时时间
        DeferredResult<String> deferredResult = new DeferredResult<>(3000L, "timeout");
        // 异步请求完成时移除 key，防止内存溢出
        deferredResult.onCompletion(() -> {
            log.info("onCompletion remove");
            watchRequests.remove(id, deferredResult);
        });
        // 注册长轮询请求
        log.info("注册长轮询请求");
        watchRequests.put(id, deferredResult);
        return deferredResult;
    }

    /**
     * 变更数据
     */
    @GetMapping(path = "publish/{id}")
    public String publish(@PathVariable String id) {
        // 数据变更 取出监听ID的所有长轮询请求，并一一响应处理
        if (watchRequests.containsKey(id)) {
            Collection<DeferredResult<String>> deferredResults = watchRequests.get(id);
            for (DeferredResult<String> deferredResult : deferredResults) {
                log.info("更新！");
                deferredResult.setResult("我更新了" + new Date());
            }
        }
        return "success";
    }

    @GetMapping(path = "message")
    public void message(HttpServletResponse response) throws IOException, InterruptedException {
        Integer count = 0;
        while (true) {
            count++;
            response.setHeader("Pragma", "no-cache");
            response.setDateHeader("Expires", 0);
            response.setHeader("Cache-Control", "no-cache,no-store");
            response.setStatus(HttpServletResponse.SC_OK);
            response.getWriter().print(" <script type=\"text/javascript\">\n" +
                    "parent.document.getElementById('clock').innerHTML = \"" + count + "\";" +
                    "parent.document.getElementById('count').innerHTML = \"" + count + "\";" +
                    "</script>");
        }
    }

    // 缓存id 和 对应的SseEmitter
    private static Map<String, SseEmitterUTF8> sseEmitterMap = new ConcurrentHashMap<>();


    @GetMapping(path = "ssesub")
    public SseEmitterUTF8 sseSub(String userId) throws IOException, InterruptedException {
        try {
            // 设置超时时间，0表示不过期。默认30秒
            SseEmitterUTF8 sseEmitter = new SseEmitterUTF8(0L);
            // 注册回调
            sseEmitter.onCompletion(() -> log.info("成功！"));
            sseEmitter.onError(throwable -> {
                sseEmitterMap.remove(userId);
                System.out.println("出错");
                log.error(throwable.getMessage());
            });


            sseEmitter.onTimeout(() -> sseEmitterMap.remove(userId));

            // 设置前端的重试时间为1s
            sseEmitter.send(SseEmitterUTF8.event().name("msg").reconnectTime(1000).data("连接成功"));

            sseEmitterMap.put(userId, sseEmitter);
            return sseEmitter;
        } catch (Exception e) {
            log.info("创建新的sse连接异常，当前用户：{}", userId);
        }
        return null;
    }

    @PostMapping(path = "send")
    public AjaxResult send(String userId, String content) throws IOException, InterruptedException {
        if (sseEmitterMap.containsKey(userId)) {
            try {
                SseEmitterUTF8 sseEmitter = sseEmitterMap.get(userId);
                // 这里 event name的msg对应前端的msg监听器 设置编码，返回格式
                // 这里可以写数据发生变化时的逻辑，直接推送到前端
                sseEmitter.send(SseEmitterUTF8.event().name("msg").data("后端发送消息：" + content,MediaType.TEXT_EVENT_STREAM));
                log.info("后端发送：" + content);
            } catch (IOException e) {
                log.error("用户[{}]推送异常:{}", userId, e.getMessage());
//                removeUser(userId);
            }
        }
        return AjaxResult.success();
    }

    @GetMapping(path = "over")
    public String over(String id) {
        SseEmitterUTF8 sseEmitter = sseEmitterMap.get(id);
        if (sseEmitter != null) {
            sseEmitter.complete();
            sseEmitterMap.remove(id);
        }
        return "over";
    }

    class SseEmitterUTF8 extends SseEmitter {

        public SseEmitterUTF8(Long timeout) {
            super(timeout);
        }

        @Override
        protected void extendResponse(ServerHttpResponse outputMessage) {
            super.extendResponse(outputMessage);
            HttpHeaders headers = outputMessage.getHeaders();
            headers.setContentType(new MediaType(MediaType.TEXT_EVENT_STREAM, StandardCharsets.UTF_8));
//            headers.setContentType( new MediaType("text", "event-stream", Charset.forName("UTF-8")));
        }
    }











}
