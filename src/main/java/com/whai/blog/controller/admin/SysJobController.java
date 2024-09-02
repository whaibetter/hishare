package com.whai.blog.controller.admin;


import com.whai.blog.constant.Constants;
import com.whai.blog.constant.ScheduleConstants;
import com.whai.blog.model.SysJob;
import com.whai.blog.service.ISysJobService;
import com.whai.blog.utils.AjaxResult;
import com.whai.blog.utils.SecurityCustomUtils;
import com.whai.blog.utils.StringUtils;
import com.whai.blog.utils.schedule.CronUtils;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.quartz.Scheduler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;


/**
 * <p>
 * 定时任务调度表 前端控制器
 * </p>
 *
 * @author whai
 * @since 2023-06-29
 */
@RestController
@RequestMapping("/admin/job")
@Slf4j
public class SysJobController {

    @Autowired
    private Scheduler scheduler;

    @Autowired
    private ISysJobService jobService;


    @GetMapping("/list")
    @ApiOperation("job任务")
    @Transactional(rollbackFor = Exception.class)
    public AjaxResult list() throws Exception {
        List<SysJob> list = jobService.list();
        return AjaxResult.success(list);
    }

    /**
     *
     * @param job
     *         SysJob job = new SysJob(
     *                 1L, "job",
     *                 "group",
     *                 "com.whai.ebbinghaus.utils.schedule.RefreshMemoryJob.execute()",
     *                 "0/10 * * * * ?",
     *                 "3",
     *                 "1", "0",
     *                 "admin",
     *                 new Date(), null, null, null);
     * @return
     * @throws Exception
     */
    @PostMapping("/add")
    @ApiOperation("job任务save")
    @Transactional(rollbackFor = Exception.class)
    public AjaxResult addScheduling(@RequestBody SysJob job) throws Exception {


        if (!CronUtils.isValid(job.getCronExpression()))
        {
            return AjaxResult.error("修改任务'" + job.getJobName() + "'失败，Cron表达式不正确");
        }
        else if (StringUtils.containsIgnoreCase(job.getInvokeTarget(), Constants.LOOKUP_RMI))
        {
            return AjaxResult.error("修改任务'" + job.getJobName() + "'失败，目标字符串不允许'rmi'调用");
        }
        else if (StringUtils.containsAnyIgnoreCase(job.getInvokeTarget(), new String[] { Constants.LOOKUP_LDAP, Constants.LOOKUP_LDAPS }))
        {
            return AjaxResult.error("修改任务'" + job.getJobName() + "'失败，目标字符串不允许'ldap(s)'调用");
        }
        else if (StringUtils.containsAnyIgnoreCase(job.getInvokeTarget(), new String[] { Constants.HTTP, Constants.HTTPS }))
        {
            return AjaxResult.error("修改任务'" + job.getJobName() + "'失败，目标字符串不允许'http(s)'调用");
        }
        else if (StringUtils.containsAnyIgnoreCase(job.getInvokeTarget(), Constants.JOB_ERROR_STR))
        {
            return AjaxResult.error("修改任务'" + job.getJobName() + "'失败，目标字符串存在违规");
        } else if (job.addJobNeedNotNull() != null) {
            return job.addJobNeedNotNull();
        }

//        job.setUpdateBy();
        job.setCreateBy(SecurityCustomUtils.getUserName());
        job.setCreateTime(new Date());
        job.setMisfirePolicy(ScheduleConstants.MISFIRE_DO_NOTHING);
        job.setStatus(ScheduleConstants.Status.NORMAL.getValue());

        int i = jobService.addJob(job);
        return i!=0?AjaxResult.success():AjaxResult.error("添加job失败！");
    }

    @DeleteMapping("/delete")
    @ApiOperation("delete任务")
    @Transactional(rollbackFor = Exception.class)
    public AjaxResult deleteScheduling(@RequestParam("jobId") String jobId) throws Exception {

        // 创建job赋予scheduler

        SysJob job = jobService.getById(jobId);
        if (job == null) {
            return AjaxResult.error("该job不存在！");
        }
        boolean b = jobService.deleteJob(job);
        log.info("delete Job:" + job);
        return  b?AjaxResult.success():AjaxResult.error("删除失败");
    }

    @PostMapping("/update")
    @ApiOperation("update任务")
    @Transactional(rollbackFor = Exception.class)
    public AjaxResult updateScheduling(@RequestBody SysJob job) throws Exception {

        if (!CronUtils.isValid(job.getCronExpression()))
        {
            return AjaxResult.error("修改任务'" + job.getJobName() + "'失败，Cron表达式不正确");
        }
        else if (StringUtils.containsIgnoreCase(job.getInvokeTarget(), Constants.LOOKUP_RMI))
        {
            return AjaxResult.error("修改任务'" + job.getJobName() + "'失败，目标字符串不允许'rmi'调用");
        }
        else if (StringUtils.containsAnyIgnoreCase(job.getInvokeTarget(), new String[] { Constants.LOOKUP_LDAP, Constants.LOOKUP_LDAPS }))
        {
            return AjaxResult.error("修改任务'" + job.getJobName() + "'失败，目标字符串不允许'ldap(s)'调用");
        }
        else if (StringUtils.containsAnyIgnoreCase(job.getInvokeTarget(), new String[] { Constants.HTTP, Constants.HTTPS }))
        {
            return AjaxResult.error("修改任务'" + job.getJobName() + "'失败，目标字符串不允许'http(s)'调用");
        }
        else if (StringUtils.containsAnyIgnoreCase(job.getInvokeTarget(), Constants.JOB_ERROR_STR))
        {
            return AjaxResult.error("修改任务'" + job.getJobName() + "'失败，目标字符串存在违规");
        }
//        else if (!ScheduleUtils.whiteList(job.getInvokeTarget()))
//        {
//            return AjaxResult.error("修改任务'" + job.getJobName() + "'失败，目标字符串不在白名单内");
//        }

        Integer i = jobService.updateJob(job);

        return i!=0?AjaxResult.success():AjaxResult.error();
    }

    @PostMapping("/changeStatus")
    @ApiOperation("改变执行状态")
    @Transactional(rollbackFor = Exception.class)
    public AjaxResult handleStatusChange(SysJob job) throws Exception {


        SysJob newJob = jobService.getById(job.getJobId());
        newJob.setStatus(job.getStatus());
        Integer i = jobService.changeStatus(newJob);

        return i!=0?AjaxResult.success():AjaxResult.error();
    }








}
