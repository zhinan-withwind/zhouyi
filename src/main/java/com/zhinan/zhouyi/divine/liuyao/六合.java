package com.zhinan.zhouyi.divine.liuyao;

import com.zhinan.zhouyi.divine.common.六十四卦;

public enum 六合 {
    天地否, 地天泰, 地雷复, 雷地豫, 火山旅, 山火贲, 泽水困, 水泽节;

    public static boolean is(六十四卦 gua) {
        boolean result = false;
        for (六合 value : values()) {
            if (value.name().equals(gua.getName())) {
                result = true;
                break;
            }
        }
        return result;
    }
}
