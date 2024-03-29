package com.zhinan.zhouyi.fate.star;

public enum 四化 {
    化禄, 化权, 化科, 化忌;

    public int getValue() {
        return ordinal();
    }

    public String getName() {
        return name();
    }

    public static 四化 getByValue(int value) { return 四化.values()[value % 4]; }
}
