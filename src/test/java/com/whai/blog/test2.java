package com.whai.blog;

import com.whai.blog.service.IBlogMainService;
import com.whai.blog.utils.filter.BloomFilter;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import javax.annotation.Resource;

@SpringBootTest
@Slf4j
public class test2 {

    @Test
    void contextLoads() throws Exception {
        AnnotationConfigApplicationContext applicationContext = new AnnotationConfigApplicationContext("com.whai.blog.service");
        IBlogMainService bean = applicationContext.getBean(IBlogMainService.class);



    }


    @Resource
    private BloomFilter redisBloomFilter ;
    @Test
    void test() throws Exception {
        redisBloomFilter.put("123", "b");
        System.out.println(redisBloomFilter.mightContain("123", "b"));
    }


}
