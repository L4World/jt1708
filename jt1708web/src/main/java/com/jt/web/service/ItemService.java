package com.jt.web.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.jt.common.service.HttpClientService;
import com.jt.common.service.RedisService;
import com.jt.web.pojo.Item;
import com.jt.web.pojo.ItemDesc;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ItemService {

    @Autowired
    private HttpClientService httpClientService;
    private static final ObjectMapper MAPPER = new ObjectMapper();
    @Autowired
    private RedisService redisService;

    public Item getItem(Long itemId) {
        //模拟发起http请求,需要一个url
        String url = "http://manage.jt.com/items/" + itemId;
        //引入redis的缓存,如果有数据就直接返回,没有数据继续执行业务
        //key值的计算,和后台需要一致
        String ITEM_KEY = "ITEM_" + itemId;
        //习惯上的一个过期时间,10天,60*60*24*10
        String jsonData = redisService.get(ITEM_KEY);
        try {
            if (StringUtils.isNotEmpty(jsonData)) {
                Item item = MAPPER.readValue(jsonData, Item.class);
                return item;
            }else{
                jsonData = httpClientService.doGet(url, "utf-8");
                Item item = MAPPER.readValue(jsonData, Item.class);
                redisService.set(ITEM_KEY, jsonData, 60 * 60 * 24 * 10);
                return item;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public ItemDesc getItemDesc(Long itemId) {
        String url = "http://manage.jt.com/item/desc/" + itemId;
        try {
            String jsonData = httpClientService.doPostJson(url, "utf-8");
            ItemDesc itemDesc = MAPPER.readValue(jsonData, ItemDesc.class);
            return itemDesc;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}
