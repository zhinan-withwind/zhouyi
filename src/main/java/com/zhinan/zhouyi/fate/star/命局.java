package com.zhinan.zhouyi.fate.star;

import com.zhinan.zhouyi.base.五行;
import lombok.AllArgsConstructor;

@AllArgsConstructor
public enum 命局 {
    水二(五行.水, 2), 木三(五行.木,3), 金四(五行.金, 4), 土五(五行.土,5), 火六(五行.火, 6);

    五行 wuXing;
    int value;

    public static 命局 getByWuXing(五行 wuXing) {
        命局 result = null;
        for (命局 ju : values()) {
            if (ju.wuXing.equals(wuXing)) {
                result = ju;
                break;
            }
        }
        return result;
    }
}
