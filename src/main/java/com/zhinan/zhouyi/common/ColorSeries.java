package com.zhinan.zhouyi.common;

import com.zhinan.zhouyi.base.五行;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum ColorSeries {
    绿(五行.木),
    红(五行.火),
    黄(五行.土),
    白(五行.金),
    黑(五行.水);

    五行 wuXing;

    public int getValue() {return ordinal();}

    public String getName() {return name();}

    public static ColorSeries getByWuXing(五行 wuXing) {
        return values()[wuXing.getValue()];
    }
}
