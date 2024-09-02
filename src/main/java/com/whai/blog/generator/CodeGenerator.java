package com.whai.blog.generator;


import com.baomidou.mybatisplus.generator.FastAutoGenerator;
import com.baomidou.mybatisplus.generator.config.OutputFile;
import com.baomidou.mybatisplus.generator.engine.FreemarkerTemplateEngine;

import java.util.Collections;

/**
 * <p>
 * 代码生成器演示
 * </p>
 */
public class CodeGenerator {

    public static void main(String[] args) {
        FastAutoGenerator.create("jdbc:mysql://localhost:3306/blog?useUnicode=true&characterEncoding=utf8&zeroDateTimeBehavior=convertToNull&useSSL=true&serverTimezone=GMT%2B8", "root", "12138qwe")
                .globalConfig(builder -> {
                    builder.author("whai") // 设置作者
                            .enableSwagger() // 开启 swagger 模式
                            .fileOverride() // 覆盖已生成文件
                            .outputDir("D://output//"); // 指定输出目录
                })
                .packageConfig(builder -> {
                    builder.parent("com.whai.blog") // 设置父包名
                            .pathInfo(Collections.singletonMap(OutputFile.xml, "D://output//")); // 设置mapperXml生成路径
                })
                .strategyConfig(builder -> {
                    builder.enableCapitalMode();
                })
                .templateEngine(new FreemarkerTemplateEngine()) // 使用Freemarker引擎模板，默认的是Velocity引擎模板
                .execute();

    }

}
