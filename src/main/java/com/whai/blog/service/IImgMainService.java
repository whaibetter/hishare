package com.whai.blog.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.IService;
import com.whai.blog.model.ImgMain;

import java.util.List;

/**
 * <p>
 * 图片上传 服务类
 * </p>
 *
 * @author whai
 * @since 2022-10-28
 */
public interface IImgMainService extends IService<ImgMain> {

    boolean deleteImgAccu(String imgId);

    ImgMain getById(String imgId);

    List<ImgMain> list(QueryWrapper<ImgMain> queryWrapper);

    boolean cancelDelete(String imgId);

}

