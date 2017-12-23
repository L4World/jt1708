package com.jt.manage.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.jt.common.service.BaseService;
import com.jt.common.service.RedisService;
import com.jt.common.util.RedisUtils;
import com.jt.common.vo.EasyUIResult;
import com.jt.manage.mapper.ItemDescMapper;
import com.jt.manage.mapper.ItemMapper;
import com.jt.manage.pojo.Item;
import com.jt.manage.pojo.ItemDesc;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import redis.clients.jedis.JedisCluster;

import java.util.Date;
import java.util.List;

@Service
public class ItemService extends BaseService<Item> {
    @Autowired
    private ItemMapper itemMapper;
    @Autowired
    private ItemDescMapper itemDescMapper;
    @Autowired
    private JedisCluster jedisCluster;
    private static final ObjectMapper MAPPER = new ObjectMapper();

    public EasyUIResult queryItemList(Integer page,Integer rows) {
        PageHelper.startPage(page,rows);
        //只开启当前 startPage 方法下的第一条查询语句的拦截
        List<Item> itemList = itemMapper.queryItemList();
        //用 pageInfo 来封装结果 , 记录总数和当前页的记录商品条数
        PageInfo<Item> info = new PageInfo<Item>(itemList);
        EasyUIResult easyUIResult = new EasyUIResult(info.getTotal(),info.getList());
        return easyUIResult;
    }

    //新增商品和商品详情
    public void saveItem(Item item, String desc) {
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
        //把后台新增页面传递过来的数据封装成json字符串
        //设置key值,将json存入redis
        //TODO--作业:待完成商品新增缓存
        String ITEM_KEY = RedisUtils.ITEM_KEY + item.getId();
        String ITEM_DESC_KEY = RedisUtils.ITEM_DESC_KEY + item.getId();
        try {
            RedisUtils.saveToRedis(jedisCluster,item,ITEM_KEY);
            RedisUtils.saveToRedis(jedisCluster,itemDesc,ITEM_DESC_KEY);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
    }

    public void updateItem(Item item, String desc) {
        item.setUpdated(new Date());
        itemMapper.updateByPrimaryKeySelective(item);
        //商品详情
        ItemDesc itemDesc = new ItemDesc();
        itemDesc.setItemId(item.getId());
        itemDesc.setItemDesc(desc);
        itemDesc.setUpdated(item.getUpdated());
        itemDescMapper.updateByPrimaryKeySelective(itemDesc);
        //TODO-作业:商品修改缓存
        String ITEM_KEY = RedisUtils.ITEM_KEY + item.getId();
        String ITEM_DESC_KEY = RedisUtils.ITEM_DESC_KEY + item.getId();
        jedisCluster.del(ITEM_KEY);
        jedisCluster.del(ITEM_DESC_KEY);
        try {
            RedisUtils.saveToRedis(jedisCluster,item,ITEM_KEY);
            RedisUtils.saveToRedis(jedisCluster,itemDesc,ITEM_DESC_KEY);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
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
