package com.jt.web.service;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.jt.common.service.HttpClientService;
import com.jt.web.pojo.Cart;
import com.jt.web.pojo.Item;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class CartService {
    @Autowired
    private HttpClientService httpClientService;
    private static final ObjectMapper MAPPER = new ObjectMapper();

    public List<Cart> show(Long userId) throws Exception {
        //http://cart.jt.com/cart/query/{userId}
        String url = "http://cart.jt.com/cart/query/" + userId;
        String jsonData = httpClientService.doGet(url, "utf-8");
        JsonNode jsonNode = MAPPER.readTree(jsonData);
        JsonNode data = jsonNode.get("data");
        List<Cart> cartList = null;
        if (data.isArray() && data.size() > 0) {
            cartList = MAPPER.readValue(data.traverse(), MAPPER.getTypeFactory()
                    .constructCollectionType(List.class, Cart.class));
        }
        return cartList;
    }

    public void saveCart(Long userId, Long itemId, Integer num) throws Exception {
        //1.调用后台,获取3个冗余字段
        String url = "http://manage.jt.com/items/"+itemId;
        String jsonData = httpClientService.doGet(url, "utf-8");
        Item item = MAPPER.readValue(jsonData, Item.class);
        //2.调用购物车接口,传递数据
        url = "http://cart.jt.com/cart/save";
        Map<String, String> param = new HashMap<>();
        param.put("userId", userId + "");
        param.put("itemId", itemId + "");
        param.put("num", num + "");
        //从后台获取的数据封装
        param.put("itemTitle", item.getTitle());
        param.put("itemImage", item.getImages()[0]);
        param.put("itemPrice", item.getPrice()+"");
        httpClientService.doPost(url, param, "utf-8");
    }

    public void updateNum(Long userId, Long itemId, Integer num) throws Exception {
        //http://cart.jt.com/cart/update/num/{userId}/{itemId}/{num}
        String url = "http://cart.jt.com/cart/update/num/" + userId + "/" + itemId + "/" + num;
        httpClientService.doGet(url, "utf-8");
    }

    public void delCart(Long userId, Long itemId) throws Exception {
        //http://cart.jt.com/cart/delete/{userId}/{itemId}
        String url = "http://cart.jt.com/cart/delete/"+userId+"/"+itemId;
        httpClientService.doGet(url, "utf-8");
    }
}
