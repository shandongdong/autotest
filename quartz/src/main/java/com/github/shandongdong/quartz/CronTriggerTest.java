package com.github.shandongdong.quartz;

import org.quartz.*;
import org.quartz.impl.StdSchedulerFactory;

import java.text.SimpleDateFormat;
import java.util.Date;


import static org.quartz.JobBuilder.newJob;


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
        // 任务的开始时间
        Date startDate = new Date();
        // 延迟2秒执行
        startDate.setTime(startDate.getTime() + 2000);
        // 任务的结束时间
        Date endDate = new Date();
        endDate.setTime(endDate.getTime() + 10000);     // 延迟2秒开始执行，任务结束时间是10秒，那么这个任务理论上是时间是8秒


        // 定义一个job，并将其与我们的 SimpleTriggerJob class 联系起来
        JobDetail jobDetail = newJob(CronTriggerJob.class)
                .withIdentity("job1", "group1")
                .build();

        String cronExpression = "0/2 * * 7 8 ?";     //表达式
        boolean c = CronExpression.isValidExpression(cronExpression);
        System.out.println("表达式是否正确：" + c);
        // 触发器
        Trigger trigger = TriggerBuilder.newTrigger()
                .withIdentity("trigger1", "group1")
                .startAt(startDate)          // 设置触发器第一次被触发执行的时间，默认是当前时间
                .endAt(endDate)              // 设置触发器终止的时间
                .withSchedule(CronScheduleBuilder.cronSchedule(cronExpression))         // 使用日历方式设定定时器，只需要改这一行代码
                .build();

        scheduler.scheduleJob(jobDetail, trigger);

        System.out.println("====================================调度器执行开始=========> Name:" + scheduler.getSchedulerName() + ", ID：" + scheduler.getSchedulerInstanceId()
                + ", time:" + new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()));
        scheduler.start();

        scheduler.standby();        // 暂停
        System.out.println("暂定2秒");
        Thread.sleep(2000);

        scheduler.start();
        scheduler.shutdown(true);
        System.out.println("====================================调度器执行结束=========> Name:" + scheduler.getSchedulerName() + ", ID：" + scheduler.getSchedulerInstanceId()
                + ", time:" + new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()));

    }

}
