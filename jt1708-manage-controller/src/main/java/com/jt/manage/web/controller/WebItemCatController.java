package com.jt.manage.web.controller;

import com.jt.common.vo.ItemCatResult;
import com.jt.manage.web.service.WebItemCatService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class WebItemCatController {

    @Autowired
    private WebItemCatService webItemCatService;

    @RequestMapping("/web/itemcat/all")
    @ResponseBody
    public ItemCatResult queryItemCatList() {
        ItemCatResult itemCatResult = webItemCatService.queryItemCat();
        return itemCatResult;
    }
}
