package com.whai.blog.controller.pub;


import com.whai.blog.constant.RabbitMQConstants;
import com.whai.blog.model.BlogComment;
import org.springframework.amqp.AmqpException;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.core.MessagePostProcessor;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import java.nio.charset.StandardCharsets;

@Controller
public class JumpController {

    private String prefix = "";

    /**
     * 跳转主页
     *
     * @return
     */
    @GetMapping("toIndex")
    public String toIndex() {
        return prefix + "/index";
    }

    @GetMapping("toLogin")
    public String toLogin(){
        return "redirect:"+prefix + "/login";
    }

    @GetMapping("toMyPage")
    public String toMyPage(){
        return prefix + "/home";
    }

    @GetMapping("toAdmin")
    public String toAdmin() {
        return prefix + "/admin/index";
    }

    @GetMapping("error")
    public String toError() {
        return prefix + "/404";
    }

    /**
     * 跳转主页
     *
     * @return
     */
    @GetMapping("test")
    public String test() {
        return prefix + "/admin/blogList";
    }


    @Autowired
    RabbitTemplate template;
    @GetMapping("msg")
    public String test1() {
        BlogComment blogComment = new BlogComment();
        blogComment.setBlogCommentContent("fdsgdfgdfg");
        template.convertAndSend(RabbitMQConstants.COMMENT_EXCHANGE, RabbitMQConstants.COMMENT_ROUTER, blogComment.toString().getBytes(StandardCharsets.UTF_8), new MessagePostProcessor() {
            @Override
            public Message postProcessMessage(Message message) throws AmqpException {
                return message;
            }
        });

        return "";
    }

}
