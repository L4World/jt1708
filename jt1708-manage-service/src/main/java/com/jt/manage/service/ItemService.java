package com.jt.manage.service;

import com.jt.common.service.BaseService;
import com.jt.manage.mapper.ItemMapper;
import com.jt.manage.pojo.Item;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ItemService extends BaseService<Item> {
    @Autowired
    private ItemMapper itemMapper;

    public List<Item> queryItemList() {
        return itemMapper.queryItemList();
    }

}
