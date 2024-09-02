package com.whai.blog.service;


import io.minio.errors.*;
import io.minio.messages.Item;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.List;

public interface IMinioService {

    /**
     * 上传文件到Minio存储桶
     *
     * @param filePath   文件路径
     * @param bucketName 存储桶名称
     */
    void uploadFile(String filePath, String bucketName);
    void uploadFile(File file, String bucketName);

    /**
     * 上传文件到Minio存储桶
     *
     * @param file       前端来的文件
     * @param bucketName 存储桶名称
     */
    void uploadFile(MultipartFile file, String bucketName);

    /**
     * 下载文件从Minio存储桶
     * @param bucketName 存储桶名称
     * @param objectName 对象名称
     * @param filePath 文件路径
     */
    void downloadFile(String bucketName, String objectName, String filePath) throws ServerException, InsufficientDataException, ErrorResponseException, IOException, NoSuchAlgorithmException, InvalidKeyException, InvalidResponseException, XmlParserException, InternalException;

    /**
     * 删除Minio存储桶中的文件
     * @param bucketName 存储桶名称
     * @param objectName 对象名称
     */
    void deleteFile(String bucketName, String objectName);

    /**
     * 列出Minio存储桶中的文件列表
     *
     * @param bucketName 存储桶名称
     * @return
     */
    List<Item> listFiles(String bucketName);

    List<Item> listFiles(String bucketName, boolean isRecursive);

    /**
     * 生成带有签名URL的文件，以便在特定时间范围内进行文件下载
     * @param bucketName 存储桶名称
     * @param objectName 对象名称
     * @param expiration 过期时间
     */
    void generatePreSignedURL(String bucketName, String objectName, int expiration);

    /**
     * 获取Minio存储桶的信息
     * @param bucketName 存储桶名称
     */
    void getBucketInfo(String bucketName);

    /**
     * 在Minio中创建存储桶
     * @param bucketName 存储桶名称
     */
    void createBucket(String bucketName);

    /**
     * 删除MinIO中的存储桶
     * @param bucketName 存储桶名称
     */
    void deleteBucket(String bucketName);

    /**
     * 设置MinIO存储桶的策略
     * @param bucketName 存储桶名称
     * @param policy 策略内容
     */
    void setBucketPolicy(String bucketName, String policy);

    /**
     * 获取MinIO存储桶的策略
     * @param bucketName 存储桶名称
     */
    void getBucketPolicy(String bucketName);
}
