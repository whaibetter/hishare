package com.whai.blog.utils;

import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;

import java.util.List;

public class ValidateUtils {

    public static String getValidateErrorMessage(BindingResult bindingResult){

        List<FieldError> fieldErrors = bindingResult.getFieldErrors();
        StringBuilder message = new StringBuilder("param error:");
        for (FieldError error : fieldErrors) {
            message.append(error.getField()).append(error.getDefaultMessage()).append(" / ");
        }
        return message.toString();
    }
}
