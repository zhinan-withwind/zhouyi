package com.zhinan.zhouyi.divine.other;

import com.zhinan.zhouyi.date.GanZhiDateTime;
import com.zhinan.zhouyi.date.LunarDateTime;
import com.zhinan.zhouyi.fate.bazi.八字;
import lombok.Getter;

import java.time.LocalDateTime;
import java.util.List;
@Getter
public class 称骨 {
    LocalDateTime  birthday;
    GanZhiDateTime ganZhiDateTime;
    LunarDateTime  lunarDateTime;
    int year, month, day, time;
    int weight;
    int sex;

    public static 称骨 of(八字 bazi) {
        return of(bazi.getBirthday(), bazi.getSex().getValue());
    }

    public static 称骨 of(LocalDateTime birthday, int sex) {
        称骨 weight = new 称骨();
        weight.birthday = birthday;
        weight.sex      = sex;
        weight.ganZhiDateTime = GanZhiDateTime.of(birthday);
        weight.lunarDateTime  = LunarDateTime.of(birthday);

        weight.year  = 年重[weight.ganZhiDateTime.getGanZhiYear().getValue()];
        weight.month = 月重[weight.lunarDateTime.getMonth() - 1];
        weight.day   = 日重[weight.lunarDateTime.getDay()   - 1];
        weight.time  = 时重[weight.lunarDateTime.getTime().getValue()];
        weight.weight = weight.year + weight.month + weight.day + weight.time;

        return weight;
    }

    private final static int[] 年重 = {
            12, 9, 6, 7,12, 5, 9, 8, 7, 8,
            15, 9,16, 8, 8,19,12, 6, 8, 7,
            5,15, 6,16,15, 7, 9,12,10, 7,
            15, 6, 5,14,14, 9, 7, 7, 9,12,
            8, 7,13, 5,14, 5, 9,17, 5, 7,
            12, 8, 8, 6,19, 6, 8,16,10, 7,
    };

    private final static int[] 月重 = {
            6, 7, 18, 9, 5, 16, 9, 15, 18, 8, 9, 5
    };

    private final static int[] 日重 = {
            5,10, 8,15,16,15, 8,16, 8,16,
            9,17, 8,17,10, 8, 9,18, 5,15,
            10, 9, 8, 9,15,18, 7, 8,16, 6
    };

    private final static int[] 时重 = {
            16, 6, 7, 10, 9, 16,
            10, 8, 8,  9, 6,  6
    };
}
