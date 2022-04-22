package com.zhinan.zhouyi.base;

import com.zhinan.zhouyi.fate.纳音;
import com.zhinan.zhouyi.fate.长生;

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
        return ((6 - ((zhi.getValue() + 12 - gan.getValue()) % 12) / 2) % 6) * 10 + gan.getValue();
    }

    public 干支 roll(int i) {
        return getByValue(getValue() + i + 60);
    }

    public 地支[] getEmpty() {
        int e = (10 - gan.getValue() + zhi.getValue()) % 12;
        return new 地支[] {地支.getByValue(e), 地支.getByValue(e+1)};
    }

    public 纳音 getSound() {
        return 纳音.getByValue(getValue() / 2);
    }

    public 长生 getStatus() {
        return 长生.getByValue((长生.growValues[gan.getValue()].getValue()
                + (gan.getYinYang().isYang() ? 1 : -1) * (zhi.getValue() - 长生.growValues[gan.getValue()].getValue()) + 12) % 12);
    }

    @Override
    public String toString() {
        return gan.getName() + zhi.getName();
    }
}
