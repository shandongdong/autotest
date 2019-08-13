package com.github.shandongdong.quartz.service;

import org.quartz.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class QuartzService {

    @Autowired
    private Scheduler quartzScheduler;

    /**
     * addJob(方法描述：添加一个定时任务) <br />
     * (方法适用条件描述： – 可选)
     *
     * @param jobName          作业名称
     * @param jobGroupName     作业组名称
     * @param triggerName      触发器名称
     * @param triggerGroupName 触发器组名称
     * @param cls              定时任务的class
     * @param cron             时间表达式 void
     * @throws
     * @since 1.0.0
     */
    public void addJob(String jobName, String jobGroupName, String triggerName,
                       String triggerGroupName, Class cls, String cron) {
        try {
            // 获取调度器
            Scheduler sched = quartzScheduler;
            // 创建一项作业
            JobDetail job = JobBuilder.newJob(cls)
                    .withIdentity(jobName, jobGroupName).build();
            // 创建一个触发器
            CronTrigger trigger = TriggerBuilder.newTrigger()
                    .withIdentity(triggerName, triggerGroupName)
                    .withSchedule(CronScheduleBuilder.cronSchedule(cron))
                    .build();
            // 告诉调度器使用该触发器来安排作业
            sched.scheduleJob(job, trigger);
            // 启动
            if (!sched.isShutdown()) {
                sched.start();
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * 修改定时器任务信息
     *
     * @param oldjobName      原job name
     * @param oldjobGroup     原job group
     * @param oldtriggerName  原 trigger name
     * @param oldtriggerGroup 原 trigger group
     * @param jobName
     * @param jobGroup
     * @param triggerName
     * @param triggerGroup
     * @param cron
     */
    public boolean modifyJobTime(String oldjobName, String oldjobGroup, String oldtriggerName, String oldtriggerGroup, String jobName, String jobGroup,
                                 String triggerName, String triggerGroup, String cron) {
        try {
            Scheduler sched = quartzScheduler;
            CronTrigger trigger = (CronTrigger) sched.getTrigger(TriggerKey
                    .triggerKey(oldtriggerName, oldtriggerGroup));
            if (trigger == null) {
                return false;
            }

            JobKey jobKey = JobKey.jobKey(oldjobName, oldjobGroup);
            TriggerKey triggerKey = TriggerKey.triggerKey(oldtriggerName,
                    oldtriggerGroup);

            JobDetail job = sched.getJobDetail(jobKey);
            Class jobClass = job.getJobClass();
            // 停止触发器
            sched.pauseTrigger(triggerKey);
            // 移除触发器
            sched.unscheduleJob(triggerKey);
            // 删除任务
            sched.deleteJob(jobKey);

            addJob(jobName, jobGroup, triggerName, triggerGroup, jobClass,
                    cron);

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
     * @param cron             cron表达式
     */
    public void modifyJobTime(String triggerName, String triggerGroupName,
                              String cron) {
        try {
            Scheduler sched = quartzScheduler;
            CronTrigger trigger = (CronTrigger) sched.getTrigger(TriggerKey
                    .triggerKey(triggerName, triggerGroupName));
            if (trigger == null) {
                return;
            }
            String oldTime = trigger.getCronExpression();
            if (!oldTime.equalsIgnoreCase(cron)) {
                CronTrigger ct = (CronTrigger) trigger;
                // 修改时间
                ct.getTriggerBuilder()
                        .withSchedule(CronScheduleBuilder.cronSchedule(cron))
                        .build();
                // 重启触发器
                sched.resumeTrigger(TriggerKey.triggerKey(triggerName,
                        triggerGroupName));
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
    public void removeJob(String jobName, String jobGroupName,
                          String triggerName, String triggerGroupName) {
        try {
            Scheduler sched = quartzScheduler;
            // 停止触发器
            sched.pauseTrigger(TriggerKey.triggerKey(triggerName,
                    triggerGroupName));
            // 移除触发器
            sched.unscheduleJob(TriggerKey.triggerKey(triggerName,
                    triggerGroupName));
            // 删除任务
            sched.deleteJob(JobKey.jobKey(jobName, jobGroupName));
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * 开始所有定时任务。启动调度器
     */
    public void startSchedule() {
        try {
            Scheduler sched = quartzScheduler;
            sched.start();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * 关闭调度器
     */
    public void shutdownSchedule() {
        try {
            Scheduler sched = quartzScheduler;
            if (!sched.isShutdown()) {
                sched.shutdown();
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
            quartzScheduler.pauseJob(JobKey.jobKey(jobName, jobGroupName));
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
            quartzScheduler.resumeJob(JobKey.jobKey(jobName, jobGroupName));
        } catch (SchedulerException e) {
            e.printStackTrace();
        }
    }


}
