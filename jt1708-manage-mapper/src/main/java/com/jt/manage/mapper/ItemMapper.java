package com.jt.manage.mapper;

import com.jt.common.mapper.SysMapper;
import com.jt.manage.pojo.Item;

import java.util.List;

public interface ItemMapper extends SysMapper<Item>{
    public List<Item> queryItemList();
}
