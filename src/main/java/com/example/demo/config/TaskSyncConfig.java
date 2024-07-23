package com.example.demo.config;

import com.example.demo.bilibili.BiliBiliPlugin;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hsqldb.lib.StopWatch;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@EnableScheduling
@EnableAsync
@Configuration
public class TaskSyncConfig {

    private static final Log logger = LogFactory.getLog(TaskSyncConfig.class);

    @Autowired
    private BiliBiliPlugin syncService;

    public static void main(String[] args) {
        System.out.println(Instant.now());
        System.out.println(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss").format(LocalDateTime.now()));
    }

    @Scheduled(cron = "@midnight")
    public void syncTask() {
        try {
            logger.info("开始同步任务：-------------" + Instant.now());
            StopWatch stopWatch = new StopWatch();
            syncService.sync();
            logger.info("结束同步任务：-------------" + Instant.now());
            logger.info("本次同步耗时：--- " + stopWatch.elapsedTime() + "ms,----- " + stopWatch.elapsedTime() / 1000 + "s");
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

    }

//    @Scheduled(cron = "*/10 * * * * *")
//    public void monitorTask() {
//        BlockingQueue<Root> roots = syncService.watchBlockingQueue();
//        logger.info("当前插入队列中数据：" + roots);
//
//    }


}
