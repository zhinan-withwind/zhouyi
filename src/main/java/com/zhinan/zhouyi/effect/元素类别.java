package com.zhinan.zhouyi.effect;

public enum 元素类别 {
    天干, 地支, 藏干;

    public int getValue() {return ordinal();}

    public String getName() {return name();}

    public static 元素类别 getByValue(int value) {
        return values()[value];
    }
}
