package com.zhinan.zhouyi.base;

public enum 生克 {
    生, 同, 泄, 耗, 克;

    public static 生克[] results = {同, 泄, 耗, 克, 生};

    public static 生克 getByValue(int value) {
        return values()[value];
    }

    public int getValue() {return ordinal();}

    public String getName() {return name();}

    public 五行 getWuXing(天干 ming) {
        return 五行.getByValue((ming.getWuXing().getValue() + getValue() - 1 + 4) % 5);
    }
}
