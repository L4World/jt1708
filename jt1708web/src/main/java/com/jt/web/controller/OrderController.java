package com.jt.web.controller;

import com.jt.common.vo.SysResult;
import com.jt.web.pojo.Cart;
import com.jt.web.pojo.Order;
import com.jt.web.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.ArrayList;
import java.util.List;

@Controller
public class OrderController {
    @Autowired
    private OrderService orderService;
    //转向订单创建页面http://www.jt.com/order/create.html
    @RequestMapping("/order/create")
    public String orderCreate(Model model) {
        //需要userId
        Long userId = 529L;
        List<Cart> cartList = new ArrayList<>();
        try {
            cartList = orderService.queryCartList(userId);
        } catch (Exception e) {
            e.printStackTrace();
        }
        model.addAttribute("carts", cartList);
        return "order-cart";
    }

    //订单提交
    @RequestMapping("/order/submit")
    @ResponseBody
    public SysResult orderSubmit(Order order) {
        Long userId = 529L;
        order.setUserId(userId);
        String orderId = null;
        try {
            orderId = orderService.saveOrder(order);
            return SysResult.oK(orderId);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return SysResult.build(201,"提交失败");
    }
    //转向成功页面/order/success.html?id="+result.data
    @RequestMapping("/order/success")
    public String success(@RequestParam("id")String orderId,Model model) {
        Order order = null;
        try {
            order = orderService.queryByOrderId(orderId);
        } catch (Exception e) {
            e.printStackTrace();
        }
        model.addAttribute("order", order);
        return "success";
    }
}
