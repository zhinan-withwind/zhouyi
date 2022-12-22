package com.zhinan.zhouyi.fate.util;

import com.zhinan.zhouyi.base.五行;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Getter
@AllArgsConstructor
public enum 风水物 {
    竹雕(五行.木), 花草盆景(五行.木), 翡翠虎(五行.木), 翡翠兔(五行.木),
    朱砂(五行.火), 红木马(五行.火), 红木蛇(五行.火),
    玉石(五行.土), 陶瓷牛(五行.土), 陶瓷羊(五行.土), 陶瓷狗(五行.土), 陶瓷龙(五行.土),
    金银(五行.金), 首饰(五行.金), 金属鸡(五行.金), 金属鸟(五行.金), 金属猴(五行.金),
    鱼缸(五行.水), 流水景(五行.水), 水晶鱼(五行.水), 水晶猪(五行.水), 水晶鼠(五行.水);

    五行 wuXing;

    public int getValue() {return ordinal();}

    public String getName() {return name();}

    public static List<风水物> getByWuXing(五行 wuXing) {
        return Arrays.stream(values()).filter(o -> wuXing.equals(o.getWuXing())).collect(Collectors.toList());
    }
}
