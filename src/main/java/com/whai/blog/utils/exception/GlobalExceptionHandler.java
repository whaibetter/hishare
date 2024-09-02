package com.whai.blog.utils.exception;

import com.whai.blog.utils.AjaxResult;
import com.whai.blog.utils.ValidateUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.validation.BindException;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import javax.servlet.http.HttpServletRequest;
import java.net.ConnectException;
import java.text.SimpleDateFormat;
import java.util.Date;

@RestControllerAdvice
@Slf4j
public class GlobalExceptionHandler{


    /**
     * 权限校验异常
     */
    @ExceptionHandler(AccessDeniedException.class)
    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    public AjaxResult handleAccessDeniedException(AccessDeniedException e, HttpServletRequest request)
    {
        String requestURI = request.getRequestURI();
        e.printStackTrace();
        log.error("请求地址'{}',权限校验失败'{}'", requestURI, e.getMessage());
        return AjaxResult.error("没有权限，请联系管理员授权");
    }

    /**
     * 校验异常
     */
    @ExceptionHandler(value = MethodArgumentNotValidException.class)
    public AjaxResult handleMethodArgumentNotValidException(MethodArgumentNotValidException e)
    {
        BindingResult bindingResult = e.getBindingResult();
        String validateErrorMessage = ValidateUtils.getValidateErrorMessage(bindingResult);
        log.error(" 参数校验失败'{}'", bindingResult);
        e.printStackTrace();
        return AjaxResult.error(validateErrorMessage);
    }

    /**
     * 普通的参数传递的形式;
     * @author  khy
     * @createTime 2021年5月18日下午5:25:16
     * @param req
     * @return
     * @throws Exception
     */
    @ExceptionHandler(value = BindException.class)
    @ResponseBody
    public AjaxResult validatedGetException(HttpServletRequest req, BindException e){

        BindingResult bindingResult = e.getBindingResult();
        String validateErrorMessage = ValidateUtils.getValidateErrorMessage(bindingResult);
        log.error(" 参数校验失败'{}',有'{}'处错误！", bindingResult,bindingResult.getFieldErrorCount());
        e.printStackTrace();
        AjaxResult error = AjaxResult.error(validateErrorMessage);
        error.put("timestamp", new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()));
        return error;
    }

    /**
     * 拦截hdfs未启动
     * @return
     * @throws Exception
     */
    @ExceptionHandler(value = HdfsDisableException.class)
    @ResponseBody
    public AjaxResult hdfsDisableException(HdfsDisableException e){
        log.warn(e.getMessage());
        AjaxResult error = AjaxResult.warn(e.getMessage());
        error.put("timestamp", new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()));
        return error;
    }

    /**
     * 拦截hdfs未启动
     * @return
     * @throws  ConnectException
     */
    @ExceptionHandler(value = ConnectException.class)
    @ResponseBody
    public AjaxResult hdfsConnectException(ConnectException e){
        log.error(e.getMessage());
        AjaxResult error = AjaxResult.error(e.getMessage());
        error.put("timestamp", new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()));
        return error;
    }

//ConnectTimeoutException

    /**
     * 全局异常处理
     * @param req
     * @param e
     * @return
     * @throws Exception
     */
    @ExceptionHandler(value = Exception.class)
    @ResponseBody
    public Object exception(HttpServletRequest req, Exception e) throws Exception {
        log.error(" 异常'{}'",e.getClass().getName() +  e.getMessage());
        e.printStackTrace();
        AjaxResult error = AjaxResult.error(e.getMessage());
        error.put("timestamp", new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()));
        return error;
    }





}
