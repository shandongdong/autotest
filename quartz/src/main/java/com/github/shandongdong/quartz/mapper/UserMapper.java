package com.github.shandongdong.quartz.mapper;

import com.github.shandongdong.quartz.beans.User;

/**
 * @Project: autotest
 * @Author: ShanDongDong
 * @Time: 2019-08-09 10:03
 * @Email: shandongdong@126.com
 * @Description:
 **/
public interface UserMapper {
    User getUserById(Integer integer);
    User findUser(User user);
}
