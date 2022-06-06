package com.zhinan.zhouyi.energy;

import com.zhinan.zhouyi.base.干支;

public enum 禄根 {
    甲寅, 乙卯, 丙巳, 丁午, 戊巳, 己午, 庚申, 辛酉, 壬亥, 癸子;

    public static boolean is(干支 ganzhi) {
        boolean result = false;
        for (禄根 gen : values()) {
            if (gen.name().equals(ganzhi.getName())) {
                result = true;
                break;
            }
        }
        return result;
    }
}
