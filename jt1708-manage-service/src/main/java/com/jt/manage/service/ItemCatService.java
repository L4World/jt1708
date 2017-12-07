package com.jt.manage.service;

import com.jt.common.service.BaseService;
import com.jt.manage.pojo.ItemCat;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.jt.manage.mapper.ItemCatMapper;

import java.util.List;

@Service
public class ItemCatService extends BaseService<ItemCat> {

    @Autowired
    private ItemCatMapper itemCatMapper;

    public List<ItemCat> queryItemCatList(Integer parentId) {
        List<ItemCat> itemCatList = itemCatMapper.queryItemCatList(parentId);
        return itemCatList;
    }

}
