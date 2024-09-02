package com.whai.blog.service.image.oss.impl;

import com.github.hui.quick.plugin.base.file.FileWriteUtil;
import com.whai.blog.config.properties.ImageProperties;
import com.whai.blog.constant.StatusEnum;
import com.whai.blog.service.image.oss.ImageUploader;
import com.whai.blog.utils.def.ExceptionUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnExpression;
import org.springframework.stereotype.Component;
import org.springframework.util.StreamUtils;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Random;

/**
 * 本地保存上传文件
 *
 * @author Whai
 * @date 2023/1/12
 */
@Slf4j
@ConditionalOnExpression(value = "#{'local'.equals(environment.getProperty('image.oss.type'))}")
@Component
public class LocalStorageWrapper implements ImageUploader {
    @Autowired
    private ImageProperties imageProperties;
    private Random random;

    public LocalStorageWrapper() {
        random = new Random();
    }

    @Override
    public String upload(InputStream input, String fileType) {
        try {
            if (fileType == null) {
                // 根据魔数判断文件类型
                byte[] bytes = StreamUtils.copyToByteArray(input);
                input = new ByteArrayInputStream(bytes);
                fileType = getFileType((ByteArrayInputStream) input, fileType);
            }

            String path = imageProperties.getAbsTmpPath() + imageProperties.getWebImgPath();
            String fileName = genTmpFileName();

            FileWriteUtil.FileInfo file = FileWriteUtil.saveFileByStream(input, path, fileName, fileType);
            return imageProperties.buildImgUrl(imageProperties.getWebImgPath() + file.getFilename() + "." + file.getFileType());
        } catch (Exception e) {
            log.error("Parse img from httpRequest to BufferedImage error! e:", e);
            throw ExceptionUtil.of(StatusEnum.UPLOAD_PIC_FAILED);
        }
    }

    /**
     * 获取文件临时名称
     *
     * @return
     */
    private String genTmpFileName() {
        return LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyyMMddhhmmssSSS")) + "_" + random.nextInt(100);
    }

    /**
     * 外网图片转存判定，对于没有转存过的，且是http开头的网络图片时，才需要进行转存
     *
     * @param img
     * @return true 表示不需要转存
     */
    @Override
    public boolean uploadIgnore(String img) {
        if (StringUtils.isNotBlank(imageProperties.getCdnHost()) && img.startsWith(imageProperties.getCdnHost())) {
            return true;
        }

        // 如果是oss的图片，也不需要转存
        if (StringUtils.isNotBlank(imageProperties.getOss().getHost()) && img.startsWith(imageProperties.getOss().getHost())) {
            return true;
        }

        return !img.startsWith("http");
    }
}
