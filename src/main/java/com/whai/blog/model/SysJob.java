package com.whai.blog.model;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.whai.blog.utils.AjaxResult;
import com.whai.blog.utils.StringUtils;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.io.Serializable;
import java.util.Date;


/**
 * <p>
 * 定时任务调度表
 * </p>
 *
 * @author whai
 * @since 2023-06-29
 */
@TableName("sys_job")
@ApiModel(value = "SysJob对象", description = "定时任务调度表")
public class SysJob implements Serializable {

    private static final long serialVersionUID = 1L;

    public SysJob() {
    }

    @ApiModelProperty("任务ID")
    @TableId(value = "job_id", type = IdType.AUTO)
    private Long jobId;

    @ApiModelProperty(value = "任务名称",required = true)
    private String jobName;

    @ApiModelProperty(value = "任务组名", example = "default")
    private String jobGroup;

    @ApiModelProperty(value = "调用目标字符串", example = "com.whai.blog.utils.schedule.RefreshMemoryJob.execute()")
    private String invokeTarget;

    @ApiModelProperty(value = "cron执行表达式", example = "0 0/1 * * * ?", required = true)
    private String cronExpression;

    @ApiModelProperty("计划执行错误策略（1立即执行 2执行一次 3放弃执行）")
    private String misfirePolicy;

    @ApiModelProperty("是否并发执行（0允许 1禁止）")
    private String concurrent;

    @ApiModelProperty("状态（0正常 1暂停）")
    private String status;

    @ApiModelProperty("创建者")
    private String createBy;

    @ApiModelProperty("创建时间")
    private Date createTime;

    @ApiModelProperty("更新者")
    private String updateBy;

    @ApiModelProperty("更新时间")
    private Date updateTime;

    @ApiModelProperty("备注信息")
    private String remark;

    /**
     * 加入的时候，部分参数不能为空
     *
     * @return
     */
    public AjaxResult addJobNeedNotNull() {
        if (StringUtils.isEmpty(this.jobName)) {
            return AjaxResult.error("jobName 空");
        } else if (StringUtils.isEmpty(this.jobGroup)) {
            return AjaxResult.error("jobGroup 空");
        } else if (StringUtils.isEmpty(this.invokeTarget)) {
            return AjaxResult.error("invokeTarget 空 全限定类名可以调用这个方法 com.whai.ebbinghaus.utils.schedule.RefreshMemoryJob.execute()");
        } else if (StringUtils.isEmpty(this.cronExpression)) {
            return AjaxResult.error("cron表达式 空");
        }
        return null;
    }

    public SysJob(Long jobId, String jobName, String jobGroup, String invokeTarget, String cronExpression, String misfirePolicy, String concurrent, String status, String createBy, Date createTime, String updateBy, Date updateTime, String remark) {
        this.jobId = jobId;
        this.jobName = jobName;
        this.jobGroup = jobGroup;
        this.invokeTarget = invokeTarget;
        this.cronExpression = cronExpression;
        this.misfirePolicy = misfirePolicy;
        this.concurrent = concurrent;
        this.status = status;
        this.createBy = createBy;
        this.createTime = createTime;
        this.updateBy = updateBy;
        this.updateTime = updateTime;
        this.remark = remark;
    }

    public Long getJobId() {
        return jobId;
    }

    public void setJobId(Long jobId) {
        this.jobId = jobId;
    }

    public String getJobName() {
        return jobName;
    }

    public void setJobName(String jobName) {
        this.jobName = jobName;
    }

    public String getJobGroup() {
        return jobGroup;
    }

    public void setJobGroup(String jobGroup) {
        this.jobGroup = jobGroup;
    }

    public String getInvokeTarget() {
        return invokeTarget;
    }

    public void setInvokeTarget(String invokeTarget) {
        this.invokeTarget = invokeTarget;
    }

    public String getCronExpression() {
        return cronExpression;
    }

    public void setCronExpression(String cronExpression) {
        this.cronExpression = cronExpression;
    }

    public String getMisfirePolicy() {
        return misfirePolicy;
    }

    public void setMisfirePolicy(String misfirePolicy) {
        this.misfirePolicy = misfirePolicy;
    }

    public String getConcurrent() {
        return concurrent;
    }

    public void setConcurrent(String concurrent) {
        this.concurrent = concurrent;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getCreateBy() {
        return createBy;
    }

    public void setCreateBy(String createBy) {
        this.createBy = createBy;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public String getUpdateBy() {
        return updateBy;
    }

    public void setUpdateBy(String updateBy) {
        this.updateBy = updateBy;
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    @Override
    public String toString() {
        return "SysJob{" +
                "jobId=" + jobId +
                ", jobName=" + jobName +
                ", jobGroup=" + jobGroup +
                ", invokeTarget=" + invokeTarget +
                ", cronExpression=" + cronExpression +
                ", misfirePolicy=" + misfirePolicy +
                ", concurrent=" + concurrent +
                ", status=" + status +
                ", createBy=" + createBy +
                ", createTime=" + createTime +
                ", updateBy=" + updateBy +
                ", updateTime=" + updateTime +
                ", remark=" + remark +
                "}";
    }
}
