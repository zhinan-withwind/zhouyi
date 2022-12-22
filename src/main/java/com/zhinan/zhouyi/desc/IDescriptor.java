package com.zhinan.zhouyi.desc;

import com.alibaba.fastjson.JSONObject;
import com.zhinan.zhouyi.common.Source;

public interface IDescriptor<T> {
    Source getSource();
    void setSource(Source source);
    void register();
    String getName(Object object);
    String describe(Object object);
    JSONObject 描述(Object object);
}
