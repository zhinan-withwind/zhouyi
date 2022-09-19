package com.zhinan.zhouyi.energy;

import com.zhinan.zhouyi.base.生克;
import com.zhinan.zhouyi.divine.meihua.吉凶;

public enum 旺衰 {
    旺, 相, 休, 囚, 死;

    private final static 旺衰[] valueByShengKe = {休, 旺, 相, 死, 囚};

    public int getValue() {
        return ordinal();
    }

    public String getName() {
        return name();
    }

    public static 旺衰 getByValue(int value) {
        return values()[value];
    }

    public static 旺衰 getByShengKe(生克 shengKe) {
        return valueByShengKe[shengKe.getValue()];
    }
}
