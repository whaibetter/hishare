package com.whai.blog.controller.admin;

import com.alibaba.fastjson.JSON;
import com.whai.blog.constant.SSEConstants;
import com.whai.blog.model.system.Server;
import com.whai.blog.service.ISSEService;
import com.whai.blog.utils.AjaxResult;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

import javax.annotation.Resource;

/**
 * 服务器监控
 *
 * @author /
 */
@RestController
@RequestMapping("/admin/server")
@Slf4j
public class ServerController {

    @Resource
    private ISSEService isseService;

    @GetMapping("systemInfo")
    @ApiOperation("系统信息")
    public AjaxResult getInfo() throws Exception
    {
        Server server = new Server();

        return AjaxResult.success(server);
    }
//
//    @GetMapping("systemSub")
//    @ApiOperation("订阅系统信息")
//    public SseEmitter sub()
//    {
//        // 建立连接
//        String id = SSEConstants.SYSTEM_INFO_STATUS;
//
////        SseEmitterUTF8 sseEmitter = sseSession.connect(30000L, id);
//
//        // 设置此线程可以给子线程使用，通过InheritableThreadLocal使用
//        RequestContextHolder.setRequestAttributes(RequestContextHolder.getRequestAttributes(),true);
//
//        /**
//         * 多线程的RequestAttribute问题
//         * https://juejin.cn/post/7021409435339718664
//         */
//
//        // 建立一个获取信息的线程，每10s推送一次
//        CompletableFuture.runAsync(
//            () -> {
//                Server systemInfo = null;
//                    try {
//                        systemInfo = new Server();
//                        sseUtils.send(id, AjaxResult.success(systemInfo));
//                    }
//                    catch (Exception e) {
//                        sseUtils.over(id);
//                        log.warn("推送系统信息异常: " + e);
//                    }
//            }
//        );
//        return sseEmitter;
//    }
//
//    @PostMapping("sent")
//    @ApiOperation("")
//    public void sent() throws Exception {
//        String id = SSEConstants.SYSTEM_INFO_STATUS;
//        Server systemInfo = null;
//        try {
//            systemInfo = new Server();
//        } catch (Exception e) {
//            throw new RuntimeException(e);
//        }
//        sseUtils.send(id, AjaxResult.success(systemInfo));
//    }

    @RequestMapping("start")
    public SseEmitter start() {
        return isseService.connect(SSEConstants.SYSTEM_INFO_STATUS,0L);
    }


    @RequestMapping("sent")
    public void sent() throws Exception {
        Server server = new Server();
        String msg = JSON.toJSONString(server);
        isseService.send(SSEConstants.SYSTEM_INFO_STATUS, SseEmitter.event().name("msg").data(AjaxResult.success(msg), MediaType.TEXT_EVENT_STREAM));
    }

    /**
     * 将SseEmitter对象设置成完成

     * @return
     */
    @RequestMapping("/end")
    public String close() {
        return isseService.close(SSEConstants.SYSTEM_INFO_STATUS);
    }

}
