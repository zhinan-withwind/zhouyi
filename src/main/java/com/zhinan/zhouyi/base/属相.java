package com.zhinan.zhouyi.base;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum 属相 implements 元素 {
    鼠(地支.子, "🐭"),
    牛(地支.丑, "🐂"),
    虎(地支.寅, "🐯"),
    兔(地支.卯, "🐰"),
    龙(地支.辰, "🐲"),
    蛇(地支.巳, "🐍"),
    马(地支.午, "🐎"),
    羊(地支.未, "🐑"),
    猴(地支.申, "🐒"),
    鸡(地支.酉, "🐔"),
    狗(地支.戌, "🐶"),
    猪(地支.亥, "🐷");

    地支 zhi;
    String symbol;

    public static 属相 getByValue(int value) {
        return values()[value];
    }

    public static 属相 getByName(String name) {
        return valueOf(name);
    }

    @Override
    public int getValue() {return zhi.getValue();}

    @Override
    public String getName() {return name();}

    @Override
    public 五行 getWuXing() {
        return zhi.getWuXing();
    }

    @Override
    public 阴阳 getYinYang() {
        return zhi.getYinYang();
    }
}
