package com.whai.blog.controller.admin;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.whai.blog.model.SysOperLog;
import com.whai.blog.service.ISysOperLogService;
import com.whai.blog.utils.AjaxResult;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * 用户操作记录 管理员可以查看
 */
@Controller
@RequestMapping("/admin/opr")
public class OprLogController {

    private static final Logger logger = LoggerFactory.getLogger(OprLogController.class);

    private String prefix = "";

    @Autowired
    ISysOperLogService sysOperService;

    /**
     * 跳转blog页面
     * @return
     */
    @GetMapping("toOprList")
    public String userList()
    {
        return prefix + "/admin/OprList";
    }

    /**
     * 用户列表
     * @param page 第几页
     * @return
     */
    @GetMapping("oprList/{page}")
    @ResponseBody
    @ApiOperation("操作日志列表")
//    @Log(value = "获取operation",BUSINESS_TYPE = Log.BusinessType.SELECT)
    public AjaxResult getBlogList(@ApiParam("页码") @PathVariable Integer page) {

        //分页 每页显示25条数据
        Page<SysOperLog> sysOperationLogIPage = new Page<SysOperLog>(page, 15);
        //条件筛选
        QueryWrapper<SysOperLog> select = new QueryWrapper<SysOperLog>();
        Page<SysOperLog> sysOperationLog = sysOperService.page(sysOperationLogIPage, select);

        //blog.hasNext用于判断下一页

        return AjaxResult.success("success", sysOperationLog);
    }
}
