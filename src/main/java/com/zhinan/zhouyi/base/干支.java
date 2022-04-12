package com.zhinan.zhouyi.base;

public class 干支 {
    天干 gan;
    地支 zhi;

    public 干支(天干 gan, 地支 zhi) {
        this.gan = gan;
        this.zhi = zhi;
    }

    public static 干支 getByValue(int gan, int zhi) {
        return new 干支(天干.getByValue(gan), 地支.getByValue(zhi));
    }

    public static 干支 getByValue(int value) {
        return 干支.getByValue(value % 10, value % 12);
    }

    public 天干 getGan() {
        return gan;
    }

    public 地支 getZhi() {
        return zhi;
    }

    public int getValue() {
        return ((6 - (((zhi.getValue()+ 12 - gan.getValue()) % 12) / 2))  % 6) * 10 + gan.getValue();
    }

    public 干支 roll(int i) {
        return getByValue(getValue() + i + 60);
    }
}
