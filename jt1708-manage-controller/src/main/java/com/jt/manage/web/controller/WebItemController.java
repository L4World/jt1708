package com.jt.manage.web.controller;

import com.jt.manage.pojo.Item;
import com.jt.manage.pojo.ItemDesc;
import com.jt.manage.service.ItemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class WebItemController {
    @Autowired
    private ItemService itemService;

    //查询商品内容
    @RequestMapping("/items/{itemId}")
    @ResponseBody
    public Item getItemById(@PathVariable Long itemId) {
        Item item = itemService.queryById(itemId);
        return item;
    }

    @RequestMapping("item/desc/{itemId}")
    @ResponseBody
    public ItemDesc getItemDescById(@PathVariable Long itemId) {
        ItemDesc itemDesc = itemService.getItemDesc(itemId);
        return itemDesc;
    }

}
