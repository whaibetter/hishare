package com.whai.blog.config;

import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.MediaType;
import org.springframework.web.context.request.RequestContextListener;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;
import org.springframework.web.servlet.config.annotation.*;
import org.springframework.web.servlet.view.InternalResourceViewResolver;

/**
 * mvc配置
 */
@EnableWebMvc
@Configuration
@Slf4j
public class SpringMVCConfig implements WebMvcConfigurer {


    /**
     * 开启跨域
     *
     * <a href='https://blog.csdn.net/qq_24427099/article/details/106261979'>跨域问题失效</a>
     * 原因是客户端请求经过的先后顺序问题，当服务端接收到一个请求时，该请求会先经过过滤器，然后进入拦截器中，然后再进入Mapping映射中的路径所指向的资源，所以跨域配置在mapping上并不起作用，返回的头信息中并没有配置的跨域信息，浏览器就会报跨域异常。
     *
     * 正确的解决跨域问题的方法时使用CorsFilter过滤器CorsFilter
     * ————————————————
     * 版权声明：本文为CSDN博主「Lishilin4510」的原创文章，遵循CC 4.0 BY-SA版权协议，转载请附上原文出处链接及本声明。
     * 原文链接：https://blog.csdn.net/qq_24427099/article/details/106261979
     *
     */
    /**
     * 跨域配置
     */
    @Bean
    public CorsFilter corsFilter()
    {
        CorsConfiguration config = new CorsConfiguration();
        config.setAllowCredentials(true);
        // 设置访问源地址
        config.addAllowedOrigin("*");
        // 设置访问源请求头
        config.addAllowedHeader("*");
        // 设置访问源请求方法
        config.addAllowedMethod("*");
        // 有效期 1800秒
        config.setMaxAge(1800L);
        // 添加映射路径，拦截一切请求
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", config);
        // 返回新的CorsFilter
        return new CorsFilter(source);
    }

    @Bean
    public RequestContextListener requestContextListener(){
        return new RequestContextListener();
    }


    @Override
    public void addViewControllers(ViewControllerRegistry registry) {
        registry.addViewController("/").setViewName("redirect:/toIndex");

    }

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {

        String property = System.getProperty("user.dir");
        if (property.equals("/")) {
            // linux环境里，如果是在
            property = "";
        }
        log.info("System.getProperty(\"user.dir\") : =====>  " + property);

//        registry.addResourceHandler("/**").addResourceLocations( "classpath:/static/");
        //文件图片映射

        String imgPath = "file:" + property + "/img/";
        registry.addResourceHandler("/img/**")
                .addResourceLocations(imgPath);

        //文件映射
        String filePath = "file:" + property + "/file/";
        registry.addResourceHandler("/file/**")
                .addResourceLocations(filePath);

        //文件映射
        String logsPath = "file:" + property + "/logs/";
        registry.addResourceHandler("logs/**")
                .addResourceLocations(logsPath);


        /*放行swagger*/
        registry.addResourceHandler("/swagger-ui/**")
                .addResourceLocations("classpath:/META-INF/resources/webjars/springfox-swagger-ui/");
        /*html这个文件存储在springfox-swagger-ui-2.9.2.jar!\META-INF\resources\swagger-ui.html*/
//        registry.addResourceHandler("/webjars/**")
//                .addResourceLocations("classpath:/META-INF/resources/webjars/");
        /*必须将springfox-swagger-ui-2.9.2.jar!\META-INF\resources\webjars\下的文件也进行放行*/
    }

    @Override
    public void configureViewResolvers(ViewResolverRegistry registry) {
        InternalResourceViewResolver viewResolver = new InternalResourceViewResolver("classpath*:/template/", ".html");
        registry.viewResolver(viewResolver);
    }

    /**
     * 设置默认内容类型
     * @param configurer
     */
    @Override
    public void configureContentNegotiation(ContentNegotiationConfigurer configurer) {
        configurer.defaultContentType(MediaType.APPLICATION_JSON);
        WebMvcConfigurer.super.configureContentNegotiation(configurer);
    }
}
