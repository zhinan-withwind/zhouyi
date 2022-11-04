package com.zhinan.zhouyi.desc.fate;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.zhinan.zhouyi.desc.IDescriptor;
import com.zhinan.zhouyi.fate.bazi.八字;

public class 八字描述器 {

    public static String describe(八字 bazi) {
        return JSON.toJSONString(描述(bazi), true);
    }

    public static JSONObject 描述(八字 bazi) {
        JSONObject result = new JSONObject();
        result.put("命主", 命主描述器.描述(bazi.getFate()));
        return result;
    }

}
