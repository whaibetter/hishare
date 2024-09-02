package com.whai.blog.config;

import io.swagger.models.auth.In;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.*;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spi.service.contexts.OperationContext;
import springfox.documentation.spi.service.contexts.SecurityContext;
import springfox.documentation.spring.web.plugins.Docket;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Predicate;

/**
 * Swagger2的接口配置
 *
 * @author /
 */
@Configuration
public class SwaggerConfig
{
    /** 是否开启swagger */
    @Value("${swagger.enabled}")
    private boolean enabled;

    @Value("${swagger.pathMapping}")
    private String pathMapping;

    @Value("${whaiblog.version}")
    private String blogVersion;

    @Value("${whaiblog.url}")
    private String blogUrl;

    @Value("${whaiblog.email}")
    private String blogEmail;

    @Value("${whaiblog.name}")
    private String blogName;

    /**
     * 创建API
     */
    @Bean
    public Docket createRestApi()
    {
        return new Docket(DocumentationType.OAS_30)
                // 是否启用Swagger
                .enable(enabled)
                // 用来创建该API的基本信息，展示在文档的页面中（自定义展示的信息）
                .apiInfo(apiInfo())
                // 设置哪些接口暴露给Swagger展示
                .select()
                // 扫描所有有注解的api，用这种方式更灵活
                .apis(RequestHandlerSelectors.basePackage("com.whai.blog.controller"))
//                .apis(RequestHandlerSelectors.withMethodAnnotation(ApiOperation.class))
                // 扫描指定包中的swagger注解
                // 扫描所有 .apis(RequestHandlerSelectors.any())
                .paths(PathSelectors.any())
                .build()
                /* 设置安全模式，swagger可以设置访问token */
                // 配置全局io.swagger.model。安全方案适用于所有或部分api操作。
                .securitySchemes(securitySchemes())
                // 配置哪些api操作(通过正则表达式模式)和HTTP方法将安全上下文应用于api。
                .securityContexts(securityContexts())
                .pathMapping(pathMapping);
                /* 设置安全模式，swagger可以设置访问token */

    }


    /**
     * 安全模式，这里指定token通过头请求头Token传递
     *
     */
    private List<SecurityScheme> securitySchemes()
    {
        List<SecurityScheme> apiKeyList = new ArrayList<SecurityScheme>();
        apiKeyList.add(new ApiKey("Token", "Token", In.HEADER.toValue()));
        return apiKeyList;
    }

    /**
     * 安全上下文
     */
    private List<SecurityContext> securityContexts()
    {
        List<SecurityContext> securityContexts = new ArrayList<>();
        securityContexts.add(
                SecurityContext.builder()
                        .securityReferences(defaultAuth())
                        .operationSelector(
                                new Predicate<OperationContext>() {
                            @Override
                            public boolean test(OperationContext operationContext) {
                                return operationContext.requestMappingPattern().matches("/.*");
                            }
                        })
                        .build());
        return securityContexts;
    }

    /**
     * 默认的安全上引用
     */
    private List<SecurityReference> defaultAuth()
    {
        /* AuthorizationScope scope定义接口权限单独范围名称 */
        AuthorizationScope authorizationScope = new AuthorizationScope("global", "accessEverything");
        AuthorizationScope[] authorizationScopes = new AuthorizationScope[1];
        authorizationScopes[0] = authorizationScope;
        List<SecurityReference> securityReferences = new ArrayList<>();
        securityReferences.add(new SecurityReference("Token", authorizationScopes));
        return securityReferences;
    }


    /**
     * 添加摘要信息
     */
    private ApiInfo apiInfo()
    {
        // 用ApiInfoBuilder进行定制
        return new ApiInfoBuilder()
                // 设置标题
                .title("whaiblog 接口文档")
                // 描述
                .description("描述")
                // 作者信息
                .contact(new Contact(blogName,blogUrl,blogEmail))
                // 版本
                .version("版本号:"+blogVersion)
                .build();
    }

}
