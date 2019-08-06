package com.github.shandongdong.quartz;

import org.quartz.*;


/**
 * @Project: autotest
 * @Author: ShanDongDong
 * @Time: 2019-08-05 17:17
 * @Email: shandongdong@126.com
 * @Description:
 **/
public class MyJobListener implements JobListener {
    private String name;

    public MyJobListener(String name) {
        if (name == null) {
            throw new IllegalArgumentException("Listener name cannot be null!");
        } else {
            this.name = name;
        }
    }

    @Override
    public String getName() {
        return this.name;
    }


    @Override
    public void jobToBeExecuted(JobExecutionContext jobExecutionContext) {
        System.out.println("监听到Job准备开始执行");
    }

    @Override
    public void jobExecutionVetoed(JobExecutionContext jobExecutionContext) {
        System.out.println("监听到Job被禁用了");

    }

    @Override
    public void jobWasExecuted(JobExecutionContext jobExecutionContext, JobExecutionException e) {
        System.out.println("监听到Job已经执行完成");

        JobKey jobKey = jobExecutionContext.getJobDetail().getKey();

        try {
            jobExecutionContext.getScheduler().triggerJob(jobKey);
            System.out.println(jobKey.getName());
        } catch (SchedulerException ex) {
            ex.printStackTrace();
        }

        try {
            jobExecutionContext.getScheduler().shutdown();  //执行完job之后关闭执行器
        } catch (SchedulerException ex) {
            ex.printStackTrace();
        }


    }
}
