package com.whai.blog.config;

import com.whai.blog.component.security.*;
import com.whai.blog.service.impl.SysUserServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.authentication.logout.LogoutFilter;
import org.springframework.web.filter.CorsFilter;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled=true)
public class SecurityConfig extends WebSecurityConfigurerAdapter {
     /**
     * 自定义服务类
     * @return
     */
    @Autowired
    private SysUserServiceImpl sysUserService;

//    @Autowired
//    private MyAuthenticationProvider myAuthenticationProvider;

    @Autowired
    private MyAuthenticationSuccessHandler myAuthenticationSuccessHandler;  // 自定义登录成功处理
    @Autowired
    private MyAuthenticationFailureHandler myAuthenticationFailureHandler;  // 自定义登录失败处理

    @Autowired
    private JwtAuthenticationEntryPoint jwtAuthenticationEntryPoint;

    @Autowired
    private JwtLogoutSuccessHandler jwtLogoutSuccessHandler;

    @Autowired
    private JwtAccessDeniedHandler jwtAccessDeniedHandler;

    @Autowired
    private MyAuthenticationProvider myAuthenticationProvider;


    @Bean
    JwtAuthenticationFilter jwtAuthenticationFilter() throws Exception {
        JwtAuthenticationFilter jwtAuthenticationFilter = new JwtAuthenticationFilter(authenticationManager());
        return jwtAuthenticationFilter;
    }

    @Autowired
    private CorsFilter corsFilter;

    @Override
    protected void configure(HttpSecurity http) throws Exception {

        /**
         * 登陆登出逻辑
         * 1. 登出过滤器 登出成功处理器
         * 2. 登陆---》 验证码过滤器-----》用户名密码过滤器----->认证管理器
         *              认证失败管理器
         * 3. 异常处理过滤器---》认证异常...
         */



//        http.authorizeHttpRequests()
//                .antMatchers("/admin/**","/swagger-ui.html").hasRole("admin")
//                .antMatchers("/*.svg","/*.png","/*.js","/*.css","/*.jpg","/**","swagger-ui.html").permitAll();

//        http.authorizeRequests()
//                .antMatchers("/admin*/**").hasRole("admin");//访问指定页面过滤
//        这里用/admin/**是不能用的

        http.authorizeRequests()
//                .antMatchers("/admin/**").hasRole("admin")
//                .antMatchers("/swagger-ui**").permitAll()
//                .antMatchers("/admin/**","/swagger-ui**").permitAll()
                .anyRequest().permitAll()
                .and()


                /*登陆配置*/
                .formLogin()
                .loginProcessingUrl("/login") /*action 里登陆接口的地址*/
                .successHandler(myAuthenticationSuccessHandler) //自定义处理成功处理器
                .failureHandler(myAuthenticationFailureHandler)//自定义处理失败处理器
                .and()
                // 基于token不要session
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS).and()


                /*验证失败 异常处理*/
                .exceptionHandling()
                .authenticationEntryPoint(jwtAuthenticationEntryPoint)
                .accessDeniedHandler(jwtAccessDeniedHandler)
                .and()

                .cors().and().csrf().disable()

                /*配置了iframe 如swagger，druid monitor才能正常访问 https://blog.csdn.net/mathcoder23/article/details/108224089 */
                .headers().frameOptions().disable().and()

                /*登出处理器*/
                .logout().logoutSuccessHandler(jwtLogoutSuccessHandler).logoutUrl("/logout").and()
                /*自定义过滤器 token*/
                .addFilter(jwtAuthenticationFilter())
                /*cors过滤器*/
                .addFilterBefore(corsFilter, JwtAuthenticationFilter.class)  /*提前加入corsfilter解决跨域*/
                .addFilterBefore(corsFilter, LogoutFilter.class);

    }



    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.authenticationProvider(myAuthenticationProvider);
    }
    @Bean
    public static BCryptPasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }



