package com.zhinan.zhouyi.common;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.zhinan.zhouyi.util.FileUtil;
import lombok.Data;
import lombok.experimental.Accessors;

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
        idMap = new HashMap<>();
        codeMap = new HashMap<>();

        JSONArray regions = JSON.parseArray(FileUtil.loadResource("data/region.json"));
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

    }

    public static Region getByCode(String code) {
        if (codeMap == null) {
            load();
        }
        return codeMap.get(code) == null ? codeMap.get(code.substring(0, 4) + "00") : codeMap.get(code);
    }

    public Region getParent() {
        return parentId == null ? null : idMap.get(parentId);
    }
}
