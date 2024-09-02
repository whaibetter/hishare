package com.whai.blog.annotation;

import java.lang.annotation.*;

@Target({ElementType.METHOD,ElementType.PARAMETER})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface BrowseLog {

    /**
     * val
     */
    String value() default "";


}
