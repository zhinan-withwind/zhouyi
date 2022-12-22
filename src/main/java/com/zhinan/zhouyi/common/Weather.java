package com.zhinan.zhouyi.common;

import com.zhinan.zhouyi.base.五行;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum Weather {
    春(五行.木), 夏(五行.火), 四(五行.土), 秋(五行.金), 冬(五行.水);

    五行 wuXing;

    public int getValue() { return ordinal(); }
    public String getName() { return name(); }

    public static Weather getByWuXing(五行 wuXing) {
        Weather result = null;
        for (Weather w : Weather.values()) {
            if (w.wuXing.equals(wuXing)) {
                result = w;
                break;
            }
        }
        return result;
    }
}
