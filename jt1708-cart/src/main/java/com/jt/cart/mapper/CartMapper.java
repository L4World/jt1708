package com.jt.cart.mapper;

import com.jt.cart.pojo.Cart;
import com.jt.common.mapper.SysMapper;

import java.util.List;

public interface CartMapper extends SysMapper<Cart>{
    public List<Cart> queryMyCart(Long userId);

    public void updateNum(Cart cart);
}
