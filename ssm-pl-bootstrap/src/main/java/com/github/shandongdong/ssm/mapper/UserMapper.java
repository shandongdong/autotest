package com.github.shandongdong.ssm.mapper;


import com.github.shandongdong.ssm.beans.User;

/**
 * @Project: autotest
 * @Author: ShanDongDong
 * @Time: 2019-08-09 10:03
 * @Email: shandongdong@126.com
 * @Description:
 **/
public interface UserMapper {
    User getUserById(Integer userId);
}
