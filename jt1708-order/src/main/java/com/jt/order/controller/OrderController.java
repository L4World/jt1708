package com.jt.order.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.jt.order.pojo.Order;
import com.jt.order.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.io.IOException;

@Controller
public class OrderController {
    @Autowired
    private OrderService orderService;
    private static final ObjectMapper MAPPER = new ObjectMapper();

    //新增订单,http://order.jt.com/order/create
    @RequestMapping("/order/create")
    @ResponseBody
    public String saveOrder(@RequestBody String json) {
        try {
            Order order = MAPPER.readValue(json,Order.class);
            String orderId = orderService.saveOrder(order);
            return orderId;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return "";
    }

    //http://order.jt.com/order/query/71487577654381
    @RequestMapping("order/query/{orderId}")
    @ResponseBody
    public Order queryOrderByOrderId(@PathVariable String orderId) {
        Order order = orderService.queryByOrderId(orderId);
        return order;
    }
}
