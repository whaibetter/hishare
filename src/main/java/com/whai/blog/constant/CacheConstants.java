package com.whai.blog.constant;

/**
 * 缓存常量
 */
public class CacheConstants {
    /**
     * 验证码 redis key
     */
    public static final String CAPTCHA_CODE_KEY = "captcha_codes:";

    /**
     * 登录用户token redis key
     */
    public static final String LOGIN_TOKEN_KEY = "blog_login_tokens:";

    /**
     * 登录用户 redis key
     */
    public static final String LOGIN_USER_KEY = "blog_login_user_id:";

    /**
     * 留言缓存列表
     */
    public static final String MESSAGE_CACHE_KEY = "message_cache_list:";


    /**
     *  博客列表
     */
    public static final String BLOGS_LIST = "blogs_list_page:";


    /**
     * 参数管理 cache key
     */
    public static final String SYS_CONFIG_KEY = "sys_config:";

    /**
     * 字典管理 cache key
     */
    public static final String SYS_DICT_KEY = "sys_dict:";

    /**
     * 防重提交 redis key
     */
    public static final String REPEAT_SUBMIT_KEY = "repeat_submit:";

    /**
     * 限流 redis key
     */
    public static final String RATE_LIMIT_KEY = "rate_limit:";

    /**
     * 登录账户密码错误次数 redis key
     */
    public static final String PWD_ERR_CNT_KEY = "pwd_err_cnt:";
    public static final String LIKE_BLOGS_RECORD =  "blogs_like_record:";;
    public static final String USER_SIGN_KEY =  "user_sign:";;
    public static final String LIKE_MESSAGES_RECORD =   "messages_like_record:";
}
