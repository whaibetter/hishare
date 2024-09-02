package com.whai.blog.mapper;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.toolkit.Constants;
import com.whai.blog.model.ImgMain;
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
public interface ImgMainMapper extends BaseMapper<ImgMain> {

    boolean deleteImgAcc(String imgId);

    List<ImgMain> selectImgWithCondition(@Param(Constants.WRAPPER) QueryWrapper<ImgMain> imgWrapper);

    boolean cancelDelete(String imgId);

}
