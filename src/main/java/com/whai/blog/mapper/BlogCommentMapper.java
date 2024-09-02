package com.whai.blog.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.whai.blog.model.BlogComment;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author whai
 * @since 2022-10-30
 */
@Repository
public interface BlogCommentMapper extends BaseMapper<BlogComment> {

    List<BlogComment> queryBlogComment(String blogId);


    Integer likeComment(String commentId);
}
