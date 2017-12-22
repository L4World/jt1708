package com.jt.cart.service;

import com.jt.cart.mapper.CartMapper;
import com.jt.cart.pojo.Cart;
import com.jt.common.service.BaseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
public class CartService extends BaseService<Cart>{
    @Autowired
    private CartMapper cartMapper;

    public List<Cart> queryMyCart(Long userId) {
        List<Cart> cartList = cartMapper.queryMyCart(userId);
        return cartList;
    }

    //保存购物车
    public int saveCart(Cart cart) {
        /**
         * 1.判断商品是否存在于购物车
         * 2.如果不存在,新增
         * 3.如果存在,修改其数量,在旧的商品数量上增加新的
         */
        Cart param = new Cart();
        param.setUserId(cart.getUserId());
        param.setItemId(cart.getItemId());
        Cart oldCart = super.queryByWhere(param);
        if (null == oldCart) {
            //不存在对应用户和对应商品的相同购物车
            cart.setCreated(new Date());
            cart.setUpdated(cart.getCreated());
            cartMapper.insertSelective(cart);
        }else{
            //新数量=旧数量+传递数量
            oldCart.setNum(oldCart.getNum()+cart.getNum());
            oldCart.setUpdated(new Date());
            cartMapper.updateByPrimaryKeySelective(oldCart);
        }
        return 200;
    }

    public void updateNum(Long userId,Long itemId,Integer num) {
        Cart param = new Cart();
        param.setUserId(userId);
        param.setItemId(itemId);
        param.setNum(num);
        param.setUpdated(new Date());
        cartMapper.updateNum(param);
    }



}
