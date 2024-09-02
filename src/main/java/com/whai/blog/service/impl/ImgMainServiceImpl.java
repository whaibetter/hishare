package com.whai.blog.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.whai.blog.mapper.ImgMainMapper;
import com.whai.blog.model.ImgMain;
import com.whai.blog.service.IImgMainService;
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
public class ImgMainServiceImpl extends ServiceImpl<ImgMainMapper, ImgMain> implements IImgMainService {


    @Autowired
    ImgMainMapper imgMainMapper;

    @Override
    public boolean deleteImgAccu(String imgId) {
        return imgMainMapper.deleteImgAcc(imgId);
    }

    @Override
    public ImgMain getById(String imgId) {
        QueryWrapper<ImgMain> imgWrapper = new QueryWrapper<>();
        imgWrapper.eq("img_id", imgId);
        return imgMainMapper.selectImgWithCondition(imgWrapper).get(0);
    }

    @Override
    public List<ImgMain> list(QueryWrapper<ImgMain> queryWrapper) {
        return imgMainMapper.selectImgWithCondition(queryWrapper);
    }

    @Override
    public boolean cancelDelete(String imgId) {

        return imgMainMapper.cancelDelete(imgId);
    }



}
