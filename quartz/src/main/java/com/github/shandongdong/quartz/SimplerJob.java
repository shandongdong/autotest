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
 * @Description:
 **/
@Setter
public class SimplerJob implements Job {

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

        System.out.println("====== 正在执行Job任务................========>" + this.getClass().getSimpleName() + ", time:" + dateString);

    }
}
