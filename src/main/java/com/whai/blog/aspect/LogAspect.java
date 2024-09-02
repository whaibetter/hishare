package com.whai.blog.aspect;


import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.support.spring.PropertyPreFilters;
import com.whai.blog.annotation.Log;
import com.whai.blog.model.SysOperLog;
import com.whai.blog.service.ISysOperLogService;
import com.whai.blog.utils.ServletUtils;
import com.whai.blog.utils.SpringUtils;
import com.whai.blog.utils.StringUtils;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;


@Component
@Aspect
@Slf4j
public class LogAspect {
    private static final Logger logger = LoggerFactory.getLogger(LogAspect.class);



    /** 排除敏感属性字段 */
    public static final String[] EXCLUDE_PROPERTIES = { "password", "oldPassword", "newPassword", "confirmPassword" };


    /**
     * 切入点  配置注解的为切入点
     * <a href="https://docs.spring.io/spring-framework/docs/current/reference/html/core.html#aop-pointcuts-designators">
     *     切入点内容官网介绍
     *     </a>
     */
    @Pointcut("@annotation(com.whai.blog.annotation.Log)") // the pointcut expression
    private void pointCut() {} // the pointcut signature



    @Before("pointCut()")
    public void doBefore() {
        logger.info("切面前before方法执行");
    }


    /**
     * 有时，您需要在advice正文中访问返回的实际值。您可以使用绑定返回值的@AfterReturning形式来获取访问权限
     *
     * 记录正常return
     * @param controllerLog
     * @param jsonResult
     */
    @AfterReturning(pointcut = "@annotation(controllerLog)", returning = "jsonResult")
    public void doAfterReturning(JoinPoint joinPoint , Log controllerLog, Object jsonResult) {
        handleLog(joinPoint, controllerLog, jsonResult, null);
    }

    /**
     * 记录报错和操作
     * @param joinPoint
     * @param controllerLog
     * @param e
     */
    @AfterThrowing(pointcut = "@annotation(controllerLog)", throwing = "e")
    public void doAfterThrowing(JoinPoint joinPoint , Log controllerLog, Exception e) {
        handleLog(joinPoint, controllerLog, null, e);
    }

    void handleLog(final JoinPoint joinPoint, Log controllerLog, Object jsonResult, final Exception e) {

        //日志注解Log内的值
        String value = controllerLog.value();
        String method = controllerLog.BUSINESS_TYPE().toString();

        SysOperLog operation = new SysOperLog();
        operation.setMethod(method);
        operation.setBusinessType(ServletUtils.getRequest().getMethod()); //post or get ...
        operation.setOperIp(ServletUtils.getIpAddress());
        String classWithMethodName = joinPoint.getTarget().getClass().getName() + "." + joinPoint.getSignature().getName();
        operation.setRequestMethod(classWithMethodName); //类的名称+方法名称
        operation.setErrorMsg(e==null?null:e.getMessage());
        operation.setOperUrl(ServletUtils.getRequest().getRequestURI());
        operation.setOperName(value);

        String oprParam = ServletUtils.getRequestParam().toJSONString();
        // 操作过滤敏感参数
        operation.setOperParam(StringUtils.substring(JSON.toJSONString(oprParam, excludePropertyPreFilter()), 0, 300));

        Map<String,Object> logMap = new HashMap<>();
//        log.info(JSONUtil.parse(webLog).toString());
        //返回参数
        ISysOperLogService logService = SpringUtils.getBean(ISysOperLogService.class);
        logService.save(operation);

    }

    /**
     * 忽略敏感属性
     */
    public PropertyPreFilters.MySimplePropertyPreFilter excludePropertyPreFilter()
    {
        return new PropertyPreFilters().addFilter().addExcludes(EXCLUDE_PROPERTIES);
    }




}

