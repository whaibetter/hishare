package com.whai.blog.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.IService;
import com.whai.blog.model.FileMain;

import java.util.List;

/**
 * <p>
 *  文件 服务类
 * </p>
 *
 * @author whai
 * @since 2022-10-28
 */
public interface IFileMainService extends IService<FileMain> {

    List<FileMain> list(QueryWrapper<FileMain> queryWrapper);

    boolean deleteFileAccu(String fileId);

    boolean deleteCancel(String fileId);
}
