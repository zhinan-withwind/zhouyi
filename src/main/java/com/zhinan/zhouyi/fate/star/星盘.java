package com.zhinan.zhouyi.fate.star;

import com.zhinan.zhouyi.base.地支;
import com.zhinan.zhouyi.base.天干;
import com.zhinan.zhouyi.base.干支;
import com.zhinan.zhouyi.base.阴阳;
import com.zhinan.zhouyi.fate.bazi.八字;
import lombok.Getter;
import run.zhinan.time.ganzhi.GanZhi;
import run.zhinan.time.ganzhi.GanZhiDate;
import run.zhinan.time.ganzhi.GanZhiDateTime;
import run.zhinan.time.ganzhi.GanZhiYear;
import run.zhinan.time.lunar.LunarDateTime;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Getter
public class 星盘 {
    enum 类型 {
        原命, 大限, 流年, 流月, 流日, 流时;

        public static 类型 getByValue(int value) {
            return values()[value - 1];
        }
    }

    类型 type;
    LunarDateTime birthday;
    GanZhiDateTime ganZhiDateTime;
    命局 pattern;
    八字 bazi;
    星曜 fateMaster;
    星曜 bodyMaster;
    int bodyPalace;
    int fatePalace;
    List<宫位> starPalaceList;
    List<化位> changeList;

    /**
     * 排星盘
     * @param birthday  命主的阳历生日
     * @param sex       命主的性别
     * @return          排好的星盘
     */
    public static 星盘 of(LocalDateTime birthday, int sex) {
        星盘 pan  = new 星盘();
        pan.type = 类型.原命;
        pan.starPalaceList = new ArrayList<>();

        // 计算时间及八字
        LunarDateTime  lunarTime  = LunarDateTime .of(birthday);
        GanZhiDateTime ganzhiTime = GanZhiDateTime.of(birthday);
        pan.ganZhiDateTime = GanZhiDateTime.of(GanZhi.toGanZhi(lunarTime.getYear()), ganzhiTime.getGanZhiMonth(),
                ganzhiTime.getGanZhiDay(), ganzhiTime.getGanZhiTime());
        pan.birthday = lunarTime;
        pan.bazi = 八字.of(birthday, sex);

        int year  = lunarTime.getYear();
        int month = lunarTime.toLunarDate().getLunarMonth().isLeap() ?
                    lunarTime.toLunarDate().getLunarMonth().getIndex() : lunarTime.getMonth();
        int hour  = lunarTime.getTime();

        int startGan = (GanZhiYear.of(year).getGan().getValue() * 2) % 10;

        pan.fatePalace = (month - 1 - hour + 12) % 12;
        pan.bodyPalace = (month - 1 + hour + 12) % 12;

        pan.pattern = 命局.getByWuXing(
                new 干支(天干.getByValue((startGan + pan.fatePalace) % 10), 地支.getByValue(pan.fatePalace + 2))
                        .getSound().getWuXing()
        );

        // 安 12 宫 宫位
        for (int i = 0; i < 12; i++) {
            宫位 palace = new 宫位(天干.getByValue((startGan + i) % 10), 地支.getByValue(i + 2));
            palace.palace = 星宫.getByValue((12 + pan.fatePalace - i) % 12);
            int direction = 干支.of(ganzhiTime.getGanZhiYear()).getGan().getYinYang().equals(阴阳.getByValue(sex)) ? 1 : -1;
            palace.startAge = ((12 - (pan.fatePalace - i) * direction) % 12) * 10 + pan.pattern.value;
            palace.endAge = palace.startAge + 9;
            pan.starPalaceList.add(palace);
        }

        // 安所有星的星位
        List<星位> stars = 星位.of(lunarTime, pan.pattern);
        for (星位 star : stars) {
            pan.starPalaceList.get((star.getPosition() + 10) % 12).getStars().add(star);
        }

        // 安四化星位置
        pan.changeList = 化位.of(GanZhiYear.of(year).getGan().getValue() - 1);

        pan.fateMaster = 命主[pan.starPalaceList.get(pan.fatePalace).getZhi().getValue()];
        pan.bodyMaster = 身主[pan.bazi.getYear().getZhi().getValue()];

        return pan;
    }

    private static final 星曜[] 命主 = {
            // 子宫    丑宫      寅宫      卯宫      辰宫       巳宫
            星曜.贪狼, 星曜.巨门, 星曜.禄存, 星曜.文曲, 星曜.廉贞, 星曜.武曲,
            // 午宫    未宫      申宫      酉宫      戌宫       亥宫
            星曜.破军, 星曜.武曲, 星曜.廉贞, 星曜.文曲, 星曜.禄存, 星曜.巨门,
    };

    private static final 星曜[] 身主 = {
            // 子宫    丑宫      寅宫      卯宫      辰宫       巳宫
            星曜.火星, 星曜.天相, 星曜.天梁, 星曜.天同, 星曜.文昌, 星曜.天机,
            // 午宫    未宫      申宫      酉宫      戌宫       亥宫
            星曜.火星, 星曜.天相, 星曜.天梁, 星曜.天同, 星曜.文昌, 星曜.天机,
    };
}
