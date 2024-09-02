package com.whai.blog.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.whai.blog.mapper.FileDownloadMapper;
import com.whai.blog.model.FileDownload;
import com.whai.blog.service.IFileDownloadService;
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
public class FileDownloadServiceImpl extends ServiceImpl<FileDownloadMapper, FileDownload> implements IFileDownloadService {

}
