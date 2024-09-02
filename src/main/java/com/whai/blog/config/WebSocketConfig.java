package com.whai.blog.config;//package com.whai.blog.config;
//
//import org.springframework.context.annotation.Configuration;
//import org.springframework.messaging.simp.config.MessageBrokerRegistry;
//import org.springframework.web.socket.config.annotation.EnableWebSocketMessageBroker;
//import org.springframework.web.socket.config.annotation.StompEndpointRegistry;
//import org.springframework.web.socket.config.annotation.WebSocketMessageBrokerConfigurer;
//
///**
// * WebSocket 为浏览器和服务器提供了双工异步通信的功能，即浏览器可以向服务器发送信息，反之也成立。
// * 服务端有消息时，将消息发送给所有连接了当前 endpoint 的浏览器。
// */
//@Configuration
///**
// * @EnableWebSocketMessageBroker 注解用于开启使用STOMP协议来传输基于代理（MessageBroker）的消息，这时候控制器（controller）
// * 开始支持@MessageMapping,就像是使用@requestMapping一样。
// */
//@EnableWebSocketMessageBroker
//public class WebSocketConfig implements WebSocketMessageBrokerConfigurer {
//
//    /**
//     * 配置Broker，表明可以在topic域上可以向客户端发送消息
//     * 当客户端向服务端发起请求是需要/app前缀
//     */
//    @Override
//    public void configureMessageBroker(MessageBrokerRegistry config) {
//        // 设置广播节点 服务端发送消息，客户端订阅就能接收消息的节点
//        config.enableSimpleBroker("/topic", "/user");
//        // 客户端向服务端发送消息需有/app 前缀
//        config.setApplicationDestinationPrefixes("/app");
//        // 指定用户发送（一对一）的前缀 /user/ 设置客户端向服务端发送消息的节点。
//        config.setUserDestinationPrefix("/user/");
//    }
//    /**
//     *配置WebSocket连接的端点
//     */
//    @Override
//    public void registerStompEndpoints(StompEndpointRegistry registry) {
//        registry.addEndpoint("/gs-guide-websocket")
//                .withSockJS();
//    }
//}

import org.springframework.context.annotation.Configuration;

//import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
//import org.springframework.boot.web.servlet.ServletContextInitializer;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.web.socket.server.standard.ServerEndpointExporter;
//import org.springframework.web.util.WebAppRootListener;
//
//import javax.servlet.ServletContext;
//import javax.servlet.ServletException;
//
//@Configuration
//@EnableAutoConfiguration
//public class WebSocketConfig implements ServletContextInitializer {
//
//    /**
//     * 给spring容器注入这个ServerEndpointExporter对象
//     * 相当于xml：
//     * <beans>
//     * <bean id="serverEndpointExporter" class="org.springframework.web.socket.server.standard.ServerEndpointExporter"/>
//     * </beans>
//     * <p>
//     * 检测所有带有@serverEndpoint注解的bean并注册他们。
//     *
//     * @return
//     */
//    @Bean
//    public ServerEndpointExporter serverEndpointExporter() {
//        return new ServerEndpointExporter();
//    }
//
//    @Override
//    public void onStartup(ServletContext servletContext) throws ServletException {
//
//        //使用初始化所需的任何 servlet、过滤器、侦听器上下文参数和属性配置给定的ServletContext。
//        servletContext.addListener(WebAppRootListener.class);
//        servletContext.setInitParameter("org.apache.tomcat.websocket.textBufferSize","52428800");
//        servletContext.setInitParameter("org.apache.tomcat.websocket.binaryBufferSize","52428800");
//    }
//
//}
@Configuration
public class WebSocketConfig {

//    @Bean
//    public ServerEndpointExporter serverEndpointExporter() {
//        return new ServerEndpointExporter();
//    }
}
