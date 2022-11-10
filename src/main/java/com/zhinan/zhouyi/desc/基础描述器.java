package com.zhinan.zhouyi.desc;

import com.zhinan.zhouyi.common.Source;

import java.util.HashMap;
import java.util.Map;

public class 基础描述器 {
    private final static Map<String, HashMap<String, HashMap<String, String[]>>> registry = new HashMap<>();

    public static void register(String source, String dClass, String key, String[] descriptions) {
        registry.computeIfAbsent(source, s -> new HashMap<>())
                .computeIfAbsent(dClass, d -> new HashMap<>())
                .put(key, descriptions);
    }

    public static Map<String, String[]> getDescriptions(String dClass) {
        return registry
                .computeIfAbsent(Source.getCurrent().getName(), s -> new HashMap<>())
                .computeIfAbsent(dClass, d -> new HashMap<>());
    }
}
