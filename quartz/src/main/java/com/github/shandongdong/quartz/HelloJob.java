package com.github.shandongdong.quartz;

import lombok.Setter;
import org.quartz.*;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * @Project: autotest
 * @Author: ShanDongDong
 * @Time: 2019-08-05 10:48
 * @Email: shandongdong@126.com
 * @Description: 表明该job需要完成什么类型的任务，除此之外，Quartz还需要知道该Job实例所包含的属性；这将由JobDetail类来完成。
 * @DisallowConcurrentExecution：将该注解加到job类上，告诉Quartz不要并发地执行同一个job定义（这里指特定的job类）的多个实例。该限制是针对JobDetail的，而不是job类的。
 * @PersistJobDataAfterExecution：有状态的job，多次调用job的时候保存JobDataMap的数据。将该注解加在job类上，告诉Quartz在成功执行了job类的execute方法后（没有发生任何异常），更新JobDetail中JobDataMap的数据， 使得该job（即JobDetail）在下一次执行的时候，JobDataMap中是更新后的数据，而不是更新前的旧数据 如果你使用了@PersistJobDataAfterExecution注解，
 * 我们强烈建议你同时使用@DisallowConcurrentExecution注解，因为当同一个job（JobDetail）的两个实例被并发执行时，由于竞争，JobDataMap中存储的数据很可能是不确定的。
 **/
@DisallowConcurrentExecution
@PersistJobDataAfterExecution
@Setter
public class HelloJob implements Job {

    private String message;
    private Integer count;

    public HelloJob() {
        System.out.println("每一次调度器执行Job时，在调用execute方法之前都会创建一个新的实例， 调用完成之后关联的job实例对象就会被释放!");
    }

    /**
     * 当Job的一个trigger被触发时，execute（）方法由调度程序的一个工作线程调用
     *
     * @param jobExecutionContext
     * @throws JobExecutionException 访问quartz运行时的环境以及job携带的数据
     */
    @Override
    public void execute(JobExecutionContext jobExecutionContext) throws JobExecutionException {
        Date date = new Date();
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String dateString = simpleDateFormat.format(date);

        System.out.println("====== Job execute start========>" + this.getClass().getSimpleName() + ", time:" + dateString);

        //在job的执行过程中，可以从JobDataMap中取出数据
        JobKey jobKey = jobExecutionContext.getJobDetail().getKey();
        System.out.println("工作任务名称：" + jobKey.getName() + ", 组:" + jobKey.getGroup());
        System.out.println("类名称:" + jobExecutionContext.getJobDetail().getJobClass().getName());

        // 获取Job执行时的数据
        JobDataMap dataMap = jobExecutionContext.getJobDetail().getJobDataMap();
        String jobSays = dataMap.getString("jobSays");
        float myFloatValue = dataMap.getFloat("myFloatValue");
        System.out.println("Job实例: " + jobKey + " of DumbJob says: " + jobSays + ", and val is: " + myFloatValue);

        // 获取Job执行时的trigger数据
        JobDataMap jobDataMap = jobExecutionContext.getTrigger().getJobDataMap();
        System.out.println(jobDataMap.getString("message"));

        // 提供一个message属性和setter方法之后，Quartz框架默认的JobFactory实现类在初始化时会自动调用setter方法
        System.out.println("获取message的值，是trigger还是jobDetail？ " + message);  // 如果相同则则trigger会覆盖jobDetail

        // 其他内容
        System.out.println("当前任务时间：" + simpleDateFormat.format(jobExecutionContext.getFireTime()));
        System.out.println("下一个任务时间：" + simpleDateFormat.format(jobExecutionContext.getNextFireTime()));

        // 实现有状态的job
        ++count;
        System.out.println("count 的值为:" + count);
        jobExecutionContext.getJobDetail().getJobDataMap().put("count", count);     //需要注意的是这里只能用于JobDetail，而不能用于trigger



        System.out.println("====== Job execute end========>" + this.getClass().getSimpleName() + ", time:" + dateString);

    }
}
