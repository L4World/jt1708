package com.jt.web.service;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.abel533.mapper.Mapper;
import com.jt.common.service.HttpClientService;
import com.jt.common.vo.SysResult;
import com.jt.web.pojo.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
public class UserService {
    @Autowired
    private HttpClientService httpClientService;
    private static final ObjectMapper MAPPER = new ObjectMapper();

    public String doRegister(User user) throws Exception {
        //发起http请求的连接地址
        String url = "http://sso.jt.com/user/register";
        //封装http请求的数据
        Map<String, String> param = new HashMap<>();
        //准备参数
        param.put("username", user.getUsername());
        param.put("password", user.getPassword());
        param.put("phone", user.getPhone());
        param.put("email", user.getEmail());
        String jsonData = httpClientService.doPost(url, param, "utf-8");
        JsonNode jsonNode = MAPPER.readTree(jsonData);
        String username = jsonNode.get("data").asText();
        SysResult sysResult = MAPPER.readValue(jsonData, SysResult.class);
        return username;
    }

    public String doLogin(User user) throws Exception {
        //调用 httpclient
        String url = "http://sso.jt.com/user/login";
        //封装传递的数据,一个是u,一个p
        Map<String, String> params = new HashMap<>();
        params.put("u", user.getUsername());
        params.put("p", user.getPassword());
        String jsonData = httpClientService.doPost(url, params, "utf-8");
        JsonNode jsonNode = MAPPER.readTree(jsonData);
        String ticket = jsonNode.get("data").asText();
        return ticket;
    }
}
