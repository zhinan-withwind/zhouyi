package com.zhinan.zhouyi.divine.liuyao;

public enum 六亲 {
    父母, 兄弟, 子孙, 妻财, 官鬼;

    public int getValue() {
        return ordinal();
    }

    public String getName() {
        return name();
    }

    public static 六亲 getByValue(int value) {
        return values()[value];
    }
}
