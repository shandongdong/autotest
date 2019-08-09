package com.github.shandongdong.quartz.learn;

import org.quartz.Scheduler;
import org.quartz.SchedulerException;
import org.quartz.impl.StdSchedulerFactory;

import java.util.Properties;

/**
 * @Project: autotest
 * @Author: ShanDongDong
 * @Time: 2019-08-07 20:27
 * @Email: shandongdong@126.com
 * @Description:
 **/
public class QuartzPropertiesTest {
    public static void main(String[] args) throws SchedulerException {
        StdSchedulerFactory stdSchedulerFactory = new StdSchedulerFactory();


        // 通过代码创建配置文件
        Properties properties = new Properties();
        properties.put("org.quartz.scheduler.instanceName", "MyScheduler");
        properties.put("org.quartz.threadPool.threadCount", "5");
        properties.put("org.quartz.jobStore.class", "org.quartz.simpl.RAMJobStore");

        // 加载配置
        stdSchedulerFactory.initialize(properties);

        Scheduler scheduler = stdSchedulerFactory.getScheduler();
        scheduler.start();
    }
}
