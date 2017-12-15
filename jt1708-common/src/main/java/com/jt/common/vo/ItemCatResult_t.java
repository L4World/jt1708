package com.jt.common.vo;

import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;

public class ItemCatResult_t {

    @JsonProperty("data")	//json序列化时指定字段名称
    private List<ItemCatData_t> itemCats = new ArrayList<ItemCatData_t>();

    public List<ItemCatData_t> getItemCats() {
        return itemCats;
    }

    public void setItemCats(List<ItemCatData_t> itemCats) {
        this.itemCats = itemCats;
    }

}
