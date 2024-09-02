//package com.whai.blog.config;
//
//import org.springframework.core.Ordered;
//import org.springframework.core.annotation.Order;
//import org.springframework.stereotype.Component;
//import org.springframework.web.cors.CorsConfiguration;
//import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
//import org.springframework.web.filter.CorsFilter;
//
//import java.util.Arrays;
//import java.util.List;
//
///**
// * 1. mvcConfig配置cors无效
// *
// * <a href='https://blog.csdn.net/qq_24427099/article/details/106261979'>跨域问题失效</a>
// *      * 原因是客户端请求经过的先后顺序问题，当服务端接收到一个请求时，该请求会先经过过滤器，然后进入拦截器中，然后再进入Mapping映射中的路径所指向的资源，所以跨域配置在mapping上并不起作用，返回的头信息中并没有配置的跨域信息，浏览器就会报跨域异常。
// *      *
// *      * 正确的解决跨域问题的方法时使用CorsFilter过滤器CorsFilter
// *      * ————————————————
// *      * 版权声明：本文为CSDN博主「Lishilin4510」的原创文章，遵循CC 4.0 BY-SA版权协议，转载请附上原文出处链接及本声明。
// *      * 原文链接：https://blog.csdn.net/qq_24427099/article/details/106261979
// *      *
// *
// * 2. 使用了corsFilter进行跨域处理 会报错 和Security冲突
// *
// * <a href="https://blog.csdn.net/Afox4l/article/details/89213363" > 解决跨域filter报错</a>
// */
//
//@Component
//@Order(Ordered.HIGHEST_PRECEDENCE)
//public class AjaxCorsFilter extends CorsFilter {
//    public AjaxCorsFilter() {
//        super(configurationSource());
//    }
//
//    private static UrlBasedCorsConfigurationSource configurationSource() {
//        CorsConfiguration corsConfig = new CorsConfiguration();
//        List<String> allowedHeaders = Arrays.asList("x-auth-token", "content-type", "X-Requested-With", "XMLHttpRequest");
//        List<String> exposedHeaders = Arrays.asList("x-auth-token", "content-type", "X-Requested-With", "XMLHttpRequest");
//        List<String> allowedMethods = Arrays.asList("POST", "GET", "DELETE", "PUT", "OPTIONS");
//        List<String> allowedOrigins = Arrays.asList("*");
//        corsConfig.setAllowedHeaders(allowedHeaders);
//        corsConfig.setAllowedMethods(allowedMethods);
//        corsConfig.setAllowedOriginPatterns(allowedOrigins);
//        corsConfig.setExposedHeaders(exposedHeaders);
//        corsConfig.setMaxAge(36000L);
//        corsConfig.setAllowCredentials(true);
//
//        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
//        source.registerCorsConfiguration("/**", corsConfig);
//        return source;
//    }
//}
