package com.github.shandongdong.quartz;

import org.quartz.JobDetail;
import org.quartz.Scheduler;
import org.quartz.Trigger;
import org.quartz.impl.StdSchedulerFactory;

import java.util.Date;

import static org.quartz.JobBuilder.newJob;
import static org.quartz.TriggerBuilder.newTrigger;
import static org.quartz.impl.matchers.EverythingMatcher.allJobs;

/**
 * @Project: autotest
 * @Author: ShanDongDong
 * @Time: 2019-08-05 18:16
 * @Email: shandongdong@126.com
 * @Description:
 **/
public class JobListenerTest {


    public static void main(String[] args) throws Exception {

        Scheduler scheduler = StdSchedulerFactory.getDefaultScheduler();
        scheduler.start();

        JobDetail job1 = newJob(HelloJob.class).build();

        Trigger trigger1;

        // 建立一个触发器，将在上午10:42每天发射
        trigger1 = triggerDailyAtHourAndMinute(job1);

        //设置调度计划
        System.out.println("=============> 多job并发1111");
        scheduler.scheduleJob(job1, trigger1);  // 多线程执行，后面的语句不用等这行执行完
        System.out.println("=============> 多job并发22222");


        // 添加监听
        scheduler.getListenerManager().addJobListener(new MyJobListener("MyJobListener"), allJobs());


//            在调用shutdown()之前，需要给job的触发和执行预留一些时间
//        Thread.sleep((1000 * (10 * 6)) * 3);
//        scheduler.shutdown();
    }

    private static Trigger triggerDailyAtHourAndMinute(JobDetail job) {
        Trigger trigger = null;

        trigger = newTrigger()
                .withIdentity("trigger3", "group1")
                .startAt(new Date())
//                .withSchedule(simpleSchedule())
                .forJob(job)
                .build();

        return trigger;
    }

}
