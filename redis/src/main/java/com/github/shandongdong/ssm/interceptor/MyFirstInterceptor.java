package com.github.shandongdong.ssm.interceptor;

import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @Project: autotest
 * @Author: ShanDongDong
 * @Time: 2019-06-05 10:40
 * @Email: shandongdong@126.com
 * @Description: 自定义拦截器
 */
public class MyFirstInterceptor implements HandlerInterceptor {

    // 请求处理方法之前执行
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
//        System.out.println(this.getClass().getSimpleName() + " preHandle method invoked!!!");
        return true;
    }

    // 请求处理方法之后，视图处理之前执行
    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {
//        System.out.println(this.getClass().getSimpleName() + " postHandle method invoked!!!");
    }

    // 视图处理之后执行。转发/重定向之后
    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
//        System.out.println(this.getClass().getSimpleName() + " afterCompletion method invoked!!!");
    }
}
