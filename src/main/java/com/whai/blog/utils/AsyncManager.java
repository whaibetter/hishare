package com.whai.blog.utils;

import com.whai.blog.controller.admin.OprLogController;
import com.whai.blog.service.ISysOperLogService;

/**
 * 异步任务管理器
 */
public class AsyncManager {




    private ISysOperLogService logService = (ISysOperLogService) SpringUtils.getBean(OprLogController.class);


}
