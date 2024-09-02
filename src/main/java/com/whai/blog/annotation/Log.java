package com.whai.blog.annotation;


import java.lang.annotation.*;

@Target({ElementType.METHOD,ElementType.PARAMETER})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface Log {

    /**
     * val
     */
    String value() default "";


    BusinessType BUSINESS_TYPE() default BusinessType.NONE;


    /**
     * 操作日志 方法类型 crud 枚举
     */
    public enum BusinessType{
        ADD,
        DELETE,
        SELECT,
        UPDATE,
        NONE
    }
}


