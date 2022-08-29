package com.zhinan.zhouyi.divine.liuyao;

import com.zhinan.zhouyi.divine.common.六十四卦;

public enum 六冲 {
    乾为天, 兑为泽, 离为火, 震为雷, 巽为风, 坎为水, 艮为山, 坤为地, 天雷无妄, 雷天大壮;

    public static boolean is(六十四卦 gua) {
        boolean result = false;
        for (六冲 value : values()) {
            if (value.name().equals(gua.getName())) {
                result = true;
                break;
            }
        }
        return result;
    }
}
