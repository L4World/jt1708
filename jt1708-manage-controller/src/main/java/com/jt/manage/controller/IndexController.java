package com.jt.manage.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class IndexController {
    @RequestMapping("/")
    public String toHome() {
        return "index";
    }


    @RequestMapping("/page/{pageName}")
    public String goHome(@PathVariable String pageName) {
        return pageName;
    }
}
