package com.zhinan.zhouyi.desc.fate;

import com.zhinan.zhouyi.date.DateFormatType;
import com.zhinan.zhouyi.date.DateTimeFormatter;
import com.zhinan.zhouyi.date.DateType;
import com.zhinan.zhouyi.date.SolarDateTime;
import com.zhinan.zhouyi.fate.luck.运势;

public class 运势描述器 {
    public static String describe(运势 luck) {
        return  "这步运势起始于" +
                DateTimeFormatter.getInstance(SolarDateTime.of(luck.getStartTime()))
                        .format(DateFormatType.ARABIC_NUMBER, DateType.FULL_DATE) +
                "，截止于" +
                DateTimeFormatter.getInstance(SolarDateTime.of(luck.getEndTime()))
                        .format(DateFormatType.ARABIC_NUMBER, DateType.FULL_DATE) +
                "。" +
                System.lineSeparator() +
                "在这" + luck.getType().getUnit() + "里，您走的是" +
                luck + "运，这步运势的得分是：" + luck.getScore() + "分，是一步" +
                运势.级别.getByScore(luck.getScore()) + "的运势。" +
                System.lineSeparator() +
                "其中" + luck.getGan() + "是你的" + luck.getGanGod() + "，" +
                "它是你的" + (luck.isGanGodGood() ? "喜神，也就是对你有利的能量。" : "忌神，也就是对你不利的能量。") +
                System.lineSeparator() +
                "它会带给你" +
                十神描述器.describe(luck.getGanGod(), luck.isGanGodGood() ? 十神描述器.描述类型.正向心性 : 十神描述器.描述类型.负向心性) +
                "等等，这些方面的特性。" +
                System.lineSeparator() +
                "另外" + luck.getZhi() + "是你的" + luck.getZhiGod() + "，" +
                "它是你的" + (luck.isZhiGodGood() ? "喜神，也就是对你有利的能量。" : "忌神，也就是对你不利的能量。") +
                System.lineSeparator() +
                "它会带给你" +
                十神描述器.describe(luck.getZhiGod(), luck.isZhiGodGood() ? 十神描述器.描述类型.正向心性 : 十神描述器.描述类型.负向心性) +
                "等等，这些方面的特性。";
    }
}
