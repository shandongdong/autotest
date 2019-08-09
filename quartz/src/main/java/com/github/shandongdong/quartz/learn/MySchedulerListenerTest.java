package com.github.shandongdong.quartz.learn;

import org.quartz.*;
import org.quartz.impl.StdSchedulerFactory;

import java.text.SimpleDateFormat;
import java.util.Date;

import static org.quartz.JobBuilder.newJob;

/**
 * @Project: autotest
 * @Author: ShanDongDong
 * @Time: 2019-08-05 18:16
 * @Email: shandongdong@126.com
 * @Description:
 **/
public class MySchedulerListenerTest {


    public static void main(String[] args) throws Exception {

        Scheduler scheduler = StdSchedulerFactory.getDefaultScheduler();

        // 定义一个job，并将其与我们的 SimpleTriggerJob class 联系起来
        JobDetail jobDetail = newJob(SimplerJob.class)
                .withIdentity("job1", "group1")
                .build();

        String cronExpression = "0/5 * * * * ?";     //表达式
        boolean c = CronExpression.isValidExpression(cronExpression);
        // 触发器
        Trigger trigger = TriggerBuilder.newTrigger()
                .withIdentity("trigger1", "group1")
                .withSchedule(CronScheduleBuilder.cronSchedule(cronExpression))         // 使用日历方式设定定时器，只需要改着一行代码
                .build();

        scheduler.scheduleJob(jobDetail, trigger);

        System.out.println("====================================调度器执行开始=========> Name:" + scheduler.getSchedulerName() + ", ID：" + scheduler.getSchedulerInstanceId()
                + ", time:" + new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()));
        scheduler.start();

        // 创建监听器
        scheduler.getListenerManager().addSchedulerListener(new MySchedulerListener());

        // 移除对应的监听器监听
//        scheduler.getListenerManager().removeSchedulerListener(new MySchedulerListener());

        Thread.sleep(8000L);
        scheduler.shutdown();
    }
}
