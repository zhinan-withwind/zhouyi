package com.zhinan.zhouyi.name;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.zhinan.zhouyi.base.五行;
import com.zhinan.zhouyi.util.FileUtil;
import lombok.Data;
import lombok.experimental.Accessors;

import java.util.*;

@Data
@Accessors(chain = true)
public class 汉字 {
    static Map<String, 汉字> dictionary;
    static Map<String, List<汉字>> dictMap;
    static Map<String, 汉字> load() {
        dictionary = new HashMap<>();
        dictMap    = new HashMap<>();
        try {
            JSONArray characters = JSON.parseArray(FileUtil.loadResource("data/dictionary.json"));
            for (Object o : characters) {
                JSONObject c = (JSONObject) o;
                汉字 hz = new 汉字()
                        .setSimplified(c.getString("simple"))
                        .setTraditional(c.getString("complex"))
                        .setStrokeNum(c.getInteger("num"))
                        .setWuXing(五行.getByName(c.getString("element")))
                        .setJiXiong(吉凶.getByName(c.getString("jx")));
                dictionary.put(hz.simplified, hz);
                dictMap.computeIfAbsent(hz.strokeNum + "-" + hz.wuXing.getValue(), k -> new ArrayList<>()).add(hz);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return dictionary;
    }

    public static Map<String, 汉字> getDictionary() {
        return dictionary == null ? load() : dictionary;
    }

    public static Map<String, List<汉字>> getDictMap() {
        if (dictMap == null) load();
        return dictMap;
    }

    public static 汉字 get(String c) {
        return getDictionary().get(c);
    }

    public static List<汉字> get(int strokeNum, 五行 wuXing) {
        return getDictMap().computeIfAbsent(strokeNum + "-" + wuXing.getValue(), k -> new ArrayList<>());
    }

    String simplified;
    String traditional;
    int    strokeNum;
    五行    wuXing;
    吉凶    jiXiong;
    String description;

    @Override
    public String toString() {
        return "汉字{" +
                "simplified='" + simplified + '\'' +
                ", traditional='" + traditional + '\'' +
                ", strokeNum=" + strokeNum +
                ", wuXing=" + wuXing +
                ", jiXiong=" + jiXiong +
                ", description='" + description + '\'' +
                '}';
    }
}
