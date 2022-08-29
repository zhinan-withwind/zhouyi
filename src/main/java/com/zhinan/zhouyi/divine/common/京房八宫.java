package com.zhinan.zhouyi.divine.common;

import lombok.Getter;

@Getter
public enum 京房八宫 {
    乾, 兑, 离, 震, 巽, 坎, 艮, 坤;

    六十四卦  主卦;
    六十四卦  一世;
    六十四卦  二世;
    六十四卦  三世;
    六十四卦  四世;
    六十四卦  五世;
    六十四卦  游魂;
    六十四卦  归魂;

    六十四卦[] generations;

    京房八宫() {
        this.主卦 = 六十四卦.getByCode(八卦.getByName(name()).code + 八卦.getByName(name()).code);
        this.一世 = this.主卦.change(1);
        this.二世 = this.一世.change(2);
        this.三世 = this.二世.change(3);
        this.四世 = this.三世.change(4);
        this.五世 = this.四世.change(5);
        this.游魂 = this.五世.change(4);
        this.归魂 = this.游魂.change(3).change(2).change(1);
        this.generations = new 六十四卦[] {主卦, 一世, 二世, 三世, 四世, 五世, 游魂, 归魂};
    }

    public int getValue() {
        return ordinal() + 1;
    }

    public String getName() {
        return name() + "宫";
    }
}
