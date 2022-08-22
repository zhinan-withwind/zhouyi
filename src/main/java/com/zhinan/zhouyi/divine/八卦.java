package com.zhinan.zhouyi.divine;

import com.zhinan.zhouyi.base.五行;
import com.zhinan.zhouyi.base.阴阳;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum 八卦 {
    乾("111", "天", 1, 6, 五行.金, 阴阳.阳),
    兑("110", "泽", 2, 7, 五行.金, 阴阳.阴),
    离("101", "火", 3, 9, 五行.火, 阴阳.阴),
    震("100", "雷", 4, 3, 五行.木, 阴阳.阳),
    巽("011", "风", 5, 4, 五行.木, 阴阳.阴),
    坎("010", "水", 6, 1, 五行.水, 阴阳.阳),
    艮("001", "山", 7, 8, 五行.土, 阴阳.阳),
    坤("000", "地", 8, 2, 五行.土, 阴阳.阴);

    String code;
    String symbolize;
    int    initValue;
    int    alterValue;

    五行 wuXing;
    阴阳 yinYang;

    private static final 八卦[] initValues  = {乾, 兑, 离, 震, 巽, 坎, 艮, 坤};
    private static final 八卦[] alterValues = {坎, 坤, 震, 巽, 坤, 乾, 兑, 艮, 离};

    public int getValue() {
        return ordinal() + 1;
    }

    public String getName() {
        return name();
    }

    public 阴阳 getYao(int i) {
        return 阴阳.getByValue(Integer.parseInt(String.valueOf(code.charAt(i - 1))));
    }

    public static 八卦 getByName(String name) {
        return valueOf(name);
    }

    public static 八卦 getBySymbolize(String symbolize) {
        八卦 result = null;
        for (八卦 value : values()) {
            if (value.symbolize.equals(symbolize)) {
                result = value;
                break;
            }
        }
        return result;
    }

    public static 八卦 getByInitValue (Integer value) {
        return value == null ? null :  initValues[value - 1];
    }

    public static 八卦 getByAlterValue(Integer value) { return value == null ? null : alterValues[value - 1]; }
}
