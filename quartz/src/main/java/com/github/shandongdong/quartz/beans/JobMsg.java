package com.github.shandongdong.quartz.beans;

import lombok.Data;
/**
 * @Project: autotest
 * @Author: ShanDongDong
 * @Time: 2019-08-13 10:03
 * @Email: shandongdong@126.com
 * @Description:
 **/
@Data
public class JobMsg {
    private int id;
    private String msg;
    private String trg;
    private String mark;
}
