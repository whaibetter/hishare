package com.whai.blog.controller.admin;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/admin/notice")
public class NoticeController {


    @GetMapping("/")
    public String messageList()
    {
        return  "/admin/message";
    }

}
