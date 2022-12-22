package com.zhinan.zhouyi.health;

import com.zhinan.zhouyi.base.五行;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum 五体 {
    筋(五行.木),
    脉(五行.火),
    肉(五行.土),
    骨(五行.水),
    皮(五行.金);

    五行 wuXing;
}
