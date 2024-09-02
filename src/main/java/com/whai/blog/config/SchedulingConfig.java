package com.whai.blog.config;


import org.quartz.Scheduler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.quartz.SchedulerFactoryBean;

import javax.sql.DataSource;

@Configuration
//@EnableAsync
//@EnableScheduling
public class SchedulingConfig {

    @Autowired
    private DataSource dataSource;


    /**
     *     执行任务。有了触发器，我们就可以执行任务了。注册一个SchedulerFactoryBean,然后将触发器以list的方式传入
     */
    @Bean
    public SchedulerFactoryBean schedulerFactoryBean(DataSource druidDataSource){
        SchedulerFactoryBean schedulerFactoryBean=new SchedulerFactoryBean();
        //调度器名称
        schedulerFactoryBean.setSchedulerName("refreshMemoryScheduler");
        //数据源
        schedulerFactoryBean.setDataSource(druidDataSource);
        //覆盖已存在的任务,用于Quartz集群，QuartzScheduler启动会更新已存在的Job
        schedulerFactoryBean.setOverwriteExistingJobs(true);
        //延时1s启动定时任务，避免系统未完全启动却开始执行定时任务的情况
        schedulerFactoryBean.setStartupDelay(1);
        //设置加载的quartz.properties配置文件
//        schedulerFactoryBean.setConfigLocation(new ClassPathResource("/quartz.properties"));
        //自动启动
        schedulerFactoryBean.setAutoStartup(true);
        //注册触发器
//        schedulerFactoryBean.setTriggers(memoryTrigger);

        return schedulerFactoryBean;
    }

    /**
     *  可以在service中获取，并往里头添加或者删除job
     * @return
     */
    @Bean
    public Scheduler scheduler() {
        return schedulerFactoryBean(dataSource).getScheduler();
    }

}
