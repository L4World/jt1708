package com.jt.manage.service;

import com.jt.common.service.BaseService;
import com.jt.manage.mapper.ItemDescMapper;
import com.jt.manage.mapper.ItemMapper;
import com.jt.manage.pojo.Item;
import com.jt.manage.pojo.ItemDesc;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
public class ItemService extends BaseService<Item> {
    @Autowired
    private ItemMapper itemMapper;
    @Autowired
    private ItemDescMapper itemDescMapper;

    public List<Item> queryItemList() {
        return itemMapper.queryItemList();
    }

    //新增商品和商品详情
    public void saveItem(Item item,String desc) {
        item.setStatus(1);  //1表示上架,2表示下架,3表示删除
        item.setCreated(new Date());
        item.setUpdated(item.getCreated());
        itemMapper.insertSelective(item);
        //新增商品详情
        ItemDesc itemDesc = new ItemDesc();
        itemDesc.setItemId(item.getId());   //对应详情的id能否使用
        //item的getId方法
        itemDesc.setItemDesc(desc);
        itemDesc.setCreated(item.getCreated());
        itemDesc.setUpdated(item.getUpdated());
        itemDescMapper.insert(itemDesc);    //插入方法不能写在两个service方法中 , 因为需要事务维护
    }

    public void updateItem(Item item,String desc) {
        item.setUpdated(new Date());
        itemMapper.updateByPrimaryKeySelective(item);
        //商品详情
        ItemDesc itemDesc = new ItemDesc();
        itemDesc.setItemId(item.getId());
        itemDesc.setItemDesc(desc);
        itemDesc.setUpdated(item.getUpdated());
        itemDescMapper.updateByPrimaryKeySelective(itemDesc);
    }

    public void deleteItems(Long[] ids) {
        itemDescMapper.deleteByIDS(ids);
        itemMapper.deleteByIDS(ids);
    }

    public ItemDesc getItemDesc(Long id) {
        ItemDesc itemDesc = itemDescMapper.selectByPrimaryKey(id);
        return itemDesc;
    }
}
