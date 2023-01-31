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

    private static void load() {
        idMap = new HashMap<>();
        codeMap = new HashMap<>();

        JSONArray regions = JSON.parseArray(FileUtil.loadResource("data/region.json"));
        for (Object o : regions) {
            JSONObject r = (JSONObject) o;
            Region region = new Region()
                    .setId(r.getLong("id"))
                    .setParentId(r.getLong("parentId"))
                    .setCode(r.getString("code"))
                    .setName(r.getString("name"))
                    .setLatitude(r.getDouble("latitude"))
                    .setLongitude(r.getDouble("longitude"));
            idMap.put(region.getId(), region);
            codeMap.put(region.getCode(), region);
        }

    }

    private static Map<String, Region> getCodeMap() {
        if (codeMap == null) {
            load();
        }
        return codeMap;
    }

    private static Map<Long, Region> getIdMap() {
        if (idMap == null) {
            load();
        }
        return idMap;
    }

    public static Region getDefault() {
        return getCodeMap().values().iterator().next();
    }

    public static Region getByCode(String code) {
        // 如果没给地区code，则返回默认地区（默认返回北京）
        // 如果未找到地区，就取上级市的地区
        return code == null ? getDefault() :
                getCodeMap().get(code) == null ?
                        getCodeMap().get(code.substring(0, 4) + "00") :
                        getCodeMap().get(code);
    }

    public static Region getByName(String name) {
        Region result = null;
        for (Region r : getIdMap().values()) {
            if (name.equals(r.getName())) {
                result = r;
                break;
            }
        }
        return result;
    }

    public Region getParent() {
        return parentId == null ? null : getIdMap().get(parentId);
    }

    public String getFullName() {
        return (getParent() != null ? getParent().getFullName() : "") + getName();
    }
}
