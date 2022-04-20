package com.zhinan.zhouyi.fate;

import com.zhinan.zhouyi.date.DateTimeFormatter;
import com.zhinan.zhouyi.date.SolarDateTime;
import com.zhinan.zhouyi.fate.luck.运势;
import com.zhinan.zhouyi.util.DateUtil;

import java.time.LocalDate;
import java.util.*;

public class 简盘 {
    Map<String, String> 十神描述 = new HashMap<>();
    Map<String, String> 十神属性 = new HashMap<>();
    Map<String, String> 五行能量;

    String         命主;
    String         命主描述;
    String         命局;
    String         命局描述;
    List<String>   五行关系;
    String         阴历生日;
    String         阳历生日;

    List<String>   干神 = new ArrayList<>();
    List<String>   天干 = new ArrayList<>();
    List<String>   地支 = new ArrayList<>();
    List<String[]> 藏干 = new ArrayList<>();
    List<String[]> 支神 = new ArrayList<>();
    List<String>   空亡 = new ArrayList<>();
    List<String>   纳音 = new ArrayList<>();
    List<String>   星运 = new ArrayList<>();
    List<String>   自坐 = new ArrayList<>();

    List<String>   天干本命;
    List<String>   地支本命;
    List<String>   天干运势;
    List<String>   地支运势;

    String         起运日期;
    String         起运年岁;

    List<String[]> 大运;
    List<String[]> 流年;
    List<String[]> 流月;
    List<String[]> 流日;
    List<String[]> 流时;

    public 简盘(命盘 pan) {
        八字 bazi = 八字.of(pan.birthday, pan.sex.getValue());
        命主 = bazi.getMing().getName();
        命局 = bazi.getPattern().getName();

        阳历生日 = DateTimeFormatter.getInstance(SolarDateTime.of(pan.birthday))
                .format(DateTimeFormatter.DATE_FORMAT_TYPE.ARABIC_NUMBER);
        阴历生日 = DateTimeFormatter.getInstance(DateUtil.toLunar(pan.birthday))
                .format(DateTimeFormatter.DATE_FORMAT_TYPE.CHINESE_NUMBER);

        for (int i = 0; i < pan.ganList.size(); i++) {
            干神.add(pan.ganGodList.get(i).getName());
            天干.add(pan.ganList.get(i).getName());
            地支.add(pan.zhiList.get(i).getName());
            藏干.add(toStringArray(pan.hideGanList.get(i)));
            支神.add(toStringArray(pan.zhiGodList.get(i)));
            空亡.add((Arrays.toString(pan.emptyList.get(i))));
            纳音.add(pan.soundList.get(i).getName());
            星运.add(pan.starStatusList.get(i).getName());
            自坐.add(pan.selfStatusList.get(i).getName());
        }

        LocalDate luckAge  = com.zhinan.zhouyi.fate.luck.大运.calculateLuckAge (pan.birthday, pan.sex.getValue());
        LocalDate luckYear = com.zhinan.zhouyi.fate.luck.大运.calculateLuckDate(pan.birthday, pan.sex.getValue());
        起运年岁 = luckAge.getYear() + "岁"  + luckAge.getMonthValue() + "个月" + luckAge.getDayOfYear() + "天";
        起运日期 = luckYear.getYear() + "年" + luckYear.getMonthValue() + "月" + luckYear.getDayOfYear() + "日";

        大运 = buildLuckList(pan.decadeLuckList);
        流年 = buildLuckList(pan.yearLuckList);
        流月 = buildLuckList(pan.monthLuckList);
        流日 = buildLuckList(pan.dayLuckList);
        流时 = buildLuckList(pan.hourLuckList);
    }

    private String[] toStringArray(Enum<?>[] enumList) {
        return Arrays.stream(enumList).map(Enum::name).toArray(String[]::new);
    }

    private String[] buildLuckData(运势 luck) {
        String[] result = null;
        if (luck != null) {
            result = new String[14];
            result[0]  = luck.getAge();
            result[1]  = luck.getDate();
            result[2]  = luck.getGanGod().getShortName();
            result[3]  = luck.getGan().getName();
            result[4]  = luck.getZhi().getName();
            result[5]  = luck.getZhiGod().getShortName();
            result[6]  = String.valueOf(luck.isGanGodGood());
            result[7]  = String.valueOf(luck.isZhiGodGood());
            result[8]  = String.valueOf(luck.isSelected());
            result[9]  = String.valueOf(luck.getStartTime().getYear());
            result[10] = String.valueOf(luck.getStartTime().getMonthValue());
            result[11] = String.valueOf(luck.getStartTime().getDayOfMonth());
            result[12] = String.valueOf(luck.getStartTime().getHour());
            result[13] = luck.getType().name();

        }
        return result;
    }

    private List<String[]> buildLuckList(List<? extends 运势> luckList) {
        List<String[]> result = new ArrayList<>();
        if (luckList != null) {
            luckList.forEach(luck -> result.add(buildLuckData(luck)));
        }
        return luckList == null ? null : result;
    }

}
