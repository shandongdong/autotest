package com.github.shandongdong.quartz.controller;

import com.alibaba.fastjson.JSON;
import com.github.shandongdong.quartz.beans.User;
import com.github.shandongdong.quartz.constant.Constant;
import com.github.shandongdong.quartz.service.UserService;
import com.github.shandongdong.quartz.util.ResponseResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.HashMap;
import java.util.UUID;

/**
 * @Project: autotest
 * @Author: ShanDongDong
 * @Time: 2019-08-09 10:14
 * @Email: shandongdong@126.com
 * @Description:
 **/
@RequestMapping(value = "user")
@Controller
public class UserController {
    @Autowired
    private UserService userService;

    @RequestMapping(value = "/getUserById", method = RequestMethod.GET)
    public String getUserById(@RequestParam("id") int id, HttpServletRequest request, Model model) {
        User user = userService.getUserById(id);
        System.out.println("模拟返回前端一个json:" + JSON.toJSONString(ResponseResult.success(user)));
        HashMap statusCode = new HashMap();
        statusCode.put("10001", user);
        System.out.println("模拟返回前端一个json:" + JSON.toJSONString(ResponseResult.failed("获取用户失败", statusCode)));
        if (user != null) {
            request.setAttribute("user", user);
            model.addAttribute("user", user);
            return "success";
        }
        request.setAttribute("error", "没有找到该用户！");
        return "error";
    }

    @RequestMapping(value = "/toLogin", method = RequestMethod.GET)
    public String toLogin(HttpServletRequest request, HttpServletResponse response) throws IOException {
        //若用户登录跳转到JobList页面。这里可以抽象出来一个公共的方法！
        String toke = (String) request.getSession().getAttribute("toke");
        if (!StringUtils.isEmpty(toke)) {
            response.sendRedirect(request.getContextPath() + "/quartz/listJob");
        }
        return "user/login";

    }

    @RequestMapping(value = "/login", method = RequestMethod.POST)
    public String login(@RequestParam("username") String username, @RequestParam("password") String password,
                        HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        User u = new User();
        u.setUserName(username);
        u.setPassWord(password);
        User user = userService.getUser(u);
        if (user != null) {
            //登录成功
            HttpSession session = request.getSession();
            session.setAttribute(Constant.LONGIN_TOKE, UUID.randomUUID().toString().replace("-", ""));
            session.setAttribute("name", username);
            response.sendRedirect(request.getContextPath() + "/quartz/listJob");
        } else {
            //跳转回登录页面
            request.setAttribute("message", "用户名或者密码错误！");
        }

        return "user/login";
    }

    @RequestMapping(value = "logout")
    public String logout(HttpServletRequest request) {
        HttpSession session = request.getSession();
        session.removeAttribute(Constant.LONGIN_TOKE);
        return "redirect:/user/toLogin";
    }

    @RequestMapping(value = "jumpLogin")
    public String login() {
        return "user/login";
    }
}
