package com.zhinan.zhouyi.base;

public enum 地支 {
    子(阴阳.阳, 五行.水),
    丑(阴阳.阴, 五行.土),
    寅(阴阳.阳, 五行.木),
    卯(阴阳.阴, 五行.木),
    辰(阴阳.阳, 五行.土),
    巳(阴阳.阴, 五行.火),
    午(阴阳.阳, 五行.火),
    未(阴阳.阴, 五行.土),
    申(阴阳.阳, 五行.金),
    酉(阴阳.阴, 五行.金),
    戌(阴阳.阳, 五行.土),
    亥(阴阳.阴, 五行.水);

    private final 阴阳 yinYang;
    private final 五行 wuXing;

    地支(阴阳 yinYang, 五行 wuXing) {
        this.yinYang = yinYang;
        this.wuXing = wuXing;
    }

    public 阴阳 getYinYang() {
        return yinYang;
    }

    public 五行 getWuXing() {
        return wuXing;
    }
}
