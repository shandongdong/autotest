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
        System.out.println("监听器的名称是:" + this.name);
        return this.name;
    }


    @Override
    public void jobToBeExecuted(JobExecutionContext jobExecutionContext) {
        System.out.println("监听Scheduler在JobDetail将要被执行时调用的方法");
    }

    @Override
    public void jobExecutionVetoed(JobExecutionContext jobExecutionContext) {
        System.out.println("监听到Scheduler在JobDetail 即将被执行，但又被TriggerListener否决时会调用该方法");

    }

    @Override
    public void jobWasExecuted(JobExecutionContext jobExecutionContext, JobExecutionException e) {
        System.out.println("监听到Scheduler在JobDetail 被执行之后调用这个方法");

//        JobKey jobKey = jobExecutionContext.getJobDetail().getKey();
//
//        try {
//            jobExecutionContext.getScheduler().triggerJob(jobKey);
//            System.out.println(jobKey.getName());
//        } catch (SchedulerException ex) {
//            ex.printStackTrace();
//        }

//        try {
//            jobExecutionContext.getScheduler().shutdown();  //执行完job之后关闭执行器
//        } catch (SchedulerException ex) {
//            ex.printStackTrace();
//        }


    }
}
