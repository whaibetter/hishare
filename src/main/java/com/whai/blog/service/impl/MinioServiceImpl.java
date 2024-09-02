package com.whai.blog.service.impl;

import cn.hutool.core.io.FileUtil;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.whai.blog.service.IMinioService;
import io.minio.*;
import io.minio.errors.*;
import io.minio.messages.Item;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.ResourceUtils;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.List;

@Service
@Slf4j
public class MinioServiceImpl implements IMinioService {

    @Resource
    private MinioClient minioClient;

    @Value("${minio.bucket}")
    private String defaultBucket;

    private JSONObject contentTypeMap;

    @PostConstruct
    private void init() {
        try {
            // 读取classpath的json文件
            File map = ResourceUtils.getFile("classpath:minio-type-map.json");
            String s = new String(FileUtil.readBytes(map));
            contentTypeMap = JSON.parseObject(s);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private String getType(String fileName) {
        String type = (String) contentTypeMap.get(fileName.substring(fileName.lastIndexOf(".")));
        if (type == null) {
            type = contentTypeMap.get("default").toString();
        }
        return type;
    }

    @Override
    public void uploadFile(String filePath, String bucketName)  {
        uploadFile(new File(filePath), bucketName);
    }

    @Override
    public void uploadFile(File file, String bucketName) {
        try (FileInputStream fileInputStream = new FileInputStream(file);) {
            String type = getType(file.getAbsolutePath());

            // 获取InputStream大小
            long size = fileInputStream.available();

            minioClient.putObject(
                    PutObjectArgs.builder()
                            .bucket(bucketName == null ? defaultBucket : bucketName)
                            .object(file.getName())
                            .stream(fileInputStream, size, -1)
                            .contentType(type)
                            .build());
        } catch (Exception e) {
            log.error("上传文件失败", e);
        }

    }



    @Override
    public void uploadFile(MultipartFile file, String bucketName) {
        try (InputStream inputStream = file.getInputStream()) {
            // 获取InputStream大小
            long size = file.getSize();
            minioClient.putObject(
                    PutObjectArgs.builder()
                            .bucket(bucketName == null ? defaultBucket : bucketName)
                            .object(file.getOriginalFilename())
                            .stream(inputStream, size, -1)
                            .contentType(file.getContentType())
                            .build());
        } catch (Exception e) {
            log.error("上传文件失败", e);
        }
    }

    @Override
    public void downloadFile(String bucketName, String objectName, String filePath) throws ServerException, InsufficientDataException, ErrorResponseException, IOException, NoSuchAlgorithmException, InvalidKeyException, InvalidResponseException, XmlParserException, InternalException {
        minioClient.downloadObject(
                DownloadObjectArgs.builder()
                        .bucket(bucketName == null ? defaultBucket : bucketName)
                        .object(objectName)
                        .filename(filePath) // 输出文件地址
                        .build());
    }

    @Override
    public void deleteFile(String bucketName, String objectName) {

    }

    @Override
    public List<Item> listFiles(String bucketName) {
        return listFiles(bucketName, true);
    }

    @Override
    public List<Item> listFiles(String bucketName, boolean isRecursive) {
        try {
            Iterable<Result<Item>> objects = minioClient.listObjects(
                    ListObjectsArgs.builder().recursive(isRecursive).bucket(bucketName == null ? defaultBucket : bucketName).
                            build()
            );
            ArrayList<Item> items = new ArrayList<>();
            for (Result<Item> object : objects) {
                items.add(object.get());
            }
            return items;
        } catch (Exception e) {
            log.error(e.getMessage());
        }
        return null;
    }

    /**
     * 生成带签名的url提供访问
     * @param bucketName 存储桶名称
     * @param objectName 对象名称
     * @param expiration 过期时间
     */
    @Override
    public void generatePreSignedURL(String bucketName, String objectName, int expiration) {

    }

    @Override
    public void getBucketInfo(String bucketName) {

    }

    @Override
    public void createBucket(String bucketName) {

    }

    @Override
    public void deleteBucket(String bucketName) {

    }

    @Override
    public void setBucketPolicy(String bucketName, String policy) {

    }

    @Override
    public void getBucketPolicy(String bucketName) {

    }
}
