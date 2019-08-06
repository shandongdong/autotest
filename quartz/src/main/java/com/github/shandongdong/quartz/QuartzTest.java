package com.github.shandongdong.quartz;

import org.quartz.JobDetail;
import org.quartz.Scheduler;
import org.quartz.SchedulerException;
import org.quartz.Trigger;
import org.quartz.impl.StdSchedulerFactory;
import org.quartz.impl.calendar.HolidayCalendar;

import java.util.Date;

import static org.quartz.CronScheduleBuilder.dailyAtHourAndMinute;
import static org.quartz.JobBuilder.newJob;
import static org.quartz.SimpleScheduleBuilder.simpleSchedule;
import static org.quartz.TriggerBuilder.newTrigger;

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
            // 使用SchedulerFactory从工厂实例化scheduler对象
            Scheduler scheduler = StdSchedulerFactory.getDefaultScheduler();

            // 启动
            scheduler.start();

            System.out.println("========scheduler.start=========> Name:" + scheduler.getSchedulerName() + ", ID：" + scheduler.getSchedulerInstanceId() + ", Hello Scheduler!!!");

            // 定义一个job，并将其与我们的 HelloJob class 联系起来
            JobDetail job = newJob(HelloJob.class)
                    .withIdentity("job1", "group1")
                    .usingJobData("jobSays", "Hello world")
                    .usingJobData("myFloatValue", 3.1415296f)   //构建JobDetail时，可以将数据放入JobDataMap中，在Job示例中可以通过jobExecutionContext.getJobDetail().getJobDataMap().getXxx获取
                    .storeDurably()    // Durability：如果一个job是非持久的，当没有活跃的trigger与之关联的时候，会被自动地从scheduler中删除。也就是说，非持久的job的生命期是由trigger的存在与否决定的；
                    .requestRecovery()  //RequestsRecovery：如果一个job是可恢复的，并且在其执行的时候，scheduler发生硬关闭（hard shutdown)（比如运行的进程崩溃了，或者关机了），则当scheduler重新启动的时候，该job会被重新执行。此时，该job的JobExecutionContext.isRecovering() 返回true
                    .build();

            // 每过2秒触发一次job执行
            Trigger trigger = newTrigger()
                    .withIdentity("trigger1", "group1")
                    .startNow()
                    .withSchedule(simpleSchedule()
                            .withIntervalInSeconds(2)
                            .repeatForever())
                    .startAt(new Date())    //  设置trigger第一次触发的时间
                    .withPriority(5)    // 优先级,默认5。只有同时触发的trigger之间才会比较优先级。10:59触发的trigger总是在11:00触发的trigger之前执行
                    .build();

            // 自定义要排除的日期
            HolidayCalendar cal = new HolidayCalendar();
            cal.addExcludedDate(new Date());
            cal.addExcludedDate(new Date());
            scheduler.addCalendar("myHolidays", cal, false, false);

            // 设置触发器
            Trigger trigger1 = newTrigger()
                    .withIdentity("myTrigger2", "group2")
                    .forJob("job1")
                    .withSchedule(dailyAtHourAndMinute(9, 30))  // 设置触发器每天9:30开始执行
                    .modifiedByCalendar("myHolidays")   // 但是排除 myHolidays 中的日期，比如节假日之类的
                    .build();


            // 告诉schedule调度器， 这个job 使用的触发器 trigger

            scheduler.scheduleJob(job, trigger);


//                if (args[0].equals("paused")) {
//                    System.out.println("暂停调度器");
//                    scheduler.standby();    //暂停
//                } else {
//                    System.out.println("重新开始触发器");
//                    scheduler.resumeTrigger(trigger.getKey());
//                }

//            在调用shutdown()之前，需要给job的触发和执行预留一些时间
            Thread.sleep(1000 * 10);

            // 调用scheduler.shutdown( )终止scheduler
            scheduler.shutdown();

        } catch (SchedulerException se) {
            se.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

    }
}
