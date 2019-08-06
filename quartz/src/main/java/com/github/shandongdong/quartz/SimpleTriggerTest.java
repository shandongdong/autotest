package com.github.shandongdong.quartz;

import org.quartz.*;
import org.quartz.impl.StdSchedulerFactory;
import org.quartz.impl.calendar.HolidayCalendar;

import java.util.Date;

import static org.quartz.CronScheduleBuilder.dailyAtHourAndMinute;
import static org.quartz.DateBuilder.dateOf;
import static org.quartz.DateBuilder.futureDate;
import static org.quartz.JobBuilder.newJob;
import static org.quartz.SimpleScheduleBuilder.simpleSchedule;
import static org.quartz.TriggerBuilder.newTrigger;

/**
 * @Project: autotest
 * @Author: ShanDongDong
 * @Time: 2019-08-05 15:50
 * @Email: shandongdong@126.com
 * @Description: SimpleTrigger可以满足的调度需求是：在具体的时间点执行一次，或者在具体的时间点执行，并且以指定的间隔重复执行若干次。
 * 比如，你有一个trigger，你可以设置它在2015年1月13日的上午11:23:54准时触发，或者在这个时间点触发，并且每隔2秒触发一次，一共重复5次。
 **/
public class SimpleTriggerTest {

    public static void main(String[] args) throws Exception {

        Scheduler scheduler = StdSchedulerFactory.getDefaultScheduler();
        scheduler.start();

        // 定义一个job，并将其与我们的 HelloJob class 联系起来
        JobDetail job = newJob(HelloJob.class)
                .withIdentity("job1", "group1")
                .build();

        Trigger trigger;
        // 指定时间触发，每隔10秒执行一次，重复10次：
//        trigger = repeatCountTrigger(job);

        // 5秒以后开始触发，仅执行一次：
//        trigger = triggerByMiniute(job);


        //立即触发，每个5秒执行一次，直到16:11
        trigger = triggerByEndAt(job);

        //立即触发，每个5秒执行一次，直到16:11
        scheduler.scheduleJob(job, trigger);
//            在调用shutdown()之前，需要给job的触发和执行预留一些时间
        Thread.sleep((1000 * (10 * 6)) * 3);
        // 调用scheduler.shutdown( )终止scheduler
        scheduler.shutdown();
    }

    private static Trigger repeatCountTrigger(JobDetail job) {
        // 指定时间触发，每隔10秒执行一次，重复10次：
        return newTrigger()
                .withIdentity("trigger1", "group1")
                .startAt(new Date())    //  设置trigger第一次触发的时间
                .withSchedule(simpleSchedule()
                        .withIntervalInSeconds(1)
                        .withRepeatCount(10))
                .forJob(job)
                .build();
    }

    // 5秒以后开始触发，仅执行一次：
    private static Trigger triggerByMiniute(JobDetail job) {
        return newTrigger()
                .withIdentity("trigger5", "group1")
                .startAt(futureDate(5, DateBuilder.IntervalUnit.SECOND)) // use DateBuilder to create a date in the future
                .forJob(job) // identify job with its JobKey
                .build();
    }

    //立即触发，每个5秒执行一次，直到16:11
    private static Trigger triggerByEndAt(JobDetail job) {
        return newTrigger()
                .withIdentity("trigger7", "group1")
                .withSchedule(simpleSchedule()
                        .withIntervalInSeconds(5)
                        .repeatForever())
                .endAt(dateOf(16, 17, 0))
                .build();
    }

}
