package com.jt.common.vo;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

public class ItemCatData {
    @JsonProperty("u")  //json序列化时指定字段名称
    private String url;
    @JsonProperty("n")
    private String name;
    @JsonProperty("i")
    private List<?> items;

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<?> getItems() {
        return items;
    }

    public void setItems(List<?> items) {
        this.items = items;
    }
}
