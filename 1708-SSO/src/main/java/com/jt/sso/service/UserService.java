package com.jt.sso.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.jt.common.service.BaseService;
import com.jt.common.service.RedisService;
import com.jt.sso.mapper.UserMapper;
import com.jt.sso.pojo.User;
import org.apache.commons.codec.digest.DigestUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Service
public class UserService extends BaseService<User> {
    @Autowired
    private UserMapper userMapper;
    private static final ObjectMapper MAPPER = new ObjectMapper();
    @Autowired
    private RedisService redisService;

    public boolean check(String param, Integer type) {
        //构建一个传递给 userMapper的Map对象,就爱那个param传递给他
        Map<String, String> map = new HashMap<>();
        if (type == 1) {
            //username
            map.put("name", "username");
        } else if (type == 2) {
            map.put("name", "phone");
        } else {
            map.put("name", "email");
        }
        map.put("val", param);
        int num = userMapper.check(map);
        return num > 0;
    }

    //注册的接口方法
    public String register(User user) {
        //对传递过来的数据进行处理,密码,保存在数据库中不能是明文
        user.setPassword(DigestUtils.md5Hex(user.getPassword()));
        user.setCreated(new Date());
        user.setUpdated(user.getCreated());
        //由于前台的注册邮箱和手机号没有同时处理,这里只有一个唯一性
        //校验,email是null,数据库对三个字段做了唯一性校验
        //username,phone,email,不能默认插入email=null
        //拿一个前台做过的唯一性校验来做email和phone
        user.setEmail(user.getPhone());
        userMapper.insertSelective(user);
        return user.getUsername();
    }

    //用户登录
    public String login(String username,String password) {
        //这里有个问题,用哪个方法查询数据
        //byWhere 会对不是null的数据进行查询条件的拼接
        User _user = new User();
        _user.setUsername(username);
        //按照用户名查询
        User user = super.queryByWhere(_user);
        if (null != user) {
            //密码对比
            String newPassword = DigestUtils.md5Hex(password);
            if (newPassword.equals(user.getPassword())) {
                //登录成功,写到redis
                try {
                    String userJson = MAPPER.writeValueAsString(user);
                    //生成key , ticket
                    String ticket = DigestUtils.md5Hex("JT_TICKET" + System.currentTimeMillis() + username);
                    redisService.set(ticket, userJson);
                    return ticket;
                } catch (JsonProcessingException e) {
                    e.printStackTrace();
                }
            }
        }
        return null;
    }

    //查询用户信息
    public String queryByTicket(String ticket) {
        String userJson = redisService.get(ticket);
        return userJson;
    }
}
