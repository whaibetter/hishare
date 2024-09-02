package com.whai.blog.utils.schedule;//package com.whai.ebbinghaus.utils.schedule;
//
//import org.quartz.*;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.ApplicationArguments;
//import org.springframework.boot.ApplicationRunner;
//import org.springframework.stereotype.Component;
//
///**
// * 这里是基于ApplicationRunner的实现，启动时run方法创建scheduler任务
// */
//@Component
//public class JobInit implements ApplicationRunner {
//
//    private static final String ID = "Memory1";
//
//    @Autowired
//    private Scheduler scheduler;
//
//    @Override
//    public void run(ApplicationArguments args) throws Exception {
//        JobDetail jobDetail = JobBuilder.newJob(RefreshMemoryJob.class)
//                .withIdentity(ID + " 01")
//                .storeDurably()
//                .build();
//        CronScheduleBuilder scheduleBuilder =
//                CronScheduleBuilder.cronSchedule("0/5 * * * * ? *");
//        // 创建任务触发器
//        Trigger trigger = TriggerBuilder.newTrigger()
//                .forJob(jobDetail)
//                .withIdentity(ID + " 01Trigger")
//                .withSchedule(scheduleBuilder)
//                .startNow() //立即執行一次任務
//                .build();
//
//        // 手动将触发器与任务绑定到调度器内
//        scheduler.scheduleJob(jobDetail, trigger);
//    }
//}
