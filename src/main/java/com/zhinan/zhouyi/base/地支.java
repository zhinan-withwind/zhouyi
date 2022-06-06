package com.zhinan.zhouyi.base;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum 地支 implements 元素 {
    子(阴阳.阳, 五行.水, new 天干[]{天干.癸}),
    丑(阴阳.阴, 五行.土, new 天干[]{天干.己, 天干.癸, 天干.辛}),
    寅(阴阳.阳, 五行.木, new 天干[]{天干.甲, 天干.戊, 天干.丙}),
    卯(阴阳.阴, 五行.木, new 天干[]{天干.乙}),
    辰(阴阳.阳, 五行.土, new 天干[]{天干.戊, 天干.乙, 天干.癸}),
    巳(阴阳.阴, 五行.火, new 天干[]{天干.丙, 天干.戊, 天干.庚}),
    午(阴阳.阳, 五行.火, new 天干[]{天干.丁, 天干.己}),
    未(阴阳.阴, 五行.土, new 天干[]{天干.己, 天干.丁, 天干.乙}),
    申(阴阳.阳, 五行.金, new 天干[]{天干.庚, 天干.戊, 天干.壬}),
    酉(阴阳.阴, 五行.金, new 天干[]{天干.辛}),
    戌(阴阳.阳, 五行.土, new 天干[]{天干.戊, 天干.辛, 天干.丁}),
    亥(阴阳.阴, 五行.水, new 天干[]{天干.壬, 天干.甲});

    private final 阴阳 yinYang;
    private final 五行 wuXing;
    private final 天干[] hiddenGan;

    public static 地支 getByValue(int value) {
        return values()[value % 12];
    }

    public 天干 getTianGan() {return get本气();}
    public 天干 get本气() {
        return hiddenGan[0];
    }
    public 天干 get余气() {
        return hiddenGan.length > 1 ? hiddenGan[1] : get本气();
    }
    public 天干 get合气() {
        return hiddenGan.length > 2 ? hiddenGan[2] : get余气();
    }

    public int    getValue() {
        return ordinal();
    }

    public String getName() {
        return name();
    }

    public String getFullName() { return this.getName() + getWuXing().getName(); }

    @Override
    public String toString() { return getFullName(); }
}
