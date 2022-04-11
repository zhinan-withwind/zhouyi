package com.zhinan.zhouyi.base;

public enum 天干 {
    甲(阴阳.阳, 五行.木),
    乙(阴阳.阴, 五行.木),
    丙(阴阳.阳, 五行.火),
    丁(阴阳.阴, 五行.火),
    戊(阴阳.阳, 五行.土),
    己(阴阳.阴, 五行.土),
    庚(阴阳.阳, 五行.金),
    辛(阴阳.阴, 五行.金),
    壬(阴阳.阳, 五行.水),
    癸(阴阳.阴, 五行.水);

    private final 阴阳 yinYang;
    private final 五行 wuXing;

    天干(阴阳 yinyang, 五行 wuxing) {
        this.yinYang = yinyang;
        this.wuXing = wuxing;
    }

    public 阴阳 getYinYang() {
        return yinYang;
    }

    public 五行 getWuXing() {
        return wuXing;
    }

    public int getValue() {
        return ordinal();
    }

    public String getName() {
        return name();
    }

    public static 天干 getByValue(int value) {
        return values()[value];
    }
}
