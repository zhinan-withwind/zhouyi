package com.zhinan.zhouyi.desc.fate;

import com.alibaba.fastjson.JSONObject;
import com.zhinan.zhouyi.desc.BaseDescriptor;
import com.zhinan.zhouyi.desc.ZhouYiDescriptor;
import com.zhinan.zhouyi.fate.bazi.八字;
import com.zhinan.zhouyi.fate.bazi.命盘;

import java.time.LocalDateTime;

public class 八字描述器 extends BaseDescriptor<八字> {
    public static 命盘 getPan(八字 bazi) {
        LocalDateTime now = LocalDateTime.now();
        return 命盘.of(bazi.getBirthday(), bazi.getSex().getValue())
                .atDay(now.getYear(), now.getMonthValue(), now.getDayOfMonth());
    }

    public static String describe(八字 bazi) {
        命盘 pan = getPan(bazi);
        return  ZhouYiDescriptor.describe(bazi.getFate()) + lineSeparator +
                ZhouYiDescriptor.describe(bazi.getFatePattern()) + lineSeparator +
                ZhouYiDescriptor.describe(pan.getOrigin()) + lineSeparator +
                ZhouYiDescriptor.describe(pan.getEnergy()) + lineSeparator +
                lineSeparator;
    }

    public static JSONObject fullDescribe(八字 bazi) {
        命盘 pan = getPan(bazi);
        return new JSONObject()
                .fluentPut("命主", ZhouYiDescriptor.fullDescribe(bazi.getFate()))
                .fluentPut("格局", ZhouYiDescriptor.fullDescribe(bazi.getFatePattern()))
                .fluentPut("原局", ZhouYiDescriptor.fullDescribe(pan.getOrigin()))
                .fluentPut("能量", ZhouYiDescriptor.fullDescribe(pan.getEnergy()))
                ;
    }

}
