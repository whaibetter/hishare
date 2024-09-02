package com.whai.blog.utils.schedule;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.Date;

@Slf4j
@Component
//@DisallowConcurrentExecution //保证上一次任务执行完毕再执行下一任务
//@PersistJobDataAfterExecution //上一个任务完成前写入需要被下一个任务获取的变量以及对应的属性值，类似求和累加
public class RefreshMemoryJob {



    public void execute() {
        log.info("执行" + new Date());
    }

}
