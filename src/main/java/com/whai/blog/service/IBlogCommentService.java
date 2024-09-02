package com.whai.blog.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.whai.blog.model.BlogComment;

import java.util.List;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author whai
 * @since 2022-10-30
 */
public interface IBlogCommentService extends IService<BlogComment> {

    List<BlogComment> queryBlogComment(String blogId);

    Integer likeComment(String commentId);

    boolean saveComment(BlogComment comment);
}
