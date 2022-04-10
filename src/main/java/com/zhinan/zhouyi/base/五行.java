package com.zhinan.zhouyi.base;

public enum 五行 {
    木, 火, 土, 金, 水;

    public static 五行 getByValue(int value) {
        return values()[value];
    }
}
