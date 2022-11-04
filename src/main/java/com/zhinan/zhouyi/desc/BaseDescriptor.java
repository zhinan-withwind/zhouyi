package com.zhinan.zhouyi.desc;

import java.util.HashMap;
import java.util.Map;

public class BaseDescriptor<T> {
    private final static Map<String, Map<String, HashMap<String, String[]>>> register = new HashMap<>();

    public void register(String key, String source, String[] descriptions) {

    }
}
