package com.zhinan.zhouyi.desc.mingyi;

import com.alibaba.fastjson.JSONObject;
import com.zhinan.zhouyi.common.Descriptor;
import com.zhinan.zhouyi.common.Source;
import com.zhinan.zhouyi.desc.周易描述器;
import com.zhinan.zhouyi.desc.基础描述器;
import com.zhinan.zhouyi.fate.bazi.八字;
import com.zhinan.zhouyi.fate.util.健康;
import com.zhinan.zhouyi.fate.util.财运;

@Descriptor(source = Source.明易)
public class 八字描述器 extends 基础描述器<八字> {
    public JSONObject 描述(Object o) {
        八字 bazi = (八字) o;
        return new JSONObject()
                .fluentPut("命主", 周易描述器.描述(bazi.getFate()))
                .fluentPut("格局", 周易描述器.描述(bazi.getFatePattern()))
                .fluentPut("能量", 周易描述器.描述(bazi.getEnergy()))
                .fluentPut("财运", 周易描述器.描述(财运.of(bazi)))
                .fluentPut("健康", 周易描述器.描述(健康.of(bazi)));
    }

}
