package com.zhinan.zhouyi.divine.meihua;

public enum 吉凶 {
    大吉, 中吉, 小凶, 小吉, 大凶;

    public int getValue() {
        return ordinal();
    }

    public String getName() {
        return name();
    }

    public static 吉凶 getByValue(int value) {
        return values()[value];
    }
}
