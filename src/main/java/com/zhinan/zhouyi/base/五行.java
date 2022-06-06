package com.zhinan.zhouyi.base;

/**
 * 五行元素
 */
public enum 五行 {
    木, 火, 土, 金, 水;

    public static 五行 getByValue(int value) {
        return values()[value];
    }

    public static 五行 getByName(String name) {
        return valueOf(name);
    }

    public int getValue() {return ordinal();}

    public String getName() {return name();}

    public 生克 compare(五行 other) {
        return 生克.results[(other.getValue() - this.getValue() + 5) % 5];
    }

    public 五行 getByShengKe(生克 shengKe) {
        return getByValue((getValue() + shengKe.getValue() - 1 + 5) % 5);
    }

    public 五行 get生() {
        return getByShengKe(生克.生);
    }

    public 五行 get同() {
        return getByShengKe(生克.同);
    }

    public 五行 get泄() {
        return getByShengKe(生克.泄);
    }

    public 五行 get耗() {
        return getByShengKe(生克.耗);
    }

    public 五行 get克() {
        return getByShengKe(生克.克);
    }

}
