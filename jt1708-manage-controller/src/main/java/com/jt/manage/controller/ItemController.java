package com.jt.manage.controller;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.jt.common.vo.EasyUIResult;
import com.jt.common.vo.SysResult;
import com.jt.manage.pojo.Item;
import com.jt.manage.pojo.ItemDesc;
import com.jt.manage.service.ItemService;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;


@Controller
@RequestMapping("/item/")
public class ItemController {
    @Autowired
    private ItemService itemService;
    private static final Logger log = Logger.getLogger(ItemController.class);

    @RequestMapping("/query")
    @ResponseBody
//    page 代表当前页数 , rows 代表每页数量 , EasyUI 封装了参数的过程
    public EasyUIResult queryItemList(Integer page, Integer rows) {
        EasyUIResult easyUIResult = itemService.queryItemList(page, rows);
        return easyUIResult;
    }

    @RequestMapping("/save")
    @ResponseBody
    public SysResult saveItem(Item item,String desc) {

        try {
            itemService.saveItem(item,desc);
            return SysResult.oK();
        } catch (Exception e) {
            //设置错误返回消息
            String message = e.getMessage();
            log.error(message);
            return SysResult.build(201, message);
        }
    }

    @RequestMapping("/update")
    @ResponseBody
    public SysResult updateItem(Item item,String desc) {
        try {
            itemService.updateItem(item ,desc);
            return SysResult.oK();
        } catch (Exception e) {
            log.error(e.getMessage());
            return SysResult.build(201, e.getMessage());
        }
    }

    @RequestMapping("/delete")
    @ResponseBody
    public SysResult deleteItems(Long[] ids) {
        try {
            itemService.deleteItems(ids);
            return SysResult.oK();
        } catch (Exception e) {
            String msg = e.getMessage();
            log.error(msg);
            return SysResult.build(201,msg);
        }
    }

    /**
     * 获取商品详情
     * @return
     */
    @RequestMapping("query/item/desc/{id}")
    @ResponseBody
    public SysResult getItemDesc(@PathVariable Long id) {
        ItemDesc itemDesc = itemService.getItemDesc(id);
        return SysResult.oK(itemDesc);
    }


}
