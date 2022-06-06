package com.zhinan.zhouyi.divine.liuyao;

public enum 位置 {
    初爻, 二爻, 三爻, 四爻, 五爻, 上爻, 伏爻;

    public int getValue() {
        return ordinal();
    }

    public String getName() {
        return name();
    }

    public static 位置 getByValue(int value) {
        return values()[value];
    }
}
