package com.whai.blog.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.whai.blog.model.Message;

/**
 * <p>
 * 留言板 服务类
 * </p>
 *
 * @author whai
 * @since 2022-10-28
 */
public interface IMessageService extends IService<Message> {

    Integer likeMessage(String MessageId);

    void synchronizedMessageCache(Integer page);

    boolean saveMessage(Message saveMessage);
}
