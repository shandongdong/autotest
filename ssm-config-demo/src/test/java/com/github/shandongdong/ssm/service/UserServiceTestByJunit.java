package com.github.shandongdong.ssm.service;

import com.github.shandongdong.ssm.beans.User;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

/**
 * @Project: autotest
 * @Author: ShanDongDong
 * @Time: 2019-08-09 17:45
 * @Email: shandongdong@126.com
 * @Description: 配置spring和junit整合，junit启动时加载springIOC容器 spring-test,junit
 **/
@RunWith(SpringJUnit4ClassRunner.class)
// 告诉junit spring配置文件
@ContextConfiguration(locations = {"classpath:springApplicationContext.xml"})
public class UserServiceTestByJunit {
    @Autowired
    public UserService userService;

    @Test
    public void getUserByIdTest() {
        User user = userService.getUserById(1);
        System.out.println(user.getUserName());
    }
}
