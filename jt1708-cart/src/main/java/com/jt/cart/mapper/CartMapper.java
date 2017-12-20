package com.jt.cart.mapper;

import com.jt.cart.pojo.Cart;
import com.jt.common.mapper.SysMapper;

import java.util.List;

public interface CartMapper extends SysMapper<CartMapper>{
    public List<Cart> queryMyCart(Long userId);
}
