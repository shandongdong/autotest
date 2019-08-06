package com.github.shandongdong.quartz;

import org.quartz.*;

/**
 * @Project: autotest
 * @Author: ShanDongDong
 * @Time: 2019-08-05 10:48
 * @Email: shandongdong@126.com
 * @Description: 表明该job需要完成什么类型的任务，除此之外，Quartz还需要知道该Job实例所包含的属性；这将由JobDetail类来完成。
 * @DisallowConcurrentExecution：将该注解加到job类上，告诉Quartz不要并发地执行同一个job定义（这里指特定的job类）的多个实例。该限制是针对JobDetail的，而不是job类的。
 * @PersistJobDataAfterExecution：将该注解加在job类上，告诉Quartz在成功执行了job类的execute方法后（没有发生任何异常），更新JobDetail中JobDataMap的数据，使得该job（即JobDetail）在下一次执行的时候，JobDataMap中是更新后的数据，而不是更新前的旧数据 如果你使用了@PersistJobDataAfterExecution注解，我们强烈建议你同时使用@DisallowConcurrentExecution注解，因为当同一个job（JobDetail）的两个实例被并发执行时，由于竞争，JobDataMap中存储的数据很可能是不确定的。
 **/
@DisallowConcurrentExecution
@PersistJobDataAfterExecution
public class HelloJob implements Job {

    /**
     * 当Job的一个trigger被触发时，execute（）方法由调度程序的一个工作线程调用
     *
     * @param jobExecutionContext
     * @throws JobExecutionException
     */
    @Override
    public void execute(JobExecutionContext jobExecutionContext) throws JobExecutionException {
        System.out.println("====== Job execute ========>" + this.getClass().getSimpleName() + ", time:" + System.currentTimeMillis());
        //在job的执行过程中，可以从JobDataMap中取出数据
//        JobKey key = jobExecutionContext.getJobDetail().getKey();
//        JobDataMap dataMap = jobExecutionContext.getJobDetail().getJobDataMap();
//        String jobSays = dataMap.getString("jobSays");
//        float myFloatValue = dataMap.getFloat("myFloatValue");
//        System.out.println("Instance " + key + " of DumbJob says: " + jobSays + ", and val is: " + myFloatValue);
    }
}
