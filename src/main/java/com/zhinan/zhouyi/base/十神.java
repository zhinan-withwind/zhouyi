package com.zhinan.zhouyi.base;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum 十神 {
    正印("印", 阴阳.阳, 生克.生),
    偏印("枭", 阴阳.阴, 生克.生),
    劫财("劫", 阴阳.阳, 生克.同),
    比肩("比", 阴阳.阴, 生克.同),
    伤官("伤", 阴阳.阳, 生克.泄),
    食神("食", 阴阳.阴, 生克.泄),
    正财("财", 阴阳.阳, 生克.耗),
    偏财("才", 阴阳.阴, 生克.耗),
    正官("官", 阴阳.阳, 生克.克),
    七杀("杀", 阴阳.阴, 生克.克);

    public static 十神[] results = {偏印, 正印, 比肩, 劫财, 食神, 伤官, 偏财, 正财, 七杀, 正官};

    String shortName;
    阴阳    yinYang;
    生克    shengKe;

    public int getValue() {return ordinal();}

    public String getName() {return name();}

    public 五行 getWuXing(天干 ming) {
        return 五行.getByValue((ming.getWuXing().getValue() + getShengKe().getValue() - 1 + 5) % 5);
    }

    public static 十神 getShen(阴阳 yinYang, 生克 shengKe) {
        十神 result = null;
        for (十神 shen : values()) {
            if (yinYang.equals(shen.getYinYang()) && shengKe.equals(shen.getShengKe())) {
                result = shen;
            }
        }
        return result;
    }
}
