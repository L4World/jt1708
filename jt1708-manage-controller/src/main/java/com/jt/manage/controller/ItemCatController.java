package com.jt.manage.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import com.jt.manage.pojo.ItemCat;
import com.jt.manage.service.ItemCatService;

import java.util.List;

@Controller
@RequestMapping("/item/cat")
public class ItemCatController {

    @Autowired
    private ItemCatService itemCatService;

    @RequestMapping("/queryall")
    @ResponseBody   //springmvc会将返回的对象利用 jackson json 转换成json字符串
    public List<ItemCat> queryAll() {
        List<ItemCat> itemCatList = itemCatService.queryAll();
        return itemCatList;
    }

    @RequestMapping("/list")
    @ResponseBody
    public List<ItemCat> queryItemCatList(@RequestParam(defaultValue = "0")Integer id) {
        List<ItemCat> itemCatList = itemCatService.queryItemCatList(id);
        return itemCatList;
    }
}
