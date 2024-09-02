package com.whai.blog.utils;

import com.whai.blog.model.LoginUser;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;

public class SecurityCustomUtils {

    public static SecurityContext getContext()
    {
        return SecurityContextHolder.getContext();
    }

    public static Authentication getAuthentication()
    {
        return getContext().getAuthentication();
    }

    public static String getUserName()
    {
        return getPrincipalUser().getUsername();
    }
    public static LoginUser getPrincipalUser()
    {
        return (LoginUser) getAuthentication().getPrincipal();
    }





}