//    @Bean
//    public CorsFilter corsFilter() {
//        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
//
//        CorsConfiguration corsConfiguration = new CorsConfiguration();
//        corsConfiguration.addAllowedOrigin("*");
//        corsConfiguration.addAllowedHeader("*");
//        corsConfiguration.addAllowedMethod("*");
//        corsConfiguration.setAllowCredentials(true);
//
//        source.registerCorsConfiguration("/**", corsConfiguration);
//        return new CorsFilter(source);
//    }

//
//
//
//
//

//
//
//    @Bean
//    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
////        http.authorizeRequests().anyRequest().permitAll();  ///测试接口全部放行
//        http.formLogin().successForwardUrl("/toIndex");
//        http.authorizeRequests()
//                .antMatchers("/admin/*").hasRole("ADMIN");
////
//        http.authorizeRequests().antMatchers("/*.svg","/*.png","/*.js","/*.css","/*.jpg","/**","swagger-ui.html").permitAll();
////        http.formLogin().defaultSuccessUrl("/toIndex");
////
////
//
////        http.authenticationProvider(myAuthenticationProvider);
//        http.csrf().disable();
//        SysUserServiceImpl sysUserService = this.sysUserService;
//        http.userDetailsService(sysUserService);
////        http.authenticationProvider(myAuthenticationProvider);
//
//
////        自定义服务类
////        根据传入的自定义UserDetailsService添加身份验证。然后它返回一个daoauthenticationconfigururer，以允许自定义身份验证。
////        该方法还确保UserDetailsService对getDefaultUserDetailsService()方法可用。注意，其他的UserDetailsService可能会覆盖这个UserDetailsService作为默认值。
//////        http.userDetailsService(sysUserService);
//
//        return http.build();
//    }
//
////    @Bean
////    public UserDetailsService userDetailsService() {
////
//////        https://docs.spring.io/spring-security/reference/6.0.0-M6/servlet/authentication/passwords/in-memory.html
//////        * 一下为在内存中存入
////
////        UserDetails user = User.withDefaultPasswordEncoder()
////                .username("user")
////                .password("password")
////                .roles("USER")
////                .build();
////        UserDetails admin = User.withDefaultPasswordEncoder()
////                .username("admin")
////                .password("password")
////                .roles("ADMIN", "USER")
////                .build();
////        return new InMemoryUserDetailsManager(user, admin);
////
////
////
////    }
//
//
////    @Bean
////    public SysUserServiceImpl userDetailsService() {
////
//////        https://docs.spring.io/spring-security/reference/6.0.0-M6/servlet/authentication/passwords/in-memory.html
//////
////        return new SysUserServiceImpl();
////    }
//
//
//    /**
//     * 更改方式，为jdbc
//     *
//     Spring Security’s JdbcDaoImpl implements UserDetailsService
//     to provide support for username-and-password-based authentication
//     that is retrieved by using JDBC.
//     JdbcUserDetailsManager extends JdbcDaoImpl
//     to provide management of UserDetails through the UserDetailsManager interface.
//     UserDetails-based authentication is used by Spring Security
//     when it is configured to accept a username/password for authentication.
//
//
//     什么是UserDetailsService？我需要它吗？
//     UserDetailsService是一个DAO接口，用于加载特定于用户帐户的数据。除了加载该数据以供框架内的其他组件使用之外，它没有其他功能。它不负责验证用户。使用用户名/密码组合对用户进行身份验证最常见的是由DaoAuthenticationProvider执行，它注入了UserDetailsService，以允许它加载用户的密码（和其他数据），以便将其与提交的值进行比较。请注意，如果您使用LDAP，这种方法可能不起作用。
//     如果您想自定义身份验证过程，那么您应该自己实现AuthenticationProvider。有关将SpringSecurity身份验证与GoogleAppEngine集成的示例，请参阅本文。
//
//     https://docs.spring.io/spring-security/reference/servlet/authentication/passwords/user-details-service.html
//
//
//     * @return
//     */
//
//
//
//
////
////
////
////
////
//
//
//    /*@Bean
//    DataSource dataSource() {
//        return new DruidDataSourceFactory().getDataSource();
//    }*/
//
//

}
