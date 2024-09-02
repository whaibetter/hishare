package com.whai.blog.aspect;


import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.whai.blog.annotation.BrowseLog;
import com.whai.blog.model.BlogBrowse;
import com.whai.blog.service.IBlogBrowseService;
import com.whai.blog.utils.ServletUtils;
import com.whai.blog.utils.StringUtils;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Date;

@Aspect
@Component
@Slf4j
public class BrowseLogAspect {

    @Autowired
    IBlogBrowseService blogBrowseService;


    /**
     * 切入点  配置注解的为切入点
     * <a href="https://docs.spring.io/spring-framework/docs/current/reference/html/core.html#aop-pointcuts-designators">
     *     切入点内容官网介绍
     *     </a>
     */
    @Pointcut("@annotation(com.whai.blog.annotation.BrowseLog)") // the pointcut expression
    private void pointCut() {} // the pointcut signature

    /**
     * 有时，您需要在advice正文中访问返回的实际值。您可以使用绑定返回值的@AfterReturning形式来获取访问权限
     *
     * 记录正常return
     * @param controllerLog BrowseLog注解标识
     * @param jsonResult
     */
    @AfterReturning(pointcut = "@annotation(controllerLog)", returning = "jsonResult")
    public void doAfterReturning(JoinPoint joinPoint , BrowseLog controllerLog, Object jsonResult) {
        //存入浏览记录
        /*RequestAttributes requestAttributes = RequestContextHolder.getRequestAttributes();
        HttpServletRequest request = (HttpServletRequest) requestAttributes
                .resolveReference(RequestAttributes.REFERENCE_REQUEST);*/


        BlogBrowse blogBrowse = new BlogBrowse();

        blogBrowse.setBrowseIp(ServletUtils.getIpAddress());
        blogBrowse.setBrowseReqUrl(ServletUtils.getRequest().getRequestURI());
        blogBrowse.setBrowseReqMethod(joinPoint.getTarget().getClass().getName() + "." + joinPoint.getSignature().getName());
        blogBrowse.setBrowseReqParam(StringUtils.substring(JSON.toJSONString(ServletUtils.getRequestParam().toJSONString()), 0, 100));
        blogBrowse.setBrowseTime(new Date(System.currentTimeMillis()));
        blogBrowse.setBrowseOs(ServletUtils.getOsAndBrowserInfo());
        blogBrowse.setBrowseBrowser(ServletUtils.getOsAndBrowserInfo());
        blogBrowse.setBrowseLocation(ServletUtils.getThisHostLocation());


        log.info(JSONObject.toJSONString(blogBrowse));

        blogBrowseService.save(blogBrowse);

    }

}
