package com.jt.test;

import org.junit.Test;
import redis.clients.jedis.*;

import java.util.ArrayList;
import java.util.List;

public class JedisTest {
//    @Test
//    public void test1() {
////        Jedis jedis = new Jedis("106.75.74.254",6);
//        Jedis jedis = new Jedis("176.17.5.90",6380);
//        String name = jedis.get("name");
//        jedis.set("age", "123");
//        System.out.println(name);
//        jedis.close();
//    }
//
//    @Test
//    public void test2Shard() {
//        //分片是什么意思
//        //将key对应的存储到唯一的redis节点中
//        //构造一个集合存储redis所有节点的信息
//        List<JedisShardInfo> infoList = new ArrayList<>();
//        //存储节点信息的内容需要对象 JedisShardInfo 对象
//        JedisShardInfo info1 = new JedisShardInfo("176.17.5.90", 6379);
//        JedisShardInfo info2 = new JedisShardInfo("176.17.5.90", 6380);
//        JedisShardInfo info3 = new JedisShardInfo("176.17.5.90", 6381);
//        infoList.add(info1);
//        infoList.add(info2);
//        infoList.add(info3);
//
//        ShardedJedis jedis = new ShardedJedis(infoList);
//        //什么样的key会对应哪个redis , 例如 name 是放到6379还是6380或是6381
//        String s = jedis.get("name");
//        jedis.set("n1", "500");
//        //key值的取值和 redis 节点的映射 --- hash一致性
//        System.out.println(s);
//        jedis.close();
//    }
//
//    @Test
//    public void test3Pool() {
//        List<JedisShardInfo> infoList = new ArrayList<>();
//        JedisShardInfo info1 = new JedisShardInfo("176.17.5.90", 6379);
//        JedisShardInfo info2 = new JedisShardInfo("176.17.5.90", 6380);
//        JedisShardInfo info3 = new JedisShardInfo("176.17.5.90", 6381);
//        infoList.add(info1);
//        infoList.add(info2);
//        infoList.add(info3);
//
//        //jedis 就是不是new出来 , 需要调用jedis池来获取jedis对象
//        JedisPoolConfig config = new JedisPoolConfig();
//
//        config.setMaxTotal(200);
//        ShardedJedisPool pool = new ShardedJedisPool(config,infoList);
//        ShardedJedis jedis = pool.getResource();
//        String name = jedis.get("name");
//        System.out.println(name);
//        pool.returnResource(jedis);
//        pool.close();
//
//    }
}
