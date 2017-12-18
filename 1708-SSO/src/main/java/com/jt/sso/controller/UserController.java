package com.jt.sso.controller;

import com.jt.common.vo.SysResult;
import com.jt.sso.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping("/user")
public class UserController {
    @Autowired
    private UserService userService;

    @RequestMapping("/check/{param}/{type}")
    @ResponseBody
    public SysResult check(@PathVariable String param,@PathVariable Integer type) {
        try {
            boolean flag = userService.check(param, type);
            return SysResult.oK(flag);
        } catch (Exception e) {
            e.printStackTrace();
            return SysResult.build(201, "失败");
        }
    }
}
