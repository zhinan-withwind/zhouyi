package com.zhinan.zhouyi.divine.liuyao;

import com.zhinan.zhouyi.base.五行;
import com.zhinan.zhouyi.base.阴阳;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum 六神 {
    青龙(五行.木, 阴阳.阳), 朱雀(五行.火, 阴阳.阳), 勾陈(五行.土, 阴阳.阳), 螣蛇(五行.土, 阴阳.阴), 白虎(五行.金, 阴阳.阳), 玄武(五行.水, 阴阳.阳);

    五行 wuXing;
    阴阳 yinYang;

    public int getValue() {
        return ordinal();
    }

    public String getName() {
        return name();
    }

    public static 六神 getByValue(int value) {
        return values()[value];
    }

}
