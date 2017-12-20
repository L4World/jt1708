package com.jt.cart.service;

import com.jt.cart.mapper.CartMapper;
import com.jt.cart.pojo.Cart;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CartService {
    @Autowired
    private CartMapper cartMapper;

    public List<Cart> queryMyCart(Long userId) {
        List<Cart> cartList = cartMapper.queryMyCart(userId);
        return cartList;
    }
}
