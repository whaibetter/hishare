package com.whai.blog.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.whai.blog.mapper.BlogBrowseMapper;
import com.whai.blog.model.BlogBrowse;
import com.whai.blog.service.IBlogBrowseService;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author whai
 * @since 2022-10-28
 */
@Service
public class BlogBrowseServiceImpl extends ServiceImpl<BlogBrowseMapper, BlogBrowse> implements IBlogBrowseService, InitializingBean {


    @PostConstruct
    public void initRedisBloomFilter() {
        //初始化布隆过滤器
    }

    @Override
    public void afterPropertiesSet() throws Exception {

    }




}
