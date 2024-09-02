package com.whai.blog.config.properties;

import lombok.Data;

/**
 * @version 1.0
 * @Author whai文海
 * @Date 2024/9/2 21:48
 * @注释
 */
@Data
public class OssProperties {
    /**
     * 上传文件前缀路径
     */
    private String prefix;
    /**
     * oss类型
     */
    private String type;
    /**
     * 下面几个是oss的配置参数
     */
    private String endpoint;
    private String ak;
    private String sk;
    private String bucket;
    private String host;
}
