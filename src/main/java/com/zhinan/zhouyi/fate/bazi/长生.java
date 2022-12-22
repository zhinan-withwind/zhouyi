package com.zhinan.zhouyi.fate.bazi;

import com.zhinan.zhouyi.base.地支;
import com.zhinan.zhouyi.base.天干;

public enum 长生 {
    长生, 沐浴, 冠带, 临官, 帝旺, 衰, 病, 死, 墓, 绝, 胎, 养;

    public final static 地支[] growValues = {地支.亥, 地支.午, 地支.寅, 地支.酉, 地支.寅, 地支.酉, 地支.巳, 地支.子, 地支.申, 地支.卯};

    public static 长生 getByValue(int value) {
        return values()[value];
    }

    public int getValue() {return ordinal();}

    public String getName() {return name();}

    public static 地支 getGrow(天干 gan) {
        return growValues[gan.getValue()];
    }
}
