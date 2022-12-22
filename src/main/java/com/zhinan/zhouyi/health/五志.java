package com.zhinan.zhouyi.health;

import com.zhinan.zhouyi.base.五行;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum 五志 {
    怒(五行.木),
    喜(五行.火),
    思(五行.土),
    悲(五行.金),
    惊(五行.水);

    五行 wuXing;
}
