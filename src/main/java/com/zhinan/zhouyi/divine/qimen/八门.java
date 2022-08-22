package com.zhinan.zhouyi.divine.qimen;

import com.zhinan.zhouyi.base.五行;
import com.zhinan.zhouyi.base.阴阳;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum 八门 {
    休(阴阳.阳, 五行.水), 生(阴阳.阳, 五行.土), 伤(阴阳.阳, 五行.木), 杜(阴阳.阳, 五行.木),
    景(阴阳.阴, 五行.火), 死(阴阳.阴, 五行.土), 惊(阴阳.阴, 五行.金), 开(阴阳.阴, 五行.金);

    阴阳 yinYang;
    五行 wuXing;

    public int getValue() {
        return ordinal();
    }

    public String getName() {
        return name();
    }

    public String getFullName() {
        return getName() + "门";
    }

    public static 八门 getByValue(int value) {
        return values()[value % 8];
    }
}
