package com.whai.blog.controller.admin;


import com.whai.blog.utils.AjaxResult;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.extern.slf4j.Slf4j;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.HtmlUtils;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@RestController
@Slf4j
public class LogController {

    @GetMapping("/admin/log/list")
    @ApiOperation("获取日志")
    public AjaxResult getList() {
        List<String> logFileNames = new ArrayList<>();
        try {
            String logsPath = System.getProperty("user.dir") + "/" + "logs";
            File logFiles = new File(logsPath);
            File[] files = logFiles.listFiles();
            for (File file : files) {
                logFileNames.add(file.getName());
            }
        } catch (Exception e) {
            log.error(e.getMessage());
            return AjaxResult.error();
        }
        return AjaxResult.success(logFileNames);
    }

    @GetMapping("/admin/log/getLog")
    @ApiOperation("获取日志")
    public AjaxResult getLogs(@ApiParam(value = "日志名称",defaultValue = "errorShow.log")
                              String logName) {
        StringBuilder contend;
        try {
            String logPath = System.getProperty("user.dir") + "/" + "logs" + "/" + logName;
            contend = readFileContend(logPath);
        } catch (Exception e) {
            log.error(e.getLocalizedMessage());
            return AjaxResult.error();
        }
        return AjaxResult.success(contend);

    }

    public StringBuilder readFileContend(String logPath) throws IOException {
        File file = new File(logPath);
        BufferedReader reader = new BufferedReader(new FileReader(file));
        StringBuilder contend = new StringBuilder();
        String line = "";
        while ((line = reader.readLine()) != null) {
            contend.append(line + "\n");
        }
        return contend;
    }


    @MessageMapping("/hello") // 浏览器向服务器发起消息，映射到该地址。
    @SendTo("/topic/hello") // 如果服务器接受到了消息，就会对订阅了 @SendTo 括号中的地址的浏览器发送消息。
    public AjaxResult greeting() throws Exception {
        Thread.sleep(1000); // simulated delay
        return AjaxResult.success(HtmlUtils.htmlEscape("12123"));
    }


}
