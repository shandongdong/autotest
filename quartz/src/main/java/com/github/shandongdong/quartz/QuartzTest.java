package com.github.shandongdong.quartz;

import org.quartz.*;
import org.quartz.impl.StdSchedulerFactory;
import org.quartz.impl.calendar.HolidayCalendar;

import java.text.SimpleDateFormat;
import java.util.Date;

import static org.quartz.CronScheduleBuilder.dailyAtHourAndMinute;
import static org.quartz.SimpleScheduleBuilder.simpleSchedule;

/**
 * @Project: autotest
 * @Author: ShanDongDong
 * @Time: 2019-08-05 10:24
 * @Email: shandongdong@126.com
 * @Description: 获取scheduler实例对象，启动，然后关闭
 **/
public class QuartzTest {

    public static void main(String[] args) {

        try {
            // 1：调度器。使用SchedulerFactory从工厂实例化scheduler对象
            Scheduler scheduler = StdSchedulerFactory.getDefaultScheduler();

            System.out.println("====================================scheduler.start=========> Name:" + scheduler.getSchedulerName() + ", ID：" + scheduler.getSchedulerInstanceId()
                    + ", time:" + new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()));

            // 2：任务实例。定义一个job，并将其与我们的 HelloJob class 联系起来
            JobDetail jobDetail = JobBuilder.newJob(HelloJob.class)
                    .withIdentity("job1", "group1")     // job1任务的唯一实例，group1组名称
                    .withDescription("My first job1")
                    .usingJobData("jobSays", "Hello world")
                    .usingJobData("myFloatValue", 3.1415296f)   //构建JobDetail时，可以将数据放入JobDataMap中，在Job示例中可以通过jobExecutionContext.getJobDetail().getJobDataMap().getXxx获取
                    .usingJobData("message", "JobDetail数据")
                    .usingJobData("count", 0)
                    .storeDurably()    // Durability：如果一个job是非持久的，当没有活跃的trigger与之关联的时候，会被自动地从scheduler中删除。也就是说，非持久的job的生命期是由trigger的存在与否决定的；
                    .requestRecovery()  //RequestsRecovery：如果一个job是可恢复的，并且在其执行的时候，scheduler发生硬关闭（hard shutdown)（比如运行的进程崩溃了，或者关机了），则当scheduler重新启动的时候，该job会被重新执行。此时，该job的JobExecutionContext.isRecovering() 返回true
                    .build();
            System.out.println("可以通过JobDetail获取Job的属性, 比如名称:" + jobDetail.getKey().getName());
            System.out.println("可以通过JobDetail获取Job的属性, 比如描述:" + jobDetail.getDescription());
            System.out.println("可以通过JobDetail获取Job的属性, 比如组名称:" + jobDetail.getKey().getGroup());
            System.out.println("可以通过JobDetail获取Job的属性, 比如类名称:" + jobDetail.getJobClass().getName());

            // 3：触发器。每过2秒触发一次job执行
            Trigger trigger = TriggerBuilder.newTrigger()
                    .withIdentity("trigger1", "group1")
                    .startNow()                             // 立即启动触发器
                    .withSchedule(simpleSchedule()
                            .withIntervalInSeconds(2)       // 间隔时间
                            .repeatForever())               // 重复次数
                    .withPriority(5)                    // 优先级,默认5。只有同时触发的trigger之间才会比较优先级。10:59触发的trigger总是在11:00触发的trigger之前执行
                    .usingJobData("message", "触发器数据")
                    .startAt(new Date())            // 设置触发器第一次被触发执行的时间，默认是当前时间
                    .endAt(new Date())              //设置触发器终止的时间
                    .build();

            // 自定义要排除的日期
            HolidayCalendar cal = new HolidayCalendar();
            cal.addExcludedDate(new Date());
            cal.addExcludedDate(new Date());
            scheduler.addCalendar("myHolidays", cal, false, false);

            // 设置触发器2.一个job可以绑定多个触发器，但是一个触发器只能绑定一个job
            Trigger trigger2 = TriggerBuilder.newTrigger()
                    .withIdentity("myTrigger2", "group2")
                    .forJob("job1")
                    .withSchedule(dailyAtHourAndMinute(9, 30))  // 设置触发器每天9:30开始执行
                    .modifiedByCalendar("myHolidays")   // 但是排除 myHolidays 中的日期，比如节假日之类的
                    .build();


            // 告诉schedule调度器， 这个job 使用的触发器 trigger
            scheduler.scheduleJob(jobDetail, trigger);

            // 启动
            scheduler.start();
//            在调用shutdown()之前，需要给job的触发和执行预留一些时间
            Thread.sleep(1000 * 4);

            // 调用scheduler.shutdown( )终止scheduler
            scheduler.shutdown();
            System.out.println("====================================scheduler.end=========> Name:" + scheduler.getSchedulerName() + ", ID：" + scheduler.getSchedulerInstanceId()
                    + ", time:" + new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()));

        } catch (SchedulerException se) {
            se.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

    }
}
