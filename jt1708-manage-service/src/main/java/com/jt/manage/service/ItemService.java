package com.jt.manage.service;

import com.jt.common.service.BaseService;
import com.jt.manage.mapper.ItemMapper;
import com.jt.manage.pojo.Item;
import javafx.scene.media.VideoTrack;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
public class ItemService extends BaseService<Item> {
    @Autowired
    private ItemMapper itemMapper;

    public List<Item> queryItemList() {
        return itemMapper.queryItemList();
    }

    public void saveItem(Item item) {
        item.setStatus(1);  //1表示上架,2表示下架,3表示删除
        item.setCreated(new Date());
        item.setUpdated(item.getCreated());
        itemMapper.insertSelective(item);
    }

    public void updateItem(Item item) {
        item.setUpdated(new Date());
        itemMapper.updateByPrimaryKeySelective(item);
    }

    public void deleteItems(Long[] ids) {
        itemMapper.deleteByIDS(ids);
    }

}
