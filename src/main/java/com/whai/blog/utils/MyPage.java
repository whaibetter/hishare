//package com.whai.blog.utils;
//
//import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
//
///**
// * 拓展了hasNext和hasPrevious字段的工具类
// * @param <T>
// */
//public class MyPage<T> extends Page<T> {
//
//    protected boolean hasNext;
//    protected boolean hasPrevious;
//
//
//    public boolean isHasNext() {
//        return hasNext;
//    }
//
//    public void setHasNext(boolean hasNext) {
//        this.hasNext = hasNext;
//    }
//
//    public boolean isHasPrevious() {
//        return hasPrevious;
//    }
//
//    public void setHasPrevious(boolean hasPrevious) {
//        this.hasPrevious = hasPrevious;
//    }
//
//    public MyPage(long current, long size) {
//        super(current, size);
//        hasNext = super.hasNext();
//        hasPrevious = super.hasPrevious();
//    }
//}
