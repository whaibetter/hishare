package com.whai.blog.service.impl;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Envelope;
import com.whai.blog.constant.RabbitMQConstants;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.io.IOException;

/**
 * @version 1.0
 * @Author whai文海
 * @Date 2023/9/2 22:05
 * @注释
 */
@Service
@Slf4j
public class ActivityService {

    @Resource
    private RabbitTemplate rabbitTemplate;

    public void publishActivity(String message) {
        rabbitTemplate.convertAndSend(RabbitMQConstants.ACTIVIT_EXCHANGE, RabbitMQConstants.ACTIVIT_ROUTER, message);
    }


    @RabbitListener(queues = RabbitMQConstants.ACTIVIT_QUEUE)
    void synMessage(org.springframework.amqp.core.Message msg , Envelope envelope, Channel channel) throws IOException {
        byte[] body = msg.getBody();
        String message = new String(body, "UTF-8");
        log.info("Consumer msg: {}", message);

        // 获取Rabbitmq消息，并保存到DB
        // 说明：这里仅作为示例，如果有多种类型的消息，可以根据消息判定，简单的用 if...else 处理，复杂的用工厂 + 策略模式
//        notifyService.saveArticleNotify(JsonUtil.toObj(message, LoginUser.class), NotifyTypeEnum.PRAISE);

        channel.basicAck(envelope.getDeliveryTag(), false);

    }
}
