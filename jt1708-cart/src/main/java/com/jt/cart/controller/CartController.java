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
        } else {
            return SysResult.build(201, "您什么也没有买");
        }
    }

    @RequestMapping("save")
    @ResponseBody
    public SysResult saveCart(Cart cart) {
        try {
            int state = cartService.saveCart(cart);
            if (state == 200) {
                return SysResult.build(200,"新增成功");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return SysResult.build(201, "加入失败");
    }
    //更新购物车商品数量
    @RequestMapping("/update/num/{userId}/{itemId}/{num}")
    public SysResult updateNum(@PathVariable Long userId, @PathVariable Long itemId, @PathVariable Integer num) {
        try {
            cartService.updateNum(userId,itemId,num);
            return SysResult.oK();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return SysResult.build(201, "修改失败");
    }

    //删除购物车
    @RequestMapping("delete/{userId}/{itemId}")
    @ResponseBody
    public SysResult delete(@PathVariable Long userId,@PathVariable Long itemId) {
        Cart param = new Cart();
        param.setUserId(userId);
        param.setItemId(itemId);
        try {
            cartService.deleteByWhere(param);
            return SysResult.oK();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return SysResult.build(201, "删除失败");

    }

}
