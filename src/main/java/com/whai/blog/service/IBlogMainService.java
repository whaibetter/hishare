package com.whai.blog.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import com.whai.blog.model.BlogMain;

import java.util.List;

/**
 * <p>
 *  博客主体 服务类
 * </p>
 *
 * @author whai
 * @since 2022-10-28
 */
public interface IBlogMainService extends IService<BlogMain> {

    Integer likeBlog(String blogId);

    IPage<BlogMain> findBlogByTagId(QueryWrapper<BlogMain> queryWrapper , IPage<BlogMain> page);

    IPage<BlogMain> pageWithDelete(QueryWrapper<BlogMain> queryWrapper , IPage<BlogMain> page);

    /**
     * 根据blogId找到blog，包括逻辑删除的
     * @return
     */
    BlogMain getBlogById(String blogId);

    boolean cancelDelete(String blogId);

    List<BlogMain> searchByKeyWord(String keyWord);

    void synchronizedBlogCache(Integer currentPage);


}
