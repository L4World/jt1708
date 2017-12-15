package com.jt.manage.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.jt.common.service.BaseService;
import com.jt.common.service.RedisService;
import com.jt.manage.pojo.ItemCat;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.jt.manage.mapper.ItemCatMapper;
import org.springframework.util.StringUtils;
import redis.clients.jedis.JedisCluster;

import java.io.IOException;
import java.util.List;

@Service
public class ItemCatService extends BaseService<ItemCat> {

    @Autowired
    private ItemCatMapper itemCatMapper;
    @Autowired(required = false)
    private RedisService redisService;
    @Autowired
    private JedisCluster jedisCluster;

    private static final ObjectMapper MAPPER = new ObjectMapper();

    public List<ItemCat> queryItemCatList(Integer parentId) throws IOException {
        //定义key值
        String ITEM_CAT_KEY = "ITEM_CAT_"+parentId;
        //1.读取缓存调用get方法拿到字符串 , 需要json格式 , json格式可以转成java对象
        String jsonData = redisService.get(ITEM_CAT_KEY);
        //判断是否有值
        List<ItemCat> itemCatList = null;
        //判断缓存值json串是否为空
        if (!StringUtils.isEmpty(jsonData)) {
            try {
                //缓存数据json字符串转化为对象
                JsonNode jsonNode = MAPPER.readTree(jsonData);
                itemCatList =MAPPER.readValue(jsonNode.traverse(), MAPPER.getTypeFactory().constructCollectionType(List.class, ItemCat.class));
            } catch (IOException e) {
                //写日志
                e.printStackTrace();
            }
        }else{//没有值,到数据库取
            try {
                itemCatList = itemCatMapper.queryItemCatList(parentId);
                //保存到缓存中,对象变json
                String json = null;
                //对象边json , 对象存入缓存
                json = MAPPER.writeValueAsString(itemCatList);
                redisService.set(ITEM_CAT_KEY, json);
            } catch (JsonProcessingException e) {
                //写日志
                e.printStackTrace();
            }
        }
//        List<ItemCat> itemCatList = itemCatMapper.queryItemCatList(parentId);
        return itemCatList;
    }



}
