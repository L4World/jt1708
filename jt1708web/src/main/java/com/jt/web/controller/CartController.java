package com.jt.web.controller;

import com.jt.web.pojo.Cart;
import com.jt.web.service.CartService;
import com.jt.web.threadlocal.UserThreadLocal;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping("cart")
public class CartController {

    @Autowired
    private CartService cartService;

    @RequestMapping("show")
    public String show(Model model) {
        Long userId = UserThreadLocal.getUserId();
        List<Cart> cartList = new ArrayList<>();
        try {
            cartList = cartService.show(userId);
        } catch (Exception e) {
            e.printStackTrace();
        }
        model.addAttribute("cartList", cartList);
        return "cart";
    }

    //保存商品到购物车 "http://www.jt.com/cart/add/${item.id}.html"
    @RequestMapping("/add/{itemId}")
    public String addCart(@PathVariable Long itemId, Integer num) {
        //需要当前用户id
        Long userId = UserThreadLocal.getUserId();
        try {
            cartService.saveCart(userId,itemId,num);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "redirect:/cart/show.html";
    }

    //更新商品数量 //service/cart/update/{num}/{itemId}
    @RequestMapping("/update/num/{itemId}/{num}")
    @ResponseBody
    public String updateNum(@PathVariable Integer num,@PathVariable Long itemId) {
        Long userId = UserThreadLocal.getUserId();
        try {
            cartService.updateNum(userId, itemId, num);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "";//如果这里直接返回,springmvc会拼接
    }

    //删除购物车商品,http://www.jt.com/cart/delete/1474391958.html
    @RequestMapping("/delete/{itemId}")
    public String delCart(@PathVariable Long itemId) {
        Long userId = UserThreadLocal.getUserId();
        try {
            cartService.delCart(userId,itemId);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "redirect:/cart/show.html";
    }
}
