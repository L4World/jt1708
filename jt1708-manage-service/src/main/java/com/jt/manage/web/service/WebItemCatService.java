package com.jt.manage.web.service;

import com.jt.common.service.BaseService;
import com.jt.common.vo.ItemCatData;
import com.jt.common.vo.ItemCatResult;
import com.jt.manage.pojo.ItemCat;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class WebItemCatService extends BaseService<ItemCat> {

    public ItemCatResult queryItemCat() {
        //准备返回给 controller 的对象
        ItemCatResult itemCatResult = new ItemCatResult();
        //获取所有的itemcat , 封装到一个 list中
        //然后对这个list的数据进行加工处理
        List<ItemCat> itemCatList = super.queryAll();
        //这里引入一个 Map 对象, 配合1个list循环拼接的方式完成
        //这种3层结构的封装, 涉及到ItemCatData
        Map<Long, List<ItemCat>> map = new HashMap<>();
        //第一步封装 , 将一个父类对应id作为 key保存在map中,value保存了这个id对应的下一级分类的ItemCat
        for (ItemCat itemCat : itemCatList) {
            //从当前的itemCat中获取parentId
            Long parentId = itemCat.getParentId();
            //判断当前获取的parentId在当前map中是否存在,
            if (!map.containsKey(parentId)) {
                map.put(parentId, new ArrayList<ItemCat>());
            }
            map.get(parentId).add(itemCat);
        }
        //构建三级菜单
        List<ItemCat> itemCatList1 = map.get(0L);
        //为一级菜单构建所有子菜单
        List<ItemCatData> itemCatDataList1 = new ArrayList<>();
        for (ItemCat itemCat1 : itemCatList1) {
            //添加数据,u,n,i
            ItemCatData itemCatData1 = new ItemCatData();
            itemCatData1.setUrl("/products/" + itemCat1.getId() + ".html");
            itemCatData1.setName("<a href='" + itemCatData1.getUrl() + "'>" + itemCat1.getName() + "</a>");
            //做 i
            //遍历二级菜单 , 构建二级结构
            List<ItemCatData> itemCatDataList2 = new ArrayList<>();
            for (ItemCat itemCat2 : map.get(itemCat1.getId())) {
                ItemCatData itemCatData2 = new ItemCatData();
                itemCatData2.setUrl("/products/" + itemCat2.getId() + ".html");
                itemCatData2.setName("<a href='" + itemCatData2.getUrl() + "'>" + itemCat2.getName() + "</a>");
                //遍历三级菜单 ,构建三级结构
                List<String> itemCatDataList3 = new ArrayList<>();
                for (ItemCat itemCat3 : map.get(itemCat2.getId())) {
                    itemCatDataList3.add("/products/" + itemCat3.getId() + ".html|" + itemCat3.getName());
                }
                itemCatData2.setItems(itemCatDataList3);
                itemCatDataList2.add(itemCatData2);

            }
            itemCatData1.setItems(itemCatDataList2);
            itemCatDataList1.add(itemCatData1); //首页的菜单只能返回14条
            if (itemCatDataList1.size() > 14) {
                break;
            }
        }
        itemCatResult.setItemCatDataList(itemCatDataList1);
        return itemCatResult;
    }
}
