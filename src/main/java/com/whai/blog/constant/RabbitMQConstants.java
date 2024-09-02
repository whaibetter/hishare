package com.whai.blog.constant;

public class RabbitMQConstants {

    /**
     *
     */
    public static final String COMMENT_EXCHANGE = "comment_exchange";

    public static final String COMMENT_QUEUE = "comment_queue";

    public static final String COMMENT_ROUTER = "comment_router";


    public static final String COMMENT_FAIL_EXCHANGE = "comment_fail_exchange";

    public static final String COMMENT_FAIL_QUEUE = "comment_fail_queue";

    public static final String COMMENT_FAIL_ROUTER = "comment_fail_router";


    public static final String SYN_BLOG_QUEUE = "syn_blog_queue";
    public static final String SYN_BLOG_ROUTER = "syn_blog_router";
    public static final String LIKE_BLOG_ROUTER = "like_blog_router";
    public static final String LIKE_BLOG_QUEUE =  "like_blog_queue";

    public static final String BLOG_EXCHANGE = "syn_blog_cache";


    public static final String MESSAGE_EXCHANGE =  "message_exchange";
    public static final String SYN_MESSAGE_ROUTER = "syn_message_router";
    public static final String SYN_MESSAGE_QUEUE = "syn_message_queue";
    public static final String LIKE_MESSAGE_ROUTER =  "like_message_router";;
    public static final String LIKE_MESSAGE_QUEUE =  "like_message_queue";;
    public static final String ACTIVIT_EXCHANGE = "activit_exchange";

    public static final String ACTIVIT_QUEUE = "activit_queue";


    public static final String ACTIVIT_ROUTER = "activit_router";
}
