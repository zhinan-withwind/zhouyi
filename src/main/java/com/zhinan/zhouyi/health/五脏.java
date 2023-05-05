package com.zhinan.zhouyi.health;

import com.zhinan.zhouyi.base.五行;
import com.zhinan.zhouyi.base.地支;
import com.zhinan.zhouyi.base.天干;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum 五脏 {
    心(五行.火, 天干.丁, 地支.午),
    肝(五行.木, 天干.甲, 地支.卯),
    脾(五行.土, 天干.己, 地支.丑),
    肺(五行.金, 天干.辛, 地支.申),
    肾(五行.水, 天干.癸, 地支.亥);

    五行 wuXing;
    天干 gan;
    地支 zhi;

    public static 五脏 getByWuXing(五行 wuXing) {
        五脏 result = null;
        for (五脏 viscus : values()) {
            if (viscus.wuXing.equals(wuXing)) {
                result = viscus;
                break;
            }
        }
        return result;
    }

    public int getValue() { return ordinal(); }
    public String getName() { return name(); }
}
