package com.github.shandongdong.quartz.job;

import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

import java.util.Date;

public class HelloWorldJob implements Job {

    public HelloWorldJob() {
        System.out.println(System.currentTimeMillis() + " 正在实例化Job类 " + this.getClass().getName());
    }

    /**
     * "0/10 * * * * ?
     */
    @Override
    public void execute(JobExecutionContext arg0) throws JobExecutionException {
        System.out.println("======================>execute job hello world " + new Date());
    }

}
