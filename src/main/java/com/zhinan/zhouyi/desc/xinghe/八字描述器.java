package com.zhinan.zhouyi.desc.xinghe;

import com.alibaba.fastjson.JSONObject;
import com.zhinan.zhouyi.common.Descriptor;
import com.zhinan.zhouyi.common.Source;
import com.zhinan.zhouyi.desc.周易描述器;
import com.zhinan.zhouyi.desc.基础描述器;
import com.zhinan.zhouyi.fate.bazi.八字;
import com.zhinan.zhouyi.fate.bazi.命盘;

import java.time.LocalDateTime;

@Descriptor(source = Source.星鹤, isDefault = true)
public class 八字描述器 extends 基础描述器<八字> {
    public static 命盘 getPan(八字 bazi) {
        LocalDateTime now = LocalDateTime.now();
        return 命盘.of(bazi.getBirthday(), bazi.getSex().getValue())
                .atDay(now.getYear(), now.getMonthValue(), now.getDayOfMonth());
    }

    public static String describe(八字 bazi) {
        命盘 pan = getPan(bazi);
        return  周易描述器.describe(bazi.getFate()) + lineSeparator +
                周易描述器.describe(bazi.getFatePattern()) + lineSeparator +
                周易描述器.describe(pan.getOrigin()) + lineSeparator +
                周易描述器.describe(pan.getEnergy()) + lineSeparator +
                lineSeparator;
    }

    public static JSONObject 描述(八字 bazi) {
        命盘 pan = getPan(bazi);
        return new JSONObject()
                .fluentPut("命主", 周易描述器.描述(bazi.getFate()))
                .fluentPut("格局", 周易描述器.描述(bazi.getFatePattern()))
                .fluentPut("原局", 周易描述器.描述(pan.getOrigin()))
                .fluentPut("能量", 周易描述器.描述(pan.getEnergy()))
                ;
    }

}
