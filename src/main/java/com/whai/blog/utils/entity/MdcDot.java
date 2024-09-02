package com.whai.blog.utils.entity;

import java.lang.annotation.*;

/**
 * @author Whai
 * @date 2023/5/26
 */
@Target({ElementType.METHOD, ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface MdcDot {
    String bizCode() default "";
}
