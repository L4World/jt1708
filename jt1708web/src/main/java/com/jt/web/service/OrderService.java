package com.jt.web.service;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.jt.common.service.HttpClientService;
import com.jt.web.pojo.Cart;
import com.jt.web.pojo.Order;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class OrderService {
    @Autowired
    private HttpClientService httpClientService;
    private static final ObjectMapper MAPPER = new ObjectMapper();

    public List<Cart> queryCartList(Long userId) throws Exception {
        String url = "http://cart.jt.com/cart/query/" + userId;
        String jsonData = httpClientService.doGet(url, "utf-8");
        JsonNode jsonNode = MAPPER.readTree(jsonData).get("data");
        List<Cart> cartList = null;
        if (jsonNode != null && jsonNode.size() > 0) {
            cartList = MAPPER.readValue(jsonNode.traverse(), MAPPER.getTypeFactory().constructCollectionType(List.class, Cart.class));
        }
        return cartList;
    }

    public String saveOrder(Order order) throws Exception {
        String url = "http://order.jt.com/order/create";
        String jsonData = MAPPER.writeValueAsString(order);
        String orderId = httpClientService.doPostJson(url, jsonData);
        return orderId;
    }

    public Order queryByOrderId(String orderId) throws Exception {
        String url = "http://order.jt.com/order/query/" + orderId;
        String jsonData = httpClientService.doGet(url, "utf-8");
        Order order = MAPPER.readValue(jsonData, Order.class);
        return order;
    }
}
