package com.whai.blog.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.whai.blog.model.Message;
import org.springframework.stereotype.Repository;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author whai
 * @since 2022-10-28
 */
@Repository
public interface MessageMapper extends BaseMapper<Message> {

    /**
     *
     * @param messageId 留言的id
     * @param likeNumber 增加的点赞数
     * @return
     */
    Integer likeMessage(String messageId, Integer likeNumber);

}
