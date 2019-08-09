package com.github.shandongdong.ssm.service;


import com.github.shandongdong.ssm.beans.User;
import com.github.shandongdong.ssm.mapper.UserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @Project: autotest
 * @Author: ShanDongDong
 * @Time: 2019-08-09 10:04
 * @Email: shandongdong@126.com
 * @Description:
 **/
@Service
public class UserService {

    @Autowired
    UserMapper userMapper;

    public User getUserById(Integer id) {
        User user = userMapper.getUserById(id);
        return user;
    }
}
