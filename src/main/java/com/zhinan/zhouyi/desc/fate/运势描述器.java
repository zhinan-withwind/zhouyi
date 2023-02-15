package com.zhinan.zhouyi.desc.fate;

import com.zhinan.zhouyi.desc.BaseDescriptor;
import com.zhinan.zhouyi.fate.luck.运势;

import java.time.format.DateTimeFormatter;

public class 运势描述器 extends BaseDescriptor<运势> {
    public String describe(运势 luck) {
        return  "这步运势起始于" +
                DateTimeFormatter.ofPattern("yyyy-MM-dd").format(luck.getStartTime()) +
                "，截止于" +
                DateTimeFormatter.ofPattern("yyyy-MM-dd").format(luck.getEndTime()) + "。" +
                lineSeparator +
                "在这" + luck.getType().getUnit() + "里，您走的是" + luck + "运，" +
//                "这步运势的得分是：" + luck.getScore() + "分，" +
                "是一步" + 运势.级别.getByScore(luck.getScore()) + "的运势。" +
                lineSeparator +
                "其中" + luck.getGan() + "是你的" + luck.getGanGod() + "，" +
                "它是你的" + (luck.isGanGodGood() ? "喜神，也就是对你有利的能量。" : "忌神，也就是对你不利的能量。") +
                lineSeparator +
                "它会带给你" +
                十神描述器.getInstance().describe(luck.getGanGod(), luck.isGanGodGood() ? "正向心性" : "负向心性") +
                "等等，这些方面的特性。" +
                lineSeparator +
                "另外" + luck.getZhi() + "是你的" + luck.getZhiGod() + "，" +
                "它是你的" + (luck.isZhiGodGood() ? "喜神，也就是对你有利的能量。" : "忌神，也就是对你不利的能量。") +
                lineSeparator +
                "它会带给你" +
                十神描述器.getInstance().describe(luck.getZhiGod(), luck.isZhiGodGood() ? "正向心性" : "负向心性") +
                "等等，这些方面的特性。";
    }
}
