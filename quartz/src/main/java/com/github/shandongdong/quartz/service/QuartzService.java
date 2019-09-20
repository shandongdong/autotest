package com.github.shandongdong.quartz.service;

import org.quartz.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
public class QuartzService {

    @Autowired
    private Scheduler scheduler;

    /**
     * 添加一个定时任务, 按照时间表达规则循环执行
     *
     * @param jobName          Job名称
     * @param jobGroupName     JOb组名称
     * @param triggerName      触发器名称
     * @param triggerGroupName 触发器组名称
     * @param clazz            定时任务的class
     * @param cronExpression   时间表达式
     * @param jobExtraDataMap  创建job时携带的参数，无的话传null即可
     */
    public void addTimeJob(String jobName, String jobGroupName, String triggerName, String triggerGroupName,
                           String clazz, String cronExpression, JobDataMap jobExtraDataMap) {
        try {
            Class cls = Class.forName(clazz);
            // 创建一项作业
            JobDetail job = JobBuilder.newJob(cls).withIdentity(jobName, jobGroupName).build();
            // 创建一个触发器
            TriggerBuilder<CronTrigger> triggerTriggerBuilder = TriggerBuilder.newTrigger()
                    .withIdentity(triggerName, triggerGroupName)
                    .withSchedule(CronScheduleBuilder.cronSchedule(cronExpression));
            if (null != jobExtraDataMap) {
                triggerTriggerBuilder.usingJobData(jobExtraDataMap);
            }
            CronTrigger trigger = triggerTriggerBuilder.build();
            // 告诉调度器使用该触发器来安排作业
            scheduler.scheduleJob(job, trigger);
            // 启动
            if (!scheduler.isShutdown()) {
                scheduler.start();
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * 添加一个执行任务, 立即触发来执行，用于手动执行任务
     */
    public void addRunOnceJob(String jobName, String jobGroupName, String triggerName, String triggerGroupName,
                              String clazz, JobDataMap jobExtraDataMap) {
        try {
            Class cls = Class.forName(clazz);
            // 创建一项作业
            JobDetail job = JobBuilder.newJob(cls).withIdentity(jobName, jobGroupName).build();

            // 触发时间点
            SimpleScheduleBuilder simpleScheduleBuilder = SimpleScheduleBuilder.simpleSchedule().withRepeatCount(0);

            TriggerBuilder<SimpleTrigger> simpleTriggerTriggerBuilder = TriggerBuilder.newTrigger().withIdentity(triggerName, triggerGroupName).withSchedule(simpleScheduleBuilder).startAt(new Date());
            if (null != jobExtraDataMap) {
                simpleTriggerTriggerBuilder.usingJobData(jobExtraDataMap);
            }
            Trigger trigger = simpleTriggerTriggerBuilder.build();
            scheduler.scheduleJob(job, trigger);
            // 启动
            if (!scheduler.isShutdown()) {
                scheduler.start();
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }


    /**
     * 修改定时器任务信息
     *
     * @param oldJobName      原job name
     * @param oldJobGroup     原job group
     * @param oldTriggerName  原trigger name
     * @param oldTriggerGroup 原trigger group
     * @see #addTimeJob(String, String, String, String, String, String, JobDataMap)
     */
    public boolean modifyJobTime(String oldJobName, String oldJobGroup, String oldTriggerName, String oldTriggerGroup,
                                 String newJobName, String newJobGroup, String newTriggerName, String newTriggerGroup,
                                 String newCronExpression, JobDataMap newJobExtraDataMap) {
        try {
            CronTrigger trigger = (CronTrigger) scheduler.getTrigger(TriggerKey.triggerKey(oldTriggerName, oldTriggerGroup));
            if (trigger == null) {
                return false;
            }
            JobKey jobKey = JobKey.jobKey(oldJobName, oldJobGroup);
            TriggerKey triggerKey = TriggerKey.triggerKey(oldTriggerName, oldTriggerGroup);

            JobDetail job = scheduler.getJobDetail(jobKey);
            String jobClass = job.getJobClass().getName();
            // 停止触发器
            scheduler.pauseTrigger(triggerKey);
            // 移除触发器
            scheduler.unscheduleJob(triggerKey);
            // 删除任务
            scheduler.deleteJob(jobKey);
            // 添加任务
            addTimeJob(newJobName, newJobGroup, newTriggerName, newTriggerGroup, jobClass, newCronExpression, newJobExtraDataMap);

            return true;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

    }

    /**
     * 修改触发器调度时间
     *
     * @param triggerName      触发器名称
     * @param triggerGroupName 触发器组名称
     * @param cronExpression   表达式
     */
    public void modifyJobTime(String triggerName, String triggerGroupName, String cronExpression) {
        try {
            CronTrigger trigger = (CronTrigger) scheduler.getTrigger(TriggerKey.triggerKey(triggerName, triggerGroupName));
            if (trigger == null) {
                return;
            }
            String oldTime = trigger.getCronExpression();
            if (!oldTime.equalsIgnoreCase(cronExpression)) {
                CronTrigger ct = (CronTrigger) trigger;
                // 修改时间
                ct.getTriggerBuilder()
                        .withSchedule(CronScheduleBuilder.cronSchedule(cronExpression))
                        .build();
                // 重启触发器
                scheduler.resumeTrigger(TriggerKey.triggerKey(triggerName, triggerGroupName));
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * 删除指定组任务
     *
     * @param jobName          作业名称
     * @param jobGroupName     作业组名称
     * @param triggerName      触发器名称
     * @param triggerGroupName 触发器组名称
     */
    public void removeJob(String jobName, String jobGroupName, String triggerName, String triggerGroupName) {
        try {
            // 停止触发器
            scheduler.pauseTrigger(TriggerKey.triggerKey(triggerName, triggerGroupName));
            // 移除触发器
            scheduler.unscheduleJob(TriggerKey.triggerKey(triggerName, triggerGroupName));
            // 删除任务
            scheduler.deleteJob(JobKey.jobKey(jobName, jobGroupName));
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * 开始所有定时任务。启动调度器
     */
    public void startSchedule() {
        try {
            scheduler.start();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * 关闭调度器
     */
    public void shutdownSchedule() {
        try {
            if (!scheduler.isShutdown()) {
                scheduler.shutdown();
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * 暂停指定的任务
     *
     * @param jobName      任务名称
     * @param jobGroupName 任务组名称
     * @return
     */
    public void pauseJob(String jobName, String jobGroupName) {
        try {
            scheduler.pauseJob(JobKey.jobKey(jobName, jobGroupName));
        } catch (SchedulerException e) {
            e.printStackTrace();
        }
    }

    /**
     * 恢复指定的任务
     *
     * @param jobName      任务名称
     * @param jobGroupName 任务组名称
     * @return
     */
    public void resumeJob(String jobName, String jobGroupName) {
        try {
            scheduler.resumeJob(JobKey.jobKey(jobName, jobGroupName));
        } catch (SchedulerException e) {
            e.printStackTrace();
        }
    }

}
