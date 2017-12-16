package com.jt;

import org.junit.Test;
import redis.clients.jedis.HostAndPort;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisCluster;

import java.util.HashSet;
import java.util.Set;

public class SentinelTest {
//    @Test
//    public void sentinel() {
//        Set<String> oSet = new HashSet<>();
//        //配置哨兵的访问地址不需要写所有的哨兵
//        oSet.add(new HostAndPort("176.17.5.96", 26379).toString());
//        JedisSentinelPool jedisSentinelPool = new JedisSentinelPool("mymaster",oSet);
//        System.out.println("当前master是:"+jedisSentinelPool.getCurrentHostMaster());
//        Jedis jedis = jedisSentinelPool.getResource();
//        jedis.set("name", "xiaohuang");
//        jedisSentinelPool.returnResource(jedis);
//        jedisSentinelPool.close();
//    }

    @Test
    public void redisCLustTest() {
//        HostAndPort hp1 = new HostAndPort("106.75.118.205",6390);
        HostAndPort hp2 = new HostAndPort("106.75.118.205",6391);
//        HostAndPort hp3 = new HostAndPort("106.75.118.205",6392);
//        HostAndPort hp4 = new HostAndPort("106.75.118.205",6393);
//        HostAndPort hp5 = new HostAndPort("106.75.118.205",6394);
//        HostAndPort hp6 = new HostAndPort("106.75.118.205",6395);

        Set<HostAndPort> set = new HashSet<>();
//        set.add(hp1);
        set.add(hp2);
//        set.add(hp3);
//        set.add(hp4);
//        set.add(hp5);
//        set.add(hp6);

        JedisCluster cluster = new JedisCluster(set);
        try {
            cluster.set("test-self", "hehehehheeh 6390 self id good");
        } catch (Exception e) {
            System.out.println(e.getMessage());
            System.out.println(e.getCause());
            System.out.println("6390 down");
        }
        System.out.println("over");
        cluster.close();
    }
}
