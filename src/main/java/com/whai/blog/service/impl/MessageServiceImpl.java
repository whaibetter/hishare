package com.whai.blog.service.impl;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.rabbitmq.client.Channel;
import com.whai.blog.component.redis.RedisCache;
import com.whai.blog.constant.CacheConstants;
import com.whai.blog.constant.RabbitMQConstants;
import com.whai.blog.mapper.MessageMapper;
import com.whai.blog.model.Message;
import com.whai.blog.service.IMessageService;
import com.whai.blog.utils.ServletUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.Set;
import java.util.concurrent.TimeUnit;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author whai
 * @since 2022-10-28
 */
@Service
@Slf4j
public class MessageServiceImpl extends ServiceImpl<MessageMapper, Message> implements IMessageService {

    @Autowired
    private MessageMapper messageMapper;

    @Autowired
    private RedisCache redisCache;

    @Autowired
    private RabbitTemplate rabbitTemplate;

    @Override
    public Integer likeMessage(String messageId) {
        // 使用Redis的Map防止重复点赞
        String sessionId = ServletUtils.getSession().getId();;

        String key = CacheConstants.LIKE_MESSAGES_RECORD + messageId;
        Set<String> cacheSet = redisCache.getCacheSet(key);
        if (cacheSet.contains(sessionId)) {
            // 已经点赞过
            return 0;
        }

        // 存入点赞的session到set中，并存入redis
        cacheSet.add(sessionId);
        redisCache.setCacheSet(key, cacheSet);

        rabbitTemplate.convertAndSend(RabbitMQConstants.MESSAGE_EXCHANGE, RabbitMQConstants.LIKE_MESSAGE_ROUTER, messageId);
        return 1;
    }

    private final static int MESSAGE_THRESHOLD = 5;
    private Integer likeNumber = 0;

    @RabbitListener(queues = RabbitMQConstants.LIKE_MESSAGE_QUEUE)
    void likeMessageConsumer(org.springframework.amqp.core.Message msg , Channel channel) throws IOException {
        String messageId = new String(msg.getBody());
        log.info("用户like Message:{} , 当前收到了 {} 个like", messageId, likeNumber + 1);
        if (++likeNumber >= MESSAGE_THRESHOLD) {
            // 收到5个请求就提交给SQL
            messageMapper.likeMessage(messageId, MESSAGE_THRESHOLD);
            likeNumber = 0;
            channel.basicAck(msg.getMessageProperties().getDeliveryTag(), false);
            return;
        }
        channel.basicAck(msg.getMessageProperties().getDeliveryTag(), false);
    }

    @Override
    public void synchronizedMessageCache(Integer page) {
        // 发送消息到rabbitMQ让消费者同步信息到redis
        rabbitTemplate.convertAndSend(RabbitMQConstants.MESSAGE_EXCHANGE, RabbitMQConstants.SYN_MESSAGE_ROUTER, page.toString());
    }

    @Override
    public boolean saveMessage(Message saveMessage) {
        int insert = messageMapper.insert(saveMessage);
        this.synchronizedMessageCache(1);
        return insert != 0;
    }


    @RabbitListener(queues = RabbitMQConstants.SYN_MESSAGE_QUEUE)
    void synMessage(org.springframework.amqp.core.Message msg , Channel channel) throws IOException {

        int currentPage = Integer.parseInt(new String(msg.getBody()));
        log.info(" 获取到 同步请求留言 同步第{}页的留言", currentPage);
        try {

            Page<Message> messageIPage = new Page<Message>(currentPage, 15);
            Page<Message> message = this.page(messageIPage);

            // 填充到redis缓存中
            String cacheKey = CacheConstants.MESSAGE_CACHE_KEY + currentPage;
            redisCache.setCacheObject(cacheKey, message, 30, TimeUnit.MINUTES);

            channel.basicAck(msg.getMessageProperties().getDeliveryTag(), false);
        } catch (Exception e) {
            // 给评论的死信队列处理
            // 生产者无需直接处理消费者发送的 channel.basicNack 消息。RabbitMQ 会根据消费者的反馈自动处理消息，并根据需要重新发送消息给其他消费者或丢弃消息。
            channel.basicNack(msg.getMessageProperties().getDeliveryTag(), false, false);
            log.error("错误消息：" + e.getMessage());
            e.printStackTrace();
        }
    }
}
