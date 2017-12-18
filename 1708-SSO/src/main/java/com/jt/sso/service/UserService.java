package com.jt.sso.service;

import com.jt.common.service.BaseService;
import com.jt.sso.mapper.UserMapper;
import com.jt.sso.pojo.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
public class UserService extends BaseService<User> {
    @Autowired
    private UserMapper userMapper;

    public boolean check(String param,Integer type) {
        //构建一个传递给 userMapper的Map对象,就爱那个param传递给他
        Map<String, String> map = new HashMap<>();
        if (type == 1) {
            //username
            map.put("name", "username");
        } else if (type == 2) {
            map.put("name", "phone");
        }else{
            map.put("name","email");
        }
        map.put("val", param);
        int num = userMapper.check(map);
        return num>0;
    }
}
