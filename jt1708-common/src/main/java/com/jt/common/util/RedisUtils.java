package com.jt.common.util;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import redis.clients.jedis.JedisCluster;

public class RedisUtils {
    public static final String ITEM_KEY = "ITEM_KEY_";
    public static final String ITEM_DESC_KEY = "ITEM_DESC_KEY_";
    public static final String JT_TICKET = "JT_TICKET";
    private static final ObjectMapper MAPPER = new ObjectMapper();

    public static void saveToRedis(JedisCluster jedisCluster, Object entity, String KEYNAME) throws JsonProcessingException {
        String jsonData = MAPPER.writeValueAsString(entity);
        jedisCluster.set(KEYNAME, jsonData);
    }

}
