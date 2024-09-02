package com.whai.blog.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.whai.blog.mapper.SysOperLogMapper;
import com.whai.blog.model.SysOperLog;
import com.whai.blog.service.ISysOperLogService;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 操作日志记录 服务实现类
 * </p>
 *
 * @author whai
 * @since 2022-10-28
 */
@Service
public class SysOperLogServiceImpl extends ServiceImpl<SysOperLogMapper, SysOperLog> implements ISysOperLogService {

}
