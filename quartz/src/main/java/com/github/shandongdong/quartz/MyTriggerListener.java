package com.github.shandongdong.quartz;

import org.quartz.JobExecutionContext;
import org.quartz.Trigger;
import org.quartz.TriggerListener;

/**
 * @Project: autotest
 * @Author: ShanDongDong
 * @Time: 2019-08-08 14:07
 * @Email: shandongdong@126.com
 * @Description:
 **/
public class MyTriggerListener implements TriggerListener {

    private String name;

    public MyTriggerListener(String name) {
        if (name == null) {
            throw new IllegalArgumentException("Listener name cannot be null!");
        } else {
            this.name = name;
        }
        System.out.println(name + " 监听器被实例化");
    }

    @Override
    public String getName() {
        System.out.println("监听器的名称是:" + this.name);
        return this.name;
    }

    @Override
    public void triggerFired(Trigger trigger, JobExecutionContext jobExecutionContext) {
        String name = trigger.getKey().getName();
        System.out.println(name + "被触发");
    }

    /**
     * 在 Trigger 触发后，Job 将要被执行时由 Scheduler 调用这个方法。
     * TriggerListener 给了一个选择去否决 Job 的执行。假如这个方法返回 true，这个 Job 将不会为此次 Trigger 触发而得到执行。
     */
    @Override
    public boolean vetoJobExecution(Trigger trigger, JobExecutionContext jobExecutionContext) {
        String name = trigger.getKey().getName();
        System.out.println(name + "没有被触发");
        return false;    // 不会执行job方法
    }

    @Override
    public void triggerMisfired(Trigger trigger) {
        String name = trigger.getKey().getName();
        System.out.println(name + "错过触发");
    }

    @Override
    public void triggerComplete(Trigger trigger, JobExecutionContext jobExecutionContext, Trigger.CompletedExecutionInstruction completedExecutionInstruction) {
        String triggerName = trigger.getKey().getName();
        System.out.println(triggerName + " 完成之后触发");
        System.out.println("\n");
    }
}
