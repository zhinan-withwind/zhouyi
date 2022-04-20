package com.zhinan.zhouyi.base;

public enum 地支 {
    子(阴阳.阳, 五行.水, new 天干[]{天干.癸}),
    丑(阴阳.阴, 五行.土, new 天干[]{天干.己, 天干.癸, 天干.辛}),
    寅(阴阳.阳, 五行.木, new 天干[]{天干.甲, 天干.戊, 天干.丙}),
    卯(阴阳.阴, 五行.木, new 天干[]{天干.乙}),
    辰(阴阳.阳, 五行.土, new 天干[]{天干.戊, 天干.乙, 天干.癸}),
    巳(阴阳.阴, 五行.火, new 天干[]{天干.丙, 天干.戊, 天干.庚}),
    午(阴阳.阳, 五行.火, new 天干[]{天干.丁, 天干.己}),
    未(阴阳.阴, 五行.土, new 天干[]{天干.己, 天干.丁, 天干.乙}),
    申(阴阳.阳, 五行.金, new 天干[]{天干.庚, 天干.戊, 天干.壬}),
    酉(阴阳.阴, 五行.金, new 天干[]{天干.辛}),
    戌(阴阳.阳, 五行.土, new 天干[]{天干.戊, 天干.辛, 天干.丁}),
    亥(阴阳.阴, 五行.水, new 天干[]{天干.壬, 天干.甲});

    private final 阴阳 yinYang;
    private final 五行 wuXing;
    private final 天干[] hiddenGan;

    地支(阴阳 yinYang, 五行 wuXing, 天干[] hiddenGan) {
        this.yinYang   = yinYang;
        this.wuXing    = wuXing;
        this.hiddenGan = hiddenGan;
    }

    public static 地支 getByValue(int value) {
        return values()[value % 12];
    }

    public 阴阳 getYinYang() {
        return yinYang;
    }

    public 五行 getWuXing() {
        return wuXing;
    }

    public 天干[] getHiddenGan() {return hiddenGan;}

    public 天干 getTianGan() {return hiddenGan[0];}

    public int getValue() {
        return ordinal();
    }

    public String getName() {
        return name();
    }


}
