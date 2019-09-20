package com.github.shandongdong.ssm.beans;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * @Project: autotest
 * @Author: ShanDongDong
 * @Time: 2019-08-09 10:03
 * @Email: shandongdong@126.com
 * @Description:
 **/
@Getter
@Setter
@ToString
public class User {
    private Integer userId;
    private String userName;
    private String passWord;
    private Integer age;
}
