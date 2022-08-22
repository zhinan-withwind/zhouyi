package com.zhinan.zhouyi.fate.star;

import com.zhinan.zhouyi.base.地支;
import com.zhinan.zhouyi.base.天干;
import com.zhinan.zhouyi.base.干支;
import com.zhinan.zhouyi.base.阴阳;
import com.zhinan.zhouyi.date.GanZhiDateTime;
import com.zhinan.zhouyi.date.LunarDateTime;
import com.zhinan.zhouyi.fate.bazi.八字;
import lombok.Getter;

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
    命局 pattern;
    八字 bazi;
    int bodyPalace;
    int fatePalace;
    List<宫位> starPalaceList;
    List<化位> changeList;

    public static 星盘 of(LocalDateTime birthday, int sex) {
        星盘 pan  = new 星盘();
        pan.type = 类型.原命;
        pan.starPalaceList = new ArrayList<>();

        LunarDateTime  lunarTime  = LunarDateTime.from(birthday);
        GanZhiDateTime ganzhiTime = GanZhiDateTime.from(birthday);
        pan.birthday = lunarTime;
        pan.bazi = 八字.of(birthday, sex);

        int month = lunarTime.getMonth();
        int hour  = lunarTime.getTime().getValue();

        int startGan = ((ganzhiTime.getGanZhiYear().getGan().getValue() + 1) * 2) % 10;

        pan.fatePalace = (month - 1 - hour + 12) % 12;
        pan.bodyPalace = (month - 1 + hour) % 12;

        pan.pattern = 命局.getByWuXing(
                new 干支(天干.getByValue((startGan + pan.fatePalace) % 10), 地支.getByValue(pan.fatePalace + 2))
                        .getSound().getWuXing()
        );

        for (int i = 0; i < 12; i++) {
            宫位 palace = new 宫位(天干.getByValue((startGan + i) % 10), 地支.getByValue(i + 2));
            palace.palace = 星宫.getByValue((12 + pan.fatePalace - i) % 12);
            int direction = ganzhiTime.getGanZhiYear().getGan().getYinYang().equals(阴阳.getByValue(sex)) ? 1 : -1;
            palace.startAge = ((12 - (pan.fatePalace - i) * direction) % 12) * 10 + pan.pattern.value;
            palace.endAge = palace.startAge + 9;
            pan.starPalaceList.add(palace);
        }

        List<星位> stars = 星位.of(lunarTime, pan.pattern);
        for (星位 star : stars) {
            pan.starPalaceList.get((star.getPosition() + 10) % 12).getStars().add(star);
        }

        pan.changeList = 化位.of(ganzhiTime.getGanZhiYear().getGan().getValue());
//        for (化位 change : pan.changeList) {
//            change.position = (stars.get(change.position).position - 2 + 12) % 12;
//        }

        return pan;
    }

    public 星曜 getFateMaster() {
        return 命主[getStarPalaceList().get(getFatePalace()).getZhi().getValue()];
    }

    public 星曜 getBodyMaster() {
        return 身主[bazi.getYear().getZhi().getValue()];
    }

    private static final 星曜[] 命主 = {
            星曜.贪狼, 星曜.巨门, 星曜.禄存, 星曜.文曲, 星曜.廉贞, 星曜.武曲,
            星曜.破军, 星曜.武曲, 星曜.廉贞, 星曜.文曲, 星曜.禄存, 星曜.巨门,
    };

    private static final 星曜[] 身主 = {
            星曜.火星, 星曜.天相, 星曜.天梁, 星曜.天同, 星曜.天机, 星曜.文昌,
            星曜.火星, 星曜.天相, 星曜.天梁, 星曜.天同, 星曜.天机, 星曜.文昌,
    };
}
