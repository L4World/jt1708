package com.jt.web.controller;

import com.jt.common.util.CookieUtils;
import com.jt.common.vo.SysResult;
import com.jt.web.pojo.User;
import com.jt.web.service.UserService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Controller
@RequestMapping("user/")
public class UserController {
    @Autowired
    private UserService userService;

    @RequestMapping("/login")
    public String loginIndex() {
        return "login";
    }

    @RequestMapping("/register")
    public String registerIndex() {
        return "register";
    }


    @RequestMapping("/doRegister")
    @ResponseBody
    public SysResult doRegister(User user) {
        String username = null;
        try {
            username = userService.doRegister(user);
            if (StringUtils.isNotEmpty(username)) {
                return SysResult.oK(username);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return SysResult.build(201, "注册失败", username);
    }

    //转向登录页面
    @RequestMapping("/doLogin")
    @ResponseBody
    public SysResult toLogin(User user, HttpServletRequest request, HttpServletResponse response) {
        String ticket = null;
        try {
            ticket = userService.doLogin(user);
            if (StringUtils.isNotEmpty(ticket)){
                //如果登录成功,封装ticket到cookie中
                String cookieName = "JT_TICKET";
                CookieUtils.setCookie(request, response, cookieName, ticket);
                return SysResult.oK(ticket);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return SysResult.build(201, "登录失败");
    }
    //登出操作
    @RequestMapping("logout")
    public String loginout(HttpServletRequest request,HttpServletResponse response) {
        //删除cookie
        String cookieName = "JT_TICKET";
        CookieUtils.deleteCookie(request, response, cookieName);
        //还需要删除UserThreadLocal
        //TODO--以后学到再加
        //缓存可以清除redies , 除了删除和过期 , redis 还会LUR计算最近最久未使用的,进行删除
        return "index";
    }

}
