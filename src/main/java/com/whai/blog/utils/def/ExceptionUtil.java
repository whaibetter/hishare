package com.whai.blog.utils.def;

import com.whai.blog.constant.StatusEnum;
import com.whai.blog.utils.entity.WhaiException;


/**
 * @version 1.0
 * @Author whai文海
 * @Date 2024/9/2 21:36
 * @注释
 */
public class ExceptionUtil {

    public static WhaiException of(StatusEnum status, Object... args) {
        return new WhaiException(status, args);
    }

}

