package com.pd.jsonconversion;

import org.codehaus.jackson.map.ObjectMapper;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 将json数据转换为java对象
 */
public class DataMap {

    public static Map<String, String> getJsonData(String json) {
        HashMap<String, String> map = new HashMap<>(10);
        ObjectMapper ob = new ObjectMapper();
        //转化为Java对象 person对象
        JsonRootBean person = null;
        try {
            person = ob.readValue(json, JsonRootBean.class);
        } catch (IOException e) {
            e.printStackTrace();
        }
        List<Cardsinfo> cardsinfo = person.getCardsinfo();
        for (Cardsinfo cardsinfo1 : cardsinfo) {
            for (Items item : cardsinfo1.getItems()) {
                if (item.getDesc() != null) {
                    map.put(item.getDesc(), item.getContent());
                }
            }
        }
        return map;
    }
}
