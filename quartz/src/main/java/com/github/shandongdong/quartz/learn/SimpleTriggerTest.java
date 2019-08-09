package com.github.shandongdong.quartz.learn;

import org.quartz.*;
import org.quartz.impl.StdSchedulerFactory;

import java.text.SimpleDateFormat;
import java.util.Date;

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
        // 任务的开始时间
        Date startDate = new Date();
        // 延迟2秒执行
        startDate.setTime(startDate.getTime() + 2000);
        // 任务的结束时间
        Date endDate = new Date();
        endDate.setTime(endDate.getTime() + 10000);     // 延迟2秒开始执行，任务结束时间是10秒，那么这个任务理论上是时间是8秒


        // 定义一个job，并将其与我们的 SimpleTriggerJob class 联系起来
        JobDetail jobDetail = newJob(SimpleTriggerJob.class)
                .withIdentity("job1", "group1")
                .build();

        // 触发器
        Trigger trigger = TriggerBuilder.newTrigger()
                .withIdentity("trigger1", "group1")
                .startAt(startDate)          // 设置触发器第一次被触发执行的时间，默认是当前时间
                .endAt(endDate)              // 设置触发器终止的时间
                .withSchedule(SimpleScheduleBuilder.simpleSchedule()
                                .withIntervalInSeconds(2)       // 每过2秒触发一次
                                .withRepeatCount(0)             // 重复的次数.设置为0，表示执行一次。这个执行次数受结束时间影响，不能超过任务结束时间
//                        .repeatForever()                // 一直循环触发直到任务的结束时间
                )
                .build();

        scheduler.scheduleJob(jobDetail, trigger);

        System.out.println("====================================调度器执行开始=========> Name:" + scheduler.getSchedulerName() + ", ID：" + scheduler.getSchedulerInstanceId()
                + ", time:" + new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()));
        scheduler.start();

//        scheduler.shutdown();
        System.out.println("====================================调度器执行结束=========> Name:" + scheduler.getSchedulerName() + ", ID：" + scheduler.getSchedulerInstanceId()
                + ", time:" + new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()));

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
