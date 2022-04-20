package com.zhinan.zhouyi.base;

public enum 十神 {
    正印("印", 阴阳.阳),
    偏印("枭", 阴阳.阴),
    劫财("劫", 阴阳.阳),
    比肩("比", 阴阳.阴),
    伤官("伤", 阴阳.阳),
    食神("食", 阴阳.阴),
    正财("财", 阴阳.阳),
    偏财("才", 阴阳.阴),
    正官("官", 阴阳.阳),
    偏官("杀", 阴阳.阴);

    public static 十神[] results = {偏印, 正印, 比肩, 劫财, 食神, 伤官, 偏财, 正财, 偏官, 正官};

    String shortName;
    阴阳    yinYang;

    十神(String shortName, 阴阳 yinYang) {
        this.shortName = shortName;
        this.yinYang   = yinYang;
    }

    public int getValue() {return ordinal();}

    public String getName() {return name();}

    public String getShortName() {
        return shortName;
    }

    public 阴阳 getYinYang() {
        return yinYang;
    }

    public 五行 getWuXing(天干 ming) {
        return 五行.getByValue((ming.getWuXing().getValue() + getShengKe().getValue() - 1 + 5) % 5);
    }

    public 生克 getShengKe() {
        return 生克.getByValue(getValue() / 2);
    }
}
