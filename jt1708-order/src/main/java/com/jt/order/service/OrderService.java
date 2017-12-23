package com.jt.order.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.jt.common.service.BaseService;
import com.jt.order.mapper.OrderMapper;
import com.jt.order.pojo.Order;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
public class OrderService extends BaseService<Order>{
    @Autowired
    private OrderMapper orderMapper;
    private static final ObjectMapper MAPPER = new ObjectMapper();

    public String saveOrder(Order order) {
        //前台只负责传递订单的部分字段内容
        //需要业务层封装一部分，orderId：userId+currentTime
        String orderId = order.getUserId() + System.currentTimeMillis()+"";
        order.setOrderId(orderId);
        order.setCreated(new Date());
        order.setUpdated(order.getCreated());
        String jsonData = null;
        try {
            jsonData = MAPPER.writeValueAsString(order);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        orderMapper.orderCreate(order);
        return orderId;
    }

    public Order queryByOrderId(String orderId) {
        Order order = orderMapper.queryByOrderId(orderId);
        return order;
    }
}
