package com.github.shandongdong.quartz.learn;

import org.quartz.*;
import org.quartz.impl.StdSchedulerFactory;

import java.util.Date;
import java.util.Properties;

/**
 * @Project: autotest
 * @Author: ShanDongDong
 * @Time: 2019-08-19 12:37
 * @Email: shandongdong@126.com
 * @Description: 手动运行触发器
 **/
public class RunOnesTriggerTest {
    static Scheduler scheduler = null;

    public static void main(String[] args) throws Exception {
        StdSchedulerFactory stdSchedulerFactory = new StdSchedulerFactory();
        // 通过代码创建配置文件
        Properties properties = new Properties();
        properties.put("org.quartz.scheduler.instanceName", "MyScheduler");
        properties.put("org.quartz.threadPool.threadCount", "5");
        properties.put("org.quartz.jobStore.class", "org.quartz.simpl.RAMJobStore");

        // 加载配置
        stdSchedulerFactory.initialize(properties);

        scheduler = stdSchedulerFactory.getScheduler();


        runOnceJob("job1", "gg", "tt", "ttg", SimplerJob.class, null);
        runOnceJob("job2", "gg2", "tt2", "ttg2", SimplerJob.class, null);
        runOnceJob("job3", "gg3", "tt3", "ttg3", SimplerJob.class, null);
        runOnceJob("job4", "gg4", "tt4", "ttg4", SimplerJob.class, null);


        scheduler.start();
        Thread.sleep(2000);
        scheduler.shutdown();
    }


    /**
     * 添加一个执行任务, 立即触发来执行，用于手动执行任务
     */
    public static void runOnceJob(String jobName, String jobGroupName, String triggerName, String triggerGroupName,
                                  Class clazz, JobDataMap jobExtraDataMap) {
        try {
            // 创建一项作业
            JobDetail job = JobBuilder.newJob(clazz).withIdentity(jobName, jobGroupName).build();

            // 触发时间点
            SimpleScheduleBuilder simpleScheduleBuilder = SimpleScheduleBuilder.simpleSchedule().withRepeatCount(0);

            TriggerBuilder<SimpleTrigger> simpleTriggerTriggerBuilder = TriggerBuilder.newTrigger().withIdentity(triggerName, triggerGroupName).withSchedule(simpleScheduleBuilder).startAt(new Date());
            if (null != jobExtraDataMap) {
                simpleTriggerTriggerBuilder.usingJobData(jobExtraDataMap);
            }
            Trigger trigger = simpleTriggerTriggerBuilder.build();
            scheduler.scheduleJob(job, trigger);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

}
