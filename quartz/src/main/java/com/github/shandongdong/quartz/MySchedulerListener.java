package com.github.shandongdong.quartz;

import org.quartz.*;

/**
 * @Project: autotest
 * @Author: ShanDongDong
 * @Time: 2019-08-08 14:07
 * @Email: shandongdong@126.com
 * @Description:
 **/
public class MySchedulerListener implements SchedulerListener {

    // jobScheduled方法：用于部署JobDetail时调用
    @Override
    public void jobScheduled(Trigger trigger) {
        String jobName = trigger.getJobKey().getName();
        System.out.println(jobName + " 完成部署");
    }

    // jobUnscheduled方法：用于卸载JobDetail时调用
    @Override
    public void jobUnscheduled(TriggerKey triggerKey) {
        System.out.println(triggerKey + " 完成卸载");
    }

    // triggerFinalized方法：当一个 Trigger 来到了再也不会触发的状态时调用这个方法。除非这个 Job 已设置成了持久性，否则它就会从 Scheduler 中移除。
    @Override
    public void triggerFinalized(Trigger trigger) {
        System.out.println("触发器被移除 " + trigger.getJobKey().getName());
    }

    //  triggersPaused方法：Scheduler 调用这个方法是发生在一个 Trigger 或 Trigger 组被暂停时。假如是 Trigger 组的话，triggerName 参数将为 null。
    @Override
    public void triggerPaused(TriggerKey triggerKey) {
        System.out.println(triggerKey + " 正在被暂停");
    }

    @Override
    public void triggersPaused(String s) {
        System.out.println("触发器组 " + s + " 正在被暂停");

    }

    @Override
    public void triggerResumed(TriggerKey triggerKey) {
        System.out.println(triggerKey + " 正在从暂停中恢复");
    }

    // triggersResumed方法：Scheduler 调用这个方法是发生成一个 Trigger 或 Trigger 组从暂停中恢复时。假如是 Trigger 组的话，假如是 Trigger 组的话，triggerName 参数将为 null。参数将为 null。
    @Override
    public void triggersResumed(String s) {
        System.out.println("触发器组 " + s + " 正在从暂停中恢复");

    }

    @Override
    public void jobAdded(JobDetail jobDetail) {
        System.out.println(jobDetail.getKey() + " 添加工作任务");
    }

    @Override
    public void jobDeleted(JobKey jobKey) {
        System.out.println(jobKey + " 删除工作任务");

    }

    @Override
    public void jobPaused(JobKey jobKey) {
        System.out.println(jobKey + " 工作任务正在被暂停");
    }

    // jobsPaused方法：当一个或一组 JobDetail 暂停时调用这个方法。
    @Override
    public void jobsPaused(String s) {
        System.out.println("工作任务组 " + s + " 正在被暂停");

    }

    @Override
    public void jobResumed(JobKey jobKey) {
        System.out.println(jobKey + " 正在从暂停中恢复");
    }

    // jobsResumed方法：当一个或一组 Job 从暂停上恢复时调用这个方法。假如是一个 Job 组，jobName 参数将为 null。
    @Override
    public void jobsResumed(String s) {
        System.out.println("工作任务组 " + s + " 正在从暂停中恢复");
    }

    // schedulerError方法：在 Scheduler 的正常运行期间产生一个严重错误时调用这个方法。
    @Override
    public void schedulerError(String s, SchedulerException e) {
        System.out.println("产生严重错误时调用：   " + s + "  " + e.getUnderlyingException());
    }

    //schedulerInStandbyMode方法： 当Scheduler处于StandBy模式时，调用该方法
    @Override
    public void schedulerInStandbyMode() {
        System.out.println("调度器在挂起模式下调用");
    }

    // schedulerStarted方法：当Scheduler 开启时，调用该方法
    @Override
    public void schedulerStarted() {
        System.out.println("调度器 开启时调用");
    }

    @Override
    public void schedulerStarting() {
        System.out.println("调度器 正在开启时调用");
    }

    // schedulerShutdown方法：当Scheduler停止时，调用该方法
    @Override
    public void schedulerShutdown() {
        System.out.println("调度器 已经被关闭 时调用");
    }

    @Override
    public void schedulerShuttingdown() {
        System.out.println("调度器 正在被关闭 时调用");
    }

    // schedulingDataCleared方法：当Scheduler中的数据被清除时，调用该方法。
    @Override
    public void schedulingDataCleared() {
        System.out.println("调度器的数据被清除时调用");
    }
}
