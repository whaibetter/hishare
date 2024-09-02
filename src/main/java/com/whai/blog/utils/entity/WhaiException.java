package com.whai.blog.utils.entity;

import com.whai.blog.constant.StatusEnum;
import lombok.Data;

/**
 * @version 1.0
 * @Author whai文海
 * @Date 2024/9/2 21:40
 * @注释
 */
@Data
public class WhaiException extends RuntimeException {



    private StatusEnum status;
    private Object[] args;


    public WhaiException(StatusEnum statusEnum, Object... args) {
        this.status = statusEnum;
        this.args = args;
    }
}
