package com.zhinan.zhouyi.base;

public enum 五行 {
    木, 火, 土, 金, 水;

    public static 五行 getByValue(int value) {
        return values()[value];
    }

    public int getValue() {return ordinal();}

    public String getName() {return name();}

    public 生克 compare(五行 other) {
        return 生克.results[(other.ordinal() - this.ordinal() + 5) % 5];
    }

    public 五行 getByShengKe(生克 shengKe) {
        return getByValue((getValue() + shengKe.getValue() - 1 + 5) % 5);
    }
}
