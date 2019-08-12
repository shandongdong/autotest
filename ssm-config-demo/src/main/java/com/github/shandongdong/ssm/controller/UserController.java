package com.github.shandongdong.ssm.controller;

import com.github.shandongdong.ssm.beans.User;
import com.github.shandongdong.ssm.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;

/**
 * @Project: autotest
 * @Author: ShanDongDong
 * @Time: 2019-08-09 10:14
 * @Email: shandongdong@126.com
 * @Description:
 **/
@RequestMapping(value = "/user")
@Controller
public class UserController {
    @Autowired
    private UserService userService;

    @RequestMapping(value = "/getUserById", method = RequestMethod.GET)
    public String getUserById(@RequestParam("id") int id, HttpServletRequest request) {
        User user = userService.getUserById(id);
//        System.out.println("模拟返回前端一个json:" + JSON.toJSONString(ResponseResult.success(user)));
        HashMap statusCode = new HashMap();
        statusCode.put("10001", user);
//        System.out.println("模拟返回前端一个json:" + JSON.toJSONString(ResponseResult.failed("获取用户失败", statusCode)));
        if (user != null) {
            request.setAttribute("user", user);
            return "success";
        }
        request.setAttribute("error", "没有找到该用户！");
        return "error";
    }
}
