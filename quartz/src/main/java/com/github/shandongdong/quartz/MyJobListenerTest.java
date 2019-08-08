package com.github.shandongdong.quartz;

import org.quartz.*;
import org.quartz.impl.StdSchedulerFactory;
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
public class MyJobListenerTest {


    public static void main(String[] args) throws Exception {

        Scheduler scheduler = StdSchedulerFactory.getDefaultScheduler();


        // 定义一个job，并将其与我们的 SimpleTriggerJob class 联系起来
        JobDetail jobDetail = newJob(SimplerJob.class)
                .withIdentity("job1", "group1")
                .build();

        String cronExpression = "0/5 * * ? * *";     //表达式
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

        // 全局的Job Listener
//        scheduler.getListenerManager().addJobListener(new MyJobListener("自定义监听"), EverythingMatcher.allJobs());
        /*
        执行结果：
            监听器的名称是:自定义监听
            监听Scheduler在JobDetail将要被执行时调用的方法
            ====== 正在执行Job任务................========>SimplerJob, time:2019-08-07 21:13:16
            监听器的名称是:自定义监听
            监听到Scheduler在JobDetail 被执行之后调用这个方法
         */

        // 局部的Job Listener
        scheduler.getListenerManager().addJobListener(new MyJobListener("自定义监听"), KeyMatcher.keyEquals(JobKey.jobKey("job1", "group1")));
    }

}
