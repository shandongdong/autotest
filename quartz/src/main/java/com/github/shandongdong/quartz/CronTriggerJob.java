package com.github.shandongdong.quartz;

import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * @Project: autotest
 * @Author: ShanDongDong
 * @Time: 2019-08-05 10:48
 * @Email: shandongdong@126.com
 * @Description:
 **/

public class CronTriggerJob implements Job {

    public CronTriggerJob() {
        System.out.println("每一次调度器执行Job时，在调用execute方法之前都会创建一个新的实例， 调用完成之后关联的job实例对象就会被释放!");
    }

    @Override
    public void execute(JobExecutionContext jobExecutionContext) {
        try {
            Thread.sleep(5000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        Date date = new Date();
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String dateString = simpleDateFormat.format(date);

        System.out.println("====== Job execute start========>" + this.getClass().getSimpleName() + ", time:" + dateString);

        System.out.println(jobExecutionContext.getTrigger().getJobKey().getName());


        System.out.println("任务的开始时间:" + simpleDateFormat.format(jobExecutionContext.getTrigger().getStartTime()));
        System.out.println("任务的结束时间:" + simpleDateFormat.format(jobExecutionContext.getTrigger().getEndTime()));
        System.out.println("====== Job execute end========>" + this.getClass().getSimpleName() + ", time:" + dateString);

    }
}
