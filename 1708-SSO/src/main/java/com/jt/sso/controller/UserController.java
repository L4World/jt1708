package com.jt.sso.controller;

import com.jt.common.vo.SysResult;
import com.jt.sso.pojo.User;
import com.jt.sso.service.UserService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping("/user")
public class UserController {
    @Autowired
    private UserService userService;

    @RequestMapping("/check/{param}/{type}")
    @ResponseBody
    public SysResult check(@PathVariable String param, @PathVariable Integer type) {
        try {
            boolean flag = userService.check(param, type);
            if (flag) {
                return SysResult.oK(flag);
            }
            return SysResult.build(201, "失败");
        } catch (Exception e) {
            e.printStackTrace();
            return SysResult.build(201, "失败");
        }
    }

    @RequestMapping("register")
    @ResponseBody
    public SysResult register(User user) {
        try {
            String username = userService.register(user);
            return SysResult.oK(username);
        } catch (Exception e) {
            e.printStackTrace();
            return SysResult.build(201, "注册失败",user.getUsername());
        }
    }

    //用户登录
    @RequestMapping("login")
    @ResponseBody
    public SysResult login(String u,String p) {
        //获取业务层返回结果
        String ticket = userService.login(u, p);
        if (StringUtils.isNotEmpty(ticket)) {
            return SysResult.oK(ticket);
        }else{
            return SysResult.build(201, "登录失败");
        }
    }
    //查询用户信息
    @RequestMapping("query/{ticket}")
    @ResponseBody
    public SysResult queryByTicket(@PathVariable String ticket) {
        String userJson = userService.queryByTicket(ticket);
        if (StringUtils.isNotEmpty(userJson)) {
            return SysResult.oK(userJson);
        }else{
            return SysResult.build(201, "未登录");
        }
    }
}
