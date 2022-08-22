package com.zhinan.zhouyi.divine.qimen;

import com.zhinan.zhouyi.base.五行;
import com.zhinan.zhouyi.base.阴阳;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum 九星 {
    天蓬(阴阳.阳, 五行.水), 天任(阴阳.阳, 五行.土), 天冲(阴阳.阳, 五行.木), 天辅(阴阳.阳, 五行.木),
    天英(阴阳.阴, 五行.火), 天芮(阴阳.阴, 五行.土), 天柱(阴阳.阴, 五行.金), 天心(阴阳.阴, 五行.金), 天禽(阴阳.阳, 五行.土);

    阴阳 yinYang;
    五行 wuXing;

    public int getValue() {
        return ordinal();
    }

    public String getName() {
        return name();
    }

    public String getFullName() {
        return getName() + "星";
    }

    public static 九星 getByValue(int value) {
        return values()[value % 8];
    }
}
