package com.zhinan.zhouyi.common;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.zhinan.zhouyi.name.汉字;
import lombok.Data;
import lombok.experimental.Accessors;

import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;

@Data
@Accessors(chain = true)
public class Region {
    Long id;
    Long parentId;
    String code;
    String name;
    Double latitude;
    Double longitude;

    private static Map<Long, Region> idMap;
    private static Map<String, Region> codeMap;

    public static void load() {
        idMap   = new HashMap<>();
        codeMap = new HashMap<>();

        InputStream is = Region.class.getClassLoader().getResourceAsStream("data/region.json");
        try {
            assert is != null;
            JSONArray regions = JSON.parseObject(is, JSONArray.class);
            for (Object o : regions) {
                JSONObject r = (JSONObject) o;
                Region region = new Region()
                        .setId(r.getLong("code"))
                        .setParentId(r.getLong("parentId"))
                        .setCode(r.getString("code"))
                        .setName(r.getString("name"))
                        .setLatitude(r.getDouble("latitude"))
                        .setLongitude(r.getDouble("longitude"));
                idMap.put(region.getId(), region);
                codeMap.put(region.getCode(), region);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static Region getByCode(String code) {
        if (codeMap == null) {
            load();
        }
        return codeMap.get(code);
    }
}
