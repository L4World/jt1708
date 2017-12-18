package com.jt.web.controller;

import com.jt.web.pojo.Item;
import com.jt.web.pojo.ItemDesc;
import com.jt.web.service.ItemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class ItemController {

    @Autowired
    private ItemService itemService;
    @RequestMapping("/items/{itemId}")
    public String getItemById(Model model, @PathVariable Long itemId) {
        //从service中获取数据
        Item item = itemService.getItem(itemId);
        model.addAttribute("item", item);
        ItemDesc itemDesc = itemService.getItemDesc(itemId);
        model.addAttribute("itemDesc", itemDesc);
        return "item";
    }
}
