package com.zhinan.zhouyi.base;

import lombok.AllArgsConstructor;
import lombok.Getter;
import run.zhinan.time.ganzhi.Gan;

@Getter
@AllArgsConstructor
public enum 天干 implements 元素 {
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

    public int getValue() {
        return ordinal();
    }

    public String getName() {
        return name();
    }

    public String getFullName() {return this.getName() + getWuXing().getName();}

    public 十神 compare(天干 other) {
        return 十神.results[this.getWuXing().compare(other.getWuXing()).getValue() * 2
                         + this.getYinYang().compare(other.getYinYang()).getValue()];
    }

    public static 天干 getByValue(int value) {
        return values()[value % 10];
    }

    public static 天干 getByName(String name) { return valueOf(name); }

    public static 天干 getByYYWX(阴阳 yinYang, 五行 wuXing) {
        天干 result = null;
        for (天干 gan : values()) {
            if (yinYang.equals(gan.getYinYang()) && wuXing.equals(gan.getWuXing())) {
                result = gan;
            }
        }
        return result;
    }

    public static 天干 fromGan(Gan gan) {
        return getByValue(gan.getValue() - 1);
    }

    @Override
    public String toString() {
        return getFullName();
    }

}
