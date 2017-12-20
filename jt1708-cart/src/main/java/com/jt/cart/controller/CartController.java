package com.jt.cart.controller;

import com.jt.cart.pojo.Cart;
import com.jt.cart.service.CartService;
import com.jt.common.vo.SysResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

@Controller
@RequestMapping("cart")
public class CartController {
    @Autowired
    private CartService cartService;
    //查询我的购物车 http://cart.jt.com/cart/query/{userId}
    @RequestMapping("/query/{userId}")
    @ResponseBody
    public SysResult queryMyCart(@PathVariable Long userId) {
        List<Cart> cartList = cartService.queryMyCart(userId);
        if (!cartList.isEmpty()) {
            return SysResult.oK(cartList);
        }else{
            return SysResult.build(201, "您什么也没有买");
        }
    }
}
