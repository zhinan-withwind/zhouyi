package com.zhinan.zhouyi.fate.star;

public enum 星级 {
    主星, 辅星, 凶星, 煞星, 桃花;

    public int getValue() {
        return ordinal();
    }

    public String getName() {
        return name();
    }
}
