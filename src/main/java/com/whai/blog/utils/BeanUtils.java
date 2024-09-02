package com.whai.blog.utils;

import static org.springframework.beans.BeanUtils.copyProperties;

/**
 * bean操作类
 */
public class BeanUtils {

    /**
     * Bean属性复制工具方法。
     *
     * @param dest 目标对象
     * @param src 源对象
     */
    public static void copyBeanProp(Object dest, Object src)
    {
        try
        {
            copyProperties(src, dest);
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
    }
}
