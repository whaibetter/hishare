package com.whai.blog.config;


import com.whai.blog.constant.RabbitMQConstants;
import org.springframework.amqp.core.*;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 *
 */
@Configuration
public class RabbitMQConfig {


    /**
     * 事件
     * @return
     */
    @Bean
    Queue eventQueue() {
        return QueueBuilder.durable(RabbitMQConstants.ACTIVIT_QUEUE)
                .ttl(1000).maxLength(5)
                .build();
    }
    @Bean
    Exchange eventExchange() {
        return ExchangeBuilder.directExchange(RabbitMQConstants.ACTIVIT_EXCHANGE).durable(true).build();
    }
    @Bean
    Binding eventBinding() {
        return BindingBuilder.bind(eventQueue()).to(eventExchange()).with(RabbitMQConstants.ACTIVIT_ROUTER).noargs();
    }




    /**
     * <img src="http://img.whai.space//msedge_dtTchXXwao.png" style="zoom:80%;" /> <br/>
     * @return
     */
    @Bean
    Queue commentQueue() {
        return QueueBuilder.durable(RabbitMQConstants.COMMENT_QUEUE)
                .ttl(1000).maxLength(5)
                .deadLetterExchange(RabbitMQConstants.COMMENT_FAIL_EXCHANGE).deadLetterRoutingKey(RabbitMQConstants.COMMENT_FAIL_ROUTER)
                .build();
    }

    @Bean
    Queue commentFailQueue() {
        return QueueBuilder.durable(RabbitMQConstants.COMMENT_FAIL_QUEUE)
                .build();
    }

    @Bean
    Exchange commentExchange() {
        return ExchangeBuilder.directExchange(RabbitMQConstants.COMMENT_EXCHANGE).durable(true).build();
    }

    @Bean
    Exchange commentFailExchange() {
        return ExchangeBuilder.directExchange(RabbitMQConstants.COMMENT_FAIL_EXCHANGE).durable(true).build();
    }

    @Bean
    Binding commentBinding() {
        return BindingBuilder.bind(commentQueue()).to(commentExchange()).with(RabbitMQConstants.COMMENT_ROUTER).noargs();
    }

    @Bean
    Binding commentFailBinding() {
        return BindingBuilder.bind(commentFailQueue()).to(commentFailExchange()).with(RabbitMQConstants.COMMENT_FAIL_ROUTER).noargs();
    }



    /**
     * 下面为进行 同步Blog
     * <img src="http://img.whai.space//msedge_0cLdRp7Krt.png" alt="msedge_0cLdRp7Krt" style="zoom:80%;" />
     * @return
     */
    @Bean
    Queue synBlogQueue() {
        return QueueBuilder.durable(RabbitMQConstants.SYN_BLOG_QUEUE)
                .ttl(1000).maxLength(5)
                .build();
    }

    @Bean
    Queue likeBlogQueue() {
        return QueueBuilder.durable(RabbitMQConstants.LIKE_BLOG_QUEUE)
                .build();
    }

    @Bean
    Exchange blogExchange() {
        return ExchangeBuilder.directExchange(RabbitMQConstants.BLOG_EXCHANGE).durable(true).build();
    }

    @Bean
    Binding synBlogBinding() {
        return BindingBuilder.bind(synBlogQueue()).to(blogExchange()).with(RabbitMQConstants.SYN_BLOG_ROUTER).noargs();
    }

    @Bean
    Binding likeBlogBinding() {
        return BindingBuilder.bind(likeBlogQueue()).to(blogExchange()).with(RabbitMQConstants.LIKE_BLOG_ROUTER).noargs();
    }



    /**
     * <img src="http://img.whai.space//msedge_XcLJxYjQXc.png" alt="msedge_XcLJxYjQXc" style="zoom:80%;" />
     * 下面为进行 同步Message的 likeMessage
     * @return
     */
    @Bean
    Queue synMessageQueue() {
        return QueueBuilder.durable(RabbitMQConstants.SYN_MESSAGE_QUEUE)
                .ttl(1000).maxLength(5)
                .build();
    }

    @Bean
    Queue likeMessageQueue() {
        return QueueBuilder.durable(RabbitMQConstants.LIKE_MESSAGE_QUEUE)
                .ttl(1000).maxLength(5)
                .build();
    }

    @Bean
    Exchange synMessageExchange() {
        return ExchangeBuilder.directExchange(RabbitMQConstants.MESSAGE_EXCHANGE).durable(true).build();
    }


    @Bean
    Binding synMessageBinding() {
        return BindingBuilder.bind(synMessageQueue()).to(synMessageExchange()).with(RabbitMQConstants.SYN_MESSAGE_ROUTER).noargs();
    }

    @Bean
    Binding likeMessageBinding() {
        return BindingBuilder.bind(likeMessageQueue()).to(synMessageExchange()).with(RabbitMQConstants.LIKE_MESSAGE_ROUTER).noargs();
    }



}
