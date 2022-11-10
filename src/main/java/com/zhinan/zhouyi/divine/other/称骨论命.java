package com.zhinan.zhouyi.divine.other;

import com.zhinan.zhouyi.date.GanZhiDateTime;
import com.zhinan.zhouyi.date.LunarDateTime;

import java.time.LocalDateTime;

public class 称骨论命 {
    private final static int[] 年重 = {
            12, 9, 6, 7,12, 5, 9, 8, 7, 8,
            15, 9,16, 8, 8,19,12, 6, 8, 7,
             5,15, 6,16,15, 7, 9,12, 1, 7,
            15, 6, 5,14,14, 9, 7, 7, 9,12,
             8, 7,13, 5,14, 5, 9,17, 5, 7,
            12, 8, 8, 6,19, 6, 8,16, 1, 7,
    };

    private final static int[] 月重 = {
            6, 7, 18, 9, 5, 16, 9, 15, 18, 8, 9, 5
    };

    private final static int[] 日重 = {
             5,10, 8,15,16,15, 8,16, 8,16,
             9,17, 8,17, 1, 8, 9,18, 5,15,
            10, 9, 8, 9,15,18, 7, 8,16, 6
    };

    private final static int[] 时重 = {
            16, 6, 7, 10, 9, 16,
            10, 8, 8,  9, 6,  6
    };

    public static int calculate(LocalDateTime birthday) {
        LunarDateTime  lunarDateTime = LunarDateTime.of(birthday);
        GanZhiDateTime ganZhiDateTime = GanZhiDateTime.of(birthday);
        return 年重[ganZhiDateTime.getGanZhiYear().getValue()]
                + 月重[lunarDateTime.getMonth()]
                + 日重[lunarDateTime.getDay()]
                + 时重[lunarDateTime.getTime().getValue()];
    }
}
