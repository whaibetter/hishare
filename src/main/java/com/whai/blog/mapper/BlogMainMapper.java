package com.whai.blog.mapper;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Constants;
import com.whai.blog.model.BlogMain;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.session.RowBounds;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author whai
 * @since 2022-10-28
 */
@Repository
public interface BlogMainMapper extends BaseMapper<BlogMain> {

    Integer likeBlog(String blogId,Integer number);


    IPage<BlogMain> findBlogByTagId(@Param(Constants.WRAPPER) Wrapper<BlogMain> queryWrapper , IPage<BlogMain> page);

    IPage<BlogMain> pageCustomSelectBlog(@Param(Constants.WRAPPER) QueryWrapper<BlogMain> queryWrapper, IPage<BlogMain> page);

    BlogMain getABlogCustom(@Param(Constants.WRAPPER) QueryWrapper<BlogMain> queryWrapper);

    boolean cancelDelete(String blogId);

    List<BlogMain> searchByKeyWord(String keyWord);


    List<BlogMain> getBlogPages(RowBounds rowBounds);
}
