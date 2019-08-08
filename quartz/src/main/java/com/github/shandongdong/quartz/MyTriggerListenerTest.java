package com.github.shandongdong.quartz;

import org.quartz.*;
import org.quartz.impl.StdSchedulerFactory;
import org.quartz.impl.matchers.EverythingMatcher;
import org.quartz.impl.matchers.KeyMatcher;

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
public class MyTriggerListenerTest {


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

        // 全局监听器
        scheduler.getListenerManager().addTriggerListener(new MyTriggerListener("MyTrigger"), EverythingMatcher.allTriggers());

        // 局部
        scheduler.getListenerManager().addTriggerListener(new MyTriggerListener("MyTrigger "), KeyMatcher.keyEquals(TriggerKey.triggerKey("trigger1", "group1")));
    }

}
