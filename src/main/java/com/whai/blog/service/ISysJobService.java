package com.whai.blog.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.whai.blog.model.SysJob;
import org.quartz.SchedulerException;

/**
 * <p>
 * 定时任务调度表 服务类
 * </p>
 *
 * @author whai
 * @since 2023-06-29
 */
public interface ISysJobService extends IService<SysJob> {

    boolean deleteJob(SysJob job) throws SchedulerException;

    int addJob(SysJob job) throws Exception;

    Integer updateJob(SysJob job) throws Exception;

    Integer changeStatus(SysJob job) throws SchedulerException;

    /**
     * 暂停任务
     *
     * @param job 调度信息
     * @return 结果
     */
    int pauseJob(SysJob job) throws SchedulerException;

    /**
     * 恢复任务
     *
     * @param job 调度信息
     * @return 结果
     */
    int resumeJob(SysJob job) throws SchedulerException;

}
