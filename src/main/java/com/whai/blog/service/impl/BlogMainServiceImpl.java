package com.whai.blog.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.rabbitmq.client.Channel;
import com.whai.blog.component.redis.RedisCache;
import com.whai.blog.constant.CacheConstants;
import com.whai.blog.constant.RabbitMQConstants;
import com.whai.blog.mapper.BlogMainMapper;
import com.whai.blog.model.BlogMain;
import com.whai.blog.service.IBlogMainService;
import com.whai.blog.utils.ServletUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.AmqpException;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.core.MessagePostProcessor;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.List;
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
public class BlogMainServiceImpl extends ServiceImpl<BlogMainMapper, BlogMain> implements IBlogMainService {

    /**
     * 这里如果不在mapper里写@Repository 是会报错的
     */
    @Autowired
    BlogMainMapper blogMainMapper;

    @Autowired
    private RabbitTemplate rabbitTemplate;

    @Autowired
    private RedisCache redisCache;



    @Override
    public IPage<BlogMain> findBlogByTagId(QueryWrapper<BlogMain> queryWrapper ,IPage<BlogMain> page) {
        return blogMainMapper.findBlogByTagId(queryWrapper,page);
    }

    @Override
    public IPage<BlogMain> pageWithDelete(QueryWrapper<BlogMain> queryWrapper, IPage<BlogMain> page) {
        //调用自定义查询mapper
        return blogMainMapper.pageCustomSelectBlog(queryWrapper, page);
    }

    @Override
    public BlogMain getBlogById(String blogId) {
        QueryWrapper<BlogMain> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("blog_id", blogId);
        return blogMainMapper.getABlogCustom(queryWrapper);
    }

    @Override
    public boolean cancelDelete(String blogId) {
        return blogMainMapper.cancelDelete(blogId);
    }

    @Override
    public List<BlogMain> searchByKeyWord(String keyWord) {
        return blogMainMapper.searchByKeyWord(keyWord);
    }

    @Override
    public void synchronizedBlogCache(Integer currentPage) {
        // 发送消息到rabbitMQ让消费者同步信息到redis
        rabbitTemplate.convertAndSend(RabbitMQConstants.BLOG_EXCHANGE, RabbitMQConstants.SYN_BLOG_ROUTER, currentPage.toString(), new MessagePostProcessor() {
            @Override
            public Message postProcessMessage(Message message) throws AmqpException {
                return message;
            }
        });
    }




    @RabbitListener(queues = RabbitMQConstants.SYN_BLOG_QUEUE)
    void synBlogConsumer(Message msg , Channel channel) throws IOException {

        byte[] body = msg.getBody();
        int currentPage = Integer.parseInt(new String(body));
        log.info(" 获取到 同步请求blog 同步第{}页的blog", currentPage);
        try {
            //分页 每页显示8条数据
            Page<BlogMain> blogMainIPage = new Page<BlogMain>(currentPage, 9);
            //条件筛选
            QueryWrapper<BlogMain> select = new QueryWrapper<BlogMain>();
            select.orderByDesc("blog_create_time");
            Page<BlogMain> blog = this.page(blogMainIPage, select);

            // 填充到redis缓存中
            String cacheKey = CacheConstants.BLOGS_LIST + currentPage;
            redisCache.setCacheObject(cacheKey, blog, 30, TimeUnit.MINUTES);

            channel.basicAck(msg.getMessageProperties().getDeliveryTag(), false);
        } catch (Exception e) {
            // 给评论的死信队列处理
            // 生产者无需直接处理消费者发送的 channel.basicNack 消息。RabbitMQ 会根据消费者的反馈自动处理消息，并根据需要重新发送消息给其他消费者或丢弃消息。
            channel.basicNack(msg.getMessageProperties().getDeliveryTag(), false, false);
            log.error("错误消息：" + e.getMessage());
            e.printStackTrace();
        }
    }

    @Override
    public Integer likeBlog(String blogId) {
        // 使用Redis的Map防止重复点赞
        String sessionId = ServletUtils.getSession().getId();;

        String key = CacheConstants.LIKE_BLOGS_RECORD + blogId;
        Set<String> cacheSet = redisCache.getCacheSet(key);
        if (cacheSet.contains(sessionId)) {
            // 已经点赞过
            return 0;
        }

        // 存入点赞的session到set中，并存入redis
        cacheSet.add(sessionId);
        redisCache.setCacheSet(key, cacheSet);
        rabbitTemplate.convertAndSend(RabbitMQConstants.BLOG_EXCHANGE, RabbitMQConstants.LIKE_BLOG_ROUTER, blogId);
        return 1;
    }

    private final static int BLOG_LIKE_THRESHOLD = 5;
    private Integer likeNumber = 0;

    @RabbitListener(queues = RabbitMQConstants.LIKE_BLOG_QUEUE)
    void likeBlogConsumer(Message msg , Channel channel) throws IOException {

        String blogId = new String(msg.getBody());
        log.info("blogId:{} , 当前缓存收到了 {} 个like", blogId, likeNumber + 1);
        if (++likeNumber >= BLOG_LIKE_THRESHOLD) {
            // 收到5个请求就提交给SQL
            blogMainMapper.likeBlog(blogId, BLOG_LIKE_THRESHOLD);
            channel.basicAck(msg.getMessageProperties().getDeliveryTag(), false);
            likeNumber = 0;
            return;
        }
        channel.basicAck(msg.getMessageProperties().getDeliveryTag(), false);
    }



}
