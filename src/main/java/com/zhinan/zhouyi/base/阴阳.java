package com.zhinan.zhouyi.base;

public enum 阴阳 {
    阴, 阳;

    public static 阴阳 getByValue(int value) {
        return values()[value];
    }

    public boolean isMajor() {
        return this.equals(阳);
    }

    public boolean isMinor() {
        return !isMajor();
    }
}
