package com.zhinan.zhouyi.divine.qimen;

import com.zhinan.zhouyi.base.地支;
import com.zhinan.zhouyi.base.天干;
import lombok.AllArgsConstructor;

@AllArgsConstructor
public enum 六仪 {
    甲子戊(天干.戊, 地支.子), 甲戌己(天干.己, 地支.戌), 甲申庚(天干.庚, 地支.申),
    甲午辛(天干.辛, 地支.午), 甲辰壬(天干.壬, 地支.辰), 甲寅癸(天干.癸, 地支.寅);

    天干 gan;
    地支 zhi;

    public int   getValue() { return ordinal(); }
    public String getName() { return name(); }

    public static 六仪 getByValue(int value) {
        return values()[value];
    }

    public static 六仪 getByGan(天干 gan) {
        return getByValue(gan.getValue() - 天干.戊.getValue());
    }

    public static 六仪 getByZhi(地支 zhi) {
        return getByValue((6 - zhi.getValue() / 2) % 6);
    }
}
