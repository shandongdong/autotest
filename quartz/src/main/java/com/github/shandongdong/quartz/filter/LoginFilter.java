package com.github.shandongdong.quartz.filter;

import com.github.shandongdong.quartz.constant.Constant;
import lombok.extern.slf4j.Slf4j;
import org.springframework.util.StringUtils;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

/**
 * @Project: autotest
 * @Author: ShanDongDong
 * @Time: 2019-08-09 17:45
 * @Email: shandongdong@126.com
 * @Description: Servlet过滤器本身并不生成请求和响应对象， 它只提供过滤作用。Servlet过滤器能够在Servlet被调用之前检查Request对象，Servlet过滤器能够在Servlet被调用之后检查Response对象。
 **/
@Slf4j
public class LoginFilter implements Filter {

    @Override
    public void destroy() {
        log.info("destroy LoginFilter ...");
    }

    @Override
    public void doFilter(ServletRequest req, ServletResponse resp, FilterChain chain) throws IOException, ServletException {
        HttpServletRequest request = (HttpServletRequest) req;
        HttpServletResponse response = (HttpServletResponse) resp;
        HttpSession session = request.getSession();
        //判断Session中是否有登录用户信息
        String toke = (String) session.getAttribute(Constant.LONGIN_TOKE);
        if (!StringUtils.isEmpty(toke)) {
            chain.doFilter(req, resp);
        } else {
            //若没有则，跳转到登录页面
            response.sendRedirect(request.getContextPath() + "/user/toLogin");
        }
    }

    @Override
    public void init(FilterConfig arg0) throws ServletException {
        log.info("init LoginFilter ...");
    }

}
