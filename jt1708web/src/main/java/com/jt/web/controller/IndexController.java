package com.jt.web.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class IndexController {

    @RequestMapping("index.html")
    public String toIndex() {
        //转向前台首页
        return "index";
    }

    @RequestMapping("/")
    public String index() {
        return "index";
    }
}
