package com.jt.order.mapper;

import com.jt.common.mapper.SysMapper;
import com.jt.order.pojo.Order;

public interface OrderMapper extends SysMapper<Order>{
    public void orderCreate(Order order);
}
