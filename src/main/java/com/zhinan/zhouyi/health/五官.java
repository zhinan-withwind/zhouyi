package com.zhinan.zhouyi.health;

import com.zhinan.zhouyi.base.五行;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum 五官 {
    眼(五行.木),
    耳(五行.水),
    鼻(五行.金),
    舌(五行.火),
    口(五行.土);

    五行 wuXing;

}
