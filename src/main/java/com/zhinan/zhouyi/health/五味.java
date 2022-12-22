package com.zhinan.zhouyi.health;

import com.zhinan.zhouyi.base.五行;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum 五味 {
    酸(五行.木),
    苦(五行.火),
    甘(五行.土),
    辛(五行.金),
    咸(五行.水);

    五行 wuXing;
}
