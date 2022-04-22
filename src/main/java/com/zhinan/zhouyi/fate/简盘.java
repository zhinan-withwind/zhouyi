package com.zhinan.zhouyi.fate;

import com.zhinan.zhouyi.base.十神;
import com.zhinan.zhouyi.base.地支;
import com.zhinan.zhouyi.base.天干;
import com.zhinan.zhouyi.base.生克;
import com.zhinan.zhouyi.date.DateTimeFormatter;
import com.zhinan.zhouyi.date.SolarDateTime;
import com.zhinan.zhouyi.desc.Descriptor;
import com.zhinan.zhouyi.fate.luck.运势;
import com.zhinan.zhouyi.util.DateUtil;
import lombok.Getter;

import java.util.*;

@Getter
public class 简盘 {
    Map<String, String> 十神描述 = new HashMap<>();
    Map<String, String> 十神属性 = new HashMap<>();
    Map<String, String> 五行关系 = new HashMap<>();
    Map<String, String> 五行能量 = new HashMap<>();
    Map<String, String> 天干属性 = new HashMap<>();
    Map<String, String> 地支属性 = new HashMap<>();

    String         命主;
    String         命主描述;
    String         命局;
    String         命局描述;
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
        命主 = pan.zhu.getName();
        命局 = bazi.getPattern().getName();

        命主描述 = Descriptor.describe(pan.zhu);
        命局描述 = Descriptor.describe(pan.pattern);

        for (生克 xing : 生克.values()) {
            五行关系.put(xing.getName(), bazi.getMing().getWuXing().getByShengKe(xing).getName());
        }
        for (十神 shen : 十神.values()) {
            十神属性.put(shen.getName(),      shen.getWuXing(bazi.getMing()).getName());
            十神属性.put(shen.getShortName(), shen.getWuXing(bazi.getMing()).getName());
            十神描述.put(shen.getName(),      Descriptor.describe(shen));
        }
        for (天干 gan  : com.zhinan.zhouyi.base.天干.values()) {
            天干属性.put(gan.getName(), gan.getWuXing().getName());
        }
        for (地支 zhi  : com.zhinan.zhouyi.base.地支.values()) {
            地支属性.put(zhi.getName(), zhi.getWuXing().getName());
        }


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
            空亡.add(pan.emptyList.get(i)[0].getName() + pan.emptyList.get(i)[1].getName());
            纳音.add(pan.soundList.get(i).getName());
            星运.add(pan.starStatusList.get(i).getName());
            自坐.add(pan.selfStatusList.get(i).getName());
        }

        起运年岁 = pan.luckAge;
        起运日期 = pan.luckDate.format(java.time.format.DateTimeFormatter.ofPattern("yyyy年MM月dd日"));

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
