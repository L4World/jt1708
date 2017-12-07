package com.jt.manage.controller;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.jt.common.vo.EasyUIResult;
import com.jt.manage.pojo.Item;
import com.jt.manage.service.ItemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

@Controller
@RequestMapping("/item/")
public class ItemController {
    @Autowired
    private ItemService itemService;

    @RequestMapping("/query")
    @ResponseBody
    //page 代表当前页数 , rows 代表每页数量 , EasyUI 封装了参数的过程
    public EasyUIResult queryItemList(Integer page,Integer rows) {
        PageHelper.startPage(page,rows);
        //只开启当前 startPage 方法下的第一条查询语句的拦截
        List<Item> itemList = itemService.queryItemList();
        //用 pageInfo 来封装结果 , 记录总数和当前页的记录商品条数
        PageInfo<Item> pageInfo = new PageInfo<Item>(itemList);
        return new EasyUIResult(pageInfo.getTotal(),pageInfo.getList());
    }
}
