package com.github.shandongdong.ssm.redis;

import com.github.shandongdong.ssm.beans.User;
import redis.clients.jedis.Jedis;

import java.util.HashMap;
import java.util.Map;

/**
 * @Project: autotest
 * @Author: ShanDongDong
 * @Time: 2019-08-29 16:15
 * @Email: shandongdong@126.com
 * @Description: Redis使用
 **/
public class Jedis01 {
    public static void main(String[] args) {
        Jedis jedis = new Jedis("192.168.163.42", 6379);
        System.out.println(jedis.ping());
        User user = new User();
        user.setUserName("Mila");
        user.setPassWord("666");
        user.setAge(3);
        Map<String, String> map = new HashMap<>();
        map.put("hello", "wolrd");
        map.put("come", "on");
        map.put("user", "com.github.shandongdong.ssm.beans.User");
        Object result = jedis.hset("user", map);
        System.out.println(result);
    }
}

