package com.whai.blog.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.whai.blog.mapper.TagMapper;
import com.whai.blog.model.Tag;
import com.whai.blog.service.ITagService;
import org.springframework.stereotype.Service;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author whai
 * @since 2022-10-28
 */
@Service
public class TagServiceImpl extends ServiceImpl<TagMapper, Tag> implements ITagService {

}
