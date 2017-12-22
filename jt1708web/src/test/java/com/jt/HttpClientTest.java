package com.jt;

import org.apache.http.HttpEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.junit.Test;

import java.io.IOException;

public class HttpClientTest {

//    @Test
//    public void httpClientTest() throws IOException {
//        CloseableHttpClient httpClient = HttpClients.createDefault();
//        //httpClient 的操作对象
//        //模拟http请求 , get请求,访问以下京东
//        String url = "https://www.jd.com/";
//        HttpGet get = new HttpGet(url);
//        CloseableHttpResponse response = httpClient.execute(get);
//        //从response是实体中获取内容
//        HttpEntity entity = response.getEntity();
//        String string = EntityUtils.toString(entity);
//        System.out.println(string);
//    }
}
