package com.zhinan.zhouyi.desc;

import com.alibaba.fastjson.JSONObject;

public interface IDescriptor<T> {
    void register();
    String getName(Object object);
    String describe(Object object);
    String describe(Object object, String type);
    JSONObject fullDescribe(Object object);
}
