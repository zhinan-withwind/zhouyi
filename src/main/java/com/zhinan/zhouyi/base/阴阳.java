package com.zhinan.zhouyi.base;

public enum 阴阳 {
    阴, 阳;

    public static 阴阳 getByValue(int value) {
        return values()[value];
    }

    public boolean isYang() {
        return this.equals(阳);
    }

    public boolean isYin() {
        return !isYang();
    }

    public int getValue() {
        return ordinal();
    }

    public String getName() {
        return name();
    }

    public 阴阳 inverse() {
        return getByValue(1 - getValue());
    }

    public 阴阳 compare(阴阳 other) {
        return getByValue(Math.abs(this.getValue() - other.getValue()));
    }
}
