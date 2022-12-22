package com.zhinan.zhouyi.desc.fate;

import com.alibaba.fastjson.JSONObject;
import com.zhinan.zhouyi.base.五行;
import com.zhinan.zhouyi.common.Descriptor;
import com.zhinan.zhouyi.common.Source;
import com.zhinan.zhouyi.desc.基础描述器;
import com.zhinan.zhouyi.energy.能量;

@Descriptor(source = Source.星鹤, isDefault = true)
public class 能量描述器 extends 基础描述器<能量> {

    public JSONObject 描述(Object o) {
        能量 energy = (能量) o;
        JSONObject result = new JSONObject();
        for (五行 wuXing : 五行.values()) {
            result.put(wuXing.getName(), new JSONObject()
                    .fluentPut("占比", energy.getPercentage(wuXing))
                    .fluentPut("个数", energy.getNumber(wuXing))
                    .fluentPut("值", energy.getValue(wuXing))
            );
        }
        result.put("最强", energy.getMax());
        result.put("最弱", energy.getMin());
        result.put("总和", energy.getTotal());
        result.put("同党", energy.getMyPart());
        result.put("异党", energy.getOtherPart());
        return result;
    }

    public String describe(Object o) {
        能量 energy = (能量) o;
        StringBuilder sb = new StringBuilder();
        sb.append("在你的命局中，五行的能量分布状态为：").append(lineSeparator);
        for (五行 wuXing : 五行.values()) {
           sb.append("『").append(wuXing.getName()).append("』：")
                   .append(describeNumber(energy.getValue(wuXing), 3))
                   .append("，\t占比为：")
                   .append(describeNumber(energy.getPercentage(wuXing), 3)).append("%；").append(lineSeparator);
        }
        sb.append("五行当中").append(energy.getMax()).append("的能量是最强的，占了")
                .append(describeNumber(energy.getPercentage(energy.getMax()), 3)).append("%，").append(lineSeparator)
                .append("然而五行").append(energy.getMin()).append("的能量是最弱的，只有")
                .append(describeNumber(energy.getPercentage(energy.getMin()), 3)).append("%。").append(lineSeparator)
        ;
        sb.append("其中帮助你的能量占了：").append(energy.getMyPart() * 100 / energy.getTotal()).append("%")
                .append(lineSeparator)
                .append("克制消耗你的能量占了：").append(100 - energy.getMyPart() * 100 / energy.getTotal()).append("%")
                .append(lineSeparator);
        sb.append("这是一个").append(energy.getMyPart() > energy.getOtherPart() ? "身强" : "身弱").append("的格局。")
                .append(lineSeparator);
        return sb.toString();
    }

    public static String describeNumber(Integer value, int width) {
        StringBuilder sb = new StringBuilder();
        int length = value.toString().length();
        if (width > length) {
            for (int i = 0; i < width - length; i++) sb.append(" ");
        }
        sb.append(value);
        return sb.toString();
    }

    public static void generateEnergyChart() {

    }
}
