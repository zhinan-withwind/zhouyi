package com.zhinan.zhouyi.health;

import com.zhinan.zhouyi.base.五行;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum 五色 {
    绿(五行.木),
    红(五行.火),
    黄(五行.土),
    白(五行.金),
    黑(五行.水);

    五行 wuXing;
}
