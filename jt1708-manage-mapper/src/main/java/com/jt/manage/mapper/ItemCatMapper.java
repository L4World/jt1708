package com.jt.manage.mapper;

import com.jt.common.mapper.SysMapper;
import com.jt.manage.pojo.ItemCat;

import java.util.List;

public interface ItemCatMapper extends SysMapper<ItemCat>{

    public List<ItemCat> queryItemCatList(Integer parentId);
}
