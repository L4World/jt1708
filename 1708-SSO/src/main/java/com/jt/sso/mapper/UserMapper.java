package com.jt.sso.mapper;

import com.jt.common.mapper.SysMapper;
import com.jt.sso.pojo.User;

import java.util.Map;

public interface UserMapper extends SysMapper<User>{
    public int check(Map<String,String> map);
}
