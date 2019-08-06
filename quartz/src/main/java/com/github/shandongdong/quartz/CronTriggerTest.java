package com.github.shandongdong.quartz;

import org.quartz.JobDetail;
import org.quartz.Scheduler;
import org.quartz.Trigger;
import org.quartz.impl.StdSchedulerFactory;
import org.quartz.impl.matchers.KeyMatcher;

import static org.quartz.CronScheduleBuilder.cronSchedule;
import static org.quartz.CronScheduleBuilder.dailyAtHourAndMinute;
import static org.quartz.DateBuilder.dateOf;
import static org.quartz.JobBuilder.newJob;
import static org.quartz.JobKey.jobKey;
import static org.quartz.SimpleScheduleBuilder.simpleSchedule;
import static org.quartz.TriggerBuilder.newTrigger;
import static org.quartz.impl.matchers.EverythingMatcher.allJobs;

/**
 * @Project: autotest
 * @Author: ShanDongDong
 * @Time: 2019-08-05 16:23
 * @Email: shandongdong@126.com
 * @Description: <p>
 * CronTrigger通常比Simple Trigger更有用，如果您需要基于日历的概念而不是按照SimpleTrigger的精确指定间隔进行重新启动的作业启动计划。
 * 使用CronTrigger，您可以指定号时间表，例如“每周五中午”或“每个工作日和上午9:30”，甚至“每周一至周五上午9:00至10点之间每5分钟”和1月份的星期五“。
 * 即使如此，和SimpleTrigger一样，CronTrigger有一个startTime，它指定何时生效，以及一个（可选的）endTime，用于指定何时停止计划。
 * <p>
 **/
public class CronTriggerTest {

    public static void main(String[] args) throws Exception {

        Scheduler scheduler = StdSchedulerFactory.getDefaultScheduler();
        scheduler.start();

        JobDetail job1 = newJob(HelloJob.class).build();
        JobDetail job2 = newJob(HelloJob.class).build();

        Trigger trigger1;
        Trigger trigger2;

        // 建立一个触发器，将在上午10:42每天发射
        trigger1 = triggerDailyAtHourAndMinute(job1);
        trigger2 = triggerDailyAtHourAndMinute2(job2);

        //设置调度计划
        System.out.println("=============> 多job并发1111");
        scheduler.scheduleJob(job1, trigger1);
        System.out.println("=============> 多job并发22222");
//        scheduler.scheduleJob(job2, trigger2);
        System.out.println("=============> 多job并发3333");

//            在调用shutdown()之前，需要给job的触发和执行预留一些时间
        Thread.sleep((1000 * (10 * 6)) * 3);
        scheduler.shutdown();
    }

    // 建立一个触发器，将在上午10:42每天发射
    private static Trigger triggerDailyAtHourAndMinute(JobDetail job) {
        Trigger trigger = null;

        trigger = newTrigger()
                .withIdentity("trigger3", "group1")
                .withSchedule(dailyAtHourAndMinute(18, 14)) // 第一种写法
//                .withSchedule(cronSchedule("0 42 10 * * ?"))    // 第二种写法
                .forJob(job)
                .build();

        return trigger;
    }

    // 建立一个触发器，将在上午10:42每天发射
    private static Trigger triggerDailyAtHourAndMinute2(JobDetail job) {
        Trigger trigger = null;

        trigger = newTrigger()
                .withIdentity("trigger4", "group2")
                .withSchedule(cronSchedule("0 42 10 * * ?"))
                .forJob(job)
                .build();

        return trigger;
    }

}
