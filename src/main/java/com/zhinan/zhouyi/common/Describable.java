package com.zhinan.zhouyi.common;

import com.alibaba.fastjson.JSONObject;

public interface Describable {
    JSONObject describe();
    String toDescription();
}
