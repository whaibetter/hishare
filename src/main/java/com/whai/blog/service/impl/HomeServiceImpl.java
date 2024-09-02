package com.whai.blog.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.whai.blog.mapper.HomeMapper;
import com.whai.blog.model.Home;
import com.whai.blog.service.IHomeService;
import org.springframework.stereotype.Service;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author whai
 * @since 2022-11-08
 */
@Service
public class HomeServiceImpl extends ServiceImpl<HomeMapper, Home> implements IHomeService {

}
