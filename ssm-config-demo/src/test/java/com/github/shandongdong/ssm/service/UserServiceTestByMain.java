package com.github.shandongdong.ssm.service;

import com.github.shandongdong.ssm.beans.User;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * @Project: autotest
 * @Author: ShanDongDong
 * @Time: 2019-08-12 09:23
 * @Email: shandongdong@126.com
 * @Description: 通过main方法进行测试
 **/
public class UserServiceTestByMain {
    // 方式二：使用main方法进行测试
    public static void main(String[] args) {
        ApplicationContext application = new ClassPathXmlApplicationContext("springApplicationContext.xml");
        UserService uService = (UserService) application.getBean("userService", UserService.class);
        User user = uService.getUserById(1);
        System.out.println(user.getUserName());
    }
}
