package com.whai.blog.config;


import org.mybatis.spring.annotation.MapperScan;
import org.springframework.context.annotation.Configuration;

@Configuration
@MapperScan("com.whai.**.mapper")
public class MybatisConfig {
}
