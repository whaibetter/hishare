package com.whai.blog.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.whai.blog.mapper.FileMainMapper;
import com.whai.blog.model.FileMain;
import com.whai.blog.service.IFileMainService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author whai
 * @since 2022-10-28
 */
@Service
public class FileMainServiceImpl extends ServiceImpl<FileMainMapper, FileMain> implements IFileMainService {

    @Autowired
    private FileMainMapper fileMainMapper;

    @Override
    public List<FileMain> list(QueryWrapper<FileMain> queryWrapper) {
        return fileMainMapper.selectFileWithCondition(queryWrapper);
    }

    @Override
    public boolean deleteFileAccu(String fileId) {
        return fileMainMapper.deleteFileAcc(fileId);
    }

    @Override
    public boolean deleteCancel(String fileId) {
        return fileMainMapper.deleteCancel(fileId);
    }

}
