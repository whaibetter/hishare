package com.whai.blog.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.whai.blog.constant.ScheduleConstants;
import com.whai.blog.mapper.SysJobMapper;
import com.whai.blog.model.SysJob;
import com.whai.blog.service.ISysJobService;
import com.whai.blog.utils.schedule.ScheduleUtils;
import lombok.extern.slf4j.Slf4j;
import org.quartz.JobKey;
import org.quartz.Scheduler;
import org.quartz.SchedulerException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * <p>
 * 定时任务调度表 服务实现类
 * </p>
 *
 * @author whai
 * @since 2023-06-29
 */
@Service
@Slf4j
public class SysJobServiceImpl extends ServiceImpl<SysJobMapper, SysJob> implements ISysJobService {

    @Autowired
    Scheduler scheduler;
    @Autowired
    SysJobMapper mapper;
    /**
     * 删除 job
     * @param job
     * @return
     */
    @Override
    public boolean deleteJob(SysJob job) throws SchedulerException {
        int i = mapper.deleteById(job);
        scheduler.deleteJob(ScheduleUtils.getJobKey(job.getJobId(), job.getJobGroup()));
        return i != 0;
    }

    @Override
    public int addJob(SysJob job) throws Exception {
        // 一个插入job表
        int insert = mapper.insert(job);
        // 创建计划任务
        ScheduleUtils.createScheduleJob(scheduler, job);
        return insert;
    }

    @Override
    public Integer updateJob(SysJob job) throws Exception {
        //这个是查询的 完整的job
        int i = mapper.updateById(job);
        SysJob sysJob = mapper.selectById(job.getJobId());
        if (i > 0) {
            updateSchedulerJob(sysJob, sysJob.getJobGroup());
        }
        return i;
    }

    @Override
    public Integer changeStatus(SysJob job) throws SchedulerException {

        int rows = 0;
        String status = job.getStatus();
        if (ScheduleConstants.Status.NORMAL.getValue().equals(status))
        {
            rows = resumeJob(job);
        }
        else if (ScheduleConstants.Status.PAUSE.getValue().equals(status))
        {
            rows = pauseJob(job);
        }
        return rows;
    }

    /**
     * 暂停任务
     *
     * @param job 调度信息
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public int pauseJob(SysJob job) throws SchedulerException
    {
        Long jobId = job.getJobId();
        String jobGroup = job.getJobGroup();
        job.setStatus(ScheduleConstants.Status.PAUSE.getValue());
        int rows = mapper.updateById(job);
        if (rows > 0)
        {
            scheduler.pauseJob(ScheduleUtils.getJobKey(jobId, jobGroup));
        }
        return rows;
    }

    /**
     * 恢复任务
     *
     * @param job 调度信息
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public int resumeJob(SysJob job) throws SchedulerException
    {
        Long jobId = job.getJobId();
        String jobGroup = job.getJobGroup();
        job.setStatus(ScheduleConstants.Status.NORMAL.getValue());
        int rows = mapper.updateById(job);
        if (rows > 0)
        {
            scheduler.resumeJob(ScheduleUtils.getJobKey(jobId, jobGroup));
        }
        return rows;
    }


    /**
     * 更新任务
     * @param job 任务对象
     * @param jobGroup 任务组名
     */
    public void updateSchedulerJob(SysJob job, String jobGroup) throws Exception
    {
        Long jobId = job.getJobId();
        // 判断是否存在
        JobKey jobKey = ScheduleUtils.getJobKey(jobId, jobGroup);
        if (scheduler.checkExists(jobKey))
        {
            // 防止创建时存在数据问题 先移除，然后在执行创建操作
            scheduler.deleteJob(jobKey);
        }
        ScheduleUtils.createScheduleJob(scheduler, job);
    }
}
