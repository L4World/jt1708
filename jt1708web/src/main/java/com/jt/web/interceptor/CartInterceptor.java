package com.jt.web.interceptor;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.jt.common.service.HttpClientService;
import com.jt.common.util.CookieUtils;
import com.jt.web.pojo.User;
import com.jt.web.threadlocal.UserThreadLocal;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 拦截器,继承接口类,拦截符合扫描规则的controller请求
 */
public class CartInterceptor implements HandlerInterceptor {
    @Autowired
    private HttpClientService httpClientService;
    private static final ObjectMapper MAPPER = new ObjectMapper();
    /**
     * 前拦截操作,转发到真正的controller之前的操作
     * 返回值是布尔类型
     * 如果返回true,拦截器放行
     * 返回false,不放行;不放行的时候,刷新页面,重定向,转发
     */
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        /**
         * 步骤
         * 1. 从cookie中获取 ticket
         * 2. 去访问sso系统获取redis的user的json
         * 3. json转化成对象user
         * 4. userId的共享数据:共享涉及到线程安全问题
         * 5. 使用UserThreadLocal 自定义的ThreadLocal的子类,专门控制 user对象的线程安全
         */
        String cookieName = "JT_TICKET";
        String ticket = CookieUtils.getCookieValue(request, cookieName);
        if (StringUtils.isNotEmpty(ticket)) {
            String url = "http://sso.jt.com/user/query/" + ticket;
            String jsonData = httpClientService.doGet(url, "utf-8");
            JsonNode userNode = MAPPER.readTree(jsonData).get("data");
            User curUser = MAPPER.readValue(userNode.traverse(), User.class);
            UserThreadLocal.set(curUser);
            return true;
        }
        //没有登录信息的时候
        UserThreadLocal.set(null);
        //转发到登录页面
        response.sendRedirect("/user/login.html");
        return false;
    }

    /**
     * 后拦截操作,controller已经返回数据之后的拦截操作
     */
    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {

    }

    /**
     * 完成后拦截,
     * 比如可以对model里面的所有key做拦截,修改
     */
    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {

    }
}
