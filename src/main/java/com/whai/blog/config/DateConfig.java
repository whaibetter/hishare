//package com.whai.blog.config;
//
//import com.baomidou.mybatisplus.core.handlers.MetaObjectHandler;
//import org.apache.ibatis.reflection.MetaObject;
//import org.springframework.stereotype.Component;
//
//import java.time.Date;
//
///**
// * 配置用于自动填充日期
// */
//@Component
//public class DateConfig implements MetaObjectHandler {
//
//    /**
//     * 使用mp做添加操作时候，这个方法执行
//     * @param metaObject
//     */
//    @Override
//    public void insertFill(MetaObject metaObject) {
//        //设置属性值
//        Date now = Date.now();
//        this.setFieldValByName("blogCreateTime", now,metaObject);
//        this.setFieldValByName("blogLastChangeTime", now,metaObject);
//    }
//
//    /**
//     * 使用mp做修改操作时候，这个方法执行
//     * @param metaObject
//     */
//    @Override
//    public void updateFill(MetaObject metaObject) {
//        this.setFieldValByName("blogLastChangeTime",Date.now(),metaObject);
//    }
//}
//
