package com.whai.blog.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.rabbitmq.client.Channel;
import com.whai.blog.constant.RabbitMQConstants;
import com.whai.blog.mapper.BlogCommentMapper;
import com.whai.blog.model.BlogComment;
import com.whai.blog.service.IBlogCommentService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.amqp.rabbit.connection.CorrelationData;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import java.io.IOException;
import java.util.Date;
import java.util.List;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author whai
 * @since 2022-10-30
 */
@Service
@Slf4j
public class BlogCommentServiceImpl extends ServiceImpl<BlogCommentMapper, BlogComment> implements IBlogCommentService {


    @Autowired
    private CommentConfirmReturnCallBack commentConfirmReturnCallBack;
    /**
     * 发布者的回调方法
     */
    @PostConstruct
    public void init() {
        rabbitTemplate.setConfirmCallback(commentConfirmReturnCallBack);
        /**
         * 在被退回时，提醒Publisher
         */
        rabbitTemplate.setReturnCallback(commentConfirmReturnCallBack);
    }

    @PreDestroy
    public void destroy() {

    }

    @Autowired
    BlogCommentMapper blogCommentMapper;

    @Autowired
    RabbitTemplate rabbitTemplate;


    @Override
    public List<BlogComment> queryBlogComment(String blogId) {
        return blogCommentMapper.queryBlogComment(blogId);
    }

    @Override
    public Integer likeComment(String commentId) {
        return blogCommentMapper.likeComment(commentId);
    }

    @Override
    public boolean saveComment(BlogComment comment) {

        rabbitTemplate.convertAndSend(RabbitMQConstants.COMMENT_EXCHANGE, RabbitMQConstants.COMMENT_ROUTER, JSONObject.toJSONString(comment), message -> {
            message.getMessageProperties().setDelay(5000);
            return message;
        });
        return true;
    }


    /**
     * <img src="http://img.whai.space//msedge_dtTchXXwao.png" style="zoom:80%;" /> <br/>
     * 监听RabbitMQ 评论队列
     * @param msg
     */
    @RabbitListener(queues = RabbitMQConstants.COMMENT_QUEUE)
    void submitComment(Message msg , Channel channel) throws IOException {
        String str = new String(msg.getBody());
        log.info("Comment Queue Listener 消费者 收到队列消息，当前时间{}，消息为{}", new Date(), str);
        try {
            int insert = blogCommentMapper.insert(JSONObject.parseObject(str, BlogComment.class));
            if (insert != 0) {
                channel.basicAck(msg.getMessageProperties().getDeliveryTag(), false);
            } else {
                throw new Exception("插入数据失败");
            }
        } catch (Exception e) {
            // 给评论的死信队列处理
            // 生产者无需直接处理消费者发送的 channel.basicNack 消息。RabbitMQ 会根据消费者的反馈自动处理消息，并根据需要重新发送消息给其他消费者或丢弃消息。
//            channel.basicNack(msg.getMessageProperties().getDeliveryTag(), false, false);
            log.error("错误消息：" + e.getMessage());
            e.printStackTrace();
        }
    }

    /**
     * 死信队列的消息交给另一个消费者后，需要根据实际情况采取相应的处理方式。以下是一些常见的处理方式：<br>
     *----<br>
     * - 丢弃：如果死信消息不是非常重要，可以选择丢弃该消息。<br>
     * - 记录死信入库：将死信消息记录到数据库或日志文件中，以供后续的业务分析和处理。<br>
     * - 通过死信队列处理：利用死信队列，将产生的死信通过程序的配置路由到指定的死信队列，然后由负责监听死信队列的应用程序进行处理。<br>
     *          通过死信队列，将产生的死信通过程序的配置路由到指定的死信队列，然后应用监听死信队列，对接收到的死信做后续的处理。<br>
     * 监听RabbitMQ 评论失败队列<br>
     * 消息拒绝，TTL，队列超长<br>
     * @param msg
     */
    @RabbitListener(queues = RabbitMQConstants.COMMENT_FAIL_QUEUE)
    void submitFailComment(Message msg, Channel channel) throws IOException {
        String str = new String(msg.getBody());
        log.warn("发现 评论死信队列，当前时间{}，信息为{}", new Date(), str);
        channel.basicAck(msg.getMessageProperties().getDeliveryTag(), false);

    }




}

@Slf4j
@Component
class CommentConfirmReturnCallBack implements  RabbitTemplate.ConfirmCallback, RabbitTemplate.ReturnCallback {


    /**
     * 生产者发送到交换机
     */
    @Override
    public void confirm(CorrelationData correlationData, boolean ack, String cause) {
        String id = correlationData!=null? correlationData.getId() : "";
        if (ack) {
            log.info("提醒publisher, 交换机收到 ack回调！{}" , id);
        } else {
            log.warn("提醒publisher, 交换机收到 Nack回调！id:{}  失败原因 cause: {}", id, cause);
        }
    }

    /**
     * 交换机到队列
     */
    @Override
    public void returnedMessage(Message message, int replyCode, String replyText, String exchange, String routingKey) {
        byte[] body = message.getBody();
        log.warn("提醒publisher,  消息:{} 被服务器退回，退回原因:{}, 交换机是:{}, 路由 key:{}", new String(body), replyText, exchange, routingKey);
    }
}
