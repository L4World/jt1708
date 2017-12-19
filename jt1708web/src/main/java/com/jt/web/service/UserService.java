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

    public String doRegister(User user) {
        //发起http请求的连接地址
        String url = "http://sso.jt.com/user/register";
        //封装http请求的数据
        Map<String, String> param = new HashMap<>();
        //准备参数
        param.put("username", user.getUsername());
        param.put("password", user.getPassword());
        param.put("phone", user.getPhone());
        param.put("email", user.getEmail());
        try {
            String jsonData = httpClientService.doPost(url, param, "utf-8");
            JsonNode jsonNode = MAPPER.readTree(jsonData);
            String username = jsonNode.get("data").asText();
            SysResult sysResult = MAPPER.readValue(jsonData, SysResult.class);
            return username;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

}
