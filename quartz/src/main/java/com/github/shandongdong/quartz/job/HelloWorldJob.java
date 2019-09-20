package com.github.shandongdong.quartz.job;

import lombok.Setter;
import org.quartz.*;

import java.util.Date;

@Setter
public class HelloWorldJob implements Job {

    private String testCaseContent = null;

    public HelloWorldJob() {
//        System.out.println(System.currentTimeMillis() + " 每次都会实例化Job类 " + this.getClass().getName());
    }

    /**
     * "0/10 * * * * ?
     */
    @Override
    public void execute(JobExecutionContext jobExecutionContext) throws JobExecutionException {
        // 这里可以通过Job携带的参数JobMap参数将测试用例传递过来执行。

        JobKey jobKey = jobExecutionContext.getJobDetail().getKey();
        System.out.println(new Date() + " ======================>execute job:" + jobKey.getName() + ", group:" + jobKey.getGroup() + ", Thread:" + Thread.currentThread().getId());


        // 提供一个 testCaseContent 属性和setter方法之后，Quartz框架默认的JobFactory实现类在初始化时会自动调用setter方法

        // 获取添加Job时的数据,将测试用例数据携带过来
//        JobDataMap jobDataMap = jobExecutionContext.getJobDetail().getJobDataMap();
        JobDataMap jobDataMap = jobExecutionContext.getTrigger().getJobDataMap();
        String data = jobDataMap.getString(jobKey.getName());
        System.out.println("创建Job时携带了数据：" + jobDataMap + ", data=" + data);
        try {
//            System.out.println("睡眠5秒，让job错误触发时间");
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

}
