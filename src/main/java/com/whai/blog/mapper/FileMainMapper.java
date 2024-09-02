package com.whai.blog.mapper;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.toolkit.Constants;
import com.whai.blog.model.FileMain;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author whai
 * @since 2022-10-28
 */
@Repository
public interface FileMainMapper extends BaseMapper<FileMain> {

    List<FileMain> selectFileWithCondition(@Param(Constants.WRAPPER) QueryWrapper<FileMain> fileWrapper);

    boolean deleteFileAcc(String fileId);

    boolean deleteCancel(String fileId);
}
