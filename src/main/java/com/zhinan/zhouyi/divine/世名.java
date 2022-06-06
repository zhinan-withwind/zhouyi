package com.zhinan.zhouyi.divine;

public enum 世名 {
    主卦, 一世, 二世, 三世, 四世, 五世, 游魂, 归魂;

    public static 世名 getByValue(int value) {
        return values()[value];
    }
}
