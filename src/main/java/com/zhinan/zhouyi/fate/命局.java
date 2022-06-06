package com.zhinan.zhouyi.fate;

import com.zhinan.zhouyi.base.五行;
import com.zhinan.zhouyi.base.生克;

import java.util.ArrayList;
import java.util.List;

public enum 命局 {
    印重局(种类.命强), 命旺局(种类.命强), 伤官局(种类.命弱), 财旺局(种类.命弱), 煞重局(种类.命弱);

    enum 种类 {
        命强, 命弱
    }

    种类       type;
    五行       masterGoodGod;
    List<五行> ministerGoodGod = new ArrayList<>();

    命局(种类 type) {
        this.type = type;
    }

    public static 命局 getByValue(int value) {
        return values()[value];
    }

    public static 命局 of(八字 bazi) {
        命局 result = getByValue(bazi.getMing().getWuXing().compare(bazi.getLing().getTianGan().getWuXing()).getValue());

        result.masterGoodGod = bazi.getType().equals(八字.种类.寒局) ? 五行.火 : bazi.getType().equals(八字.种类.燥局) ? 五行.水 : null;

        if (result.getType().equals(种类.命强)) {
            result.ministerGoodGod.add(bazi.getMing().getWuXing().getByShengKe(生克.泄));
            result.ministerGoodGod.add(bazi.getMing().getWuXing().getByShengKe(生克.耗));
            result.ministerGoodGod.add(bazi.getMing().getWuXing().getByShengKe(生克.克));
        } else {
            result.ministerGoodGod.add(bazi.getMing().getWuXing().getByShengKe(生克.生));
            result.ministerGoodGod.add(bazi.getMing().getWuXing().getByShengKe(生克.同));
        }
        return result;
    }

    public int getValue() {return ordinal();}

    public String getName() {return name();}

    public 种类 getType() {
        return type;
    }

    public 五行 getMasterGoodGod() {
        return masterGoodGod;
    }

    public List<五行> getMinisterGoodGod() {
        return ministerGoodGod;
    }

    List<五行> getGoodList() {
        List<五行> result = new ArrayList<>();
        result.add(getMasterGoodGod());
        result.addAll(getMinisterGoodGod());
        return result;
    }

    public boolean isGood(五行 wuXing) {
        return getGoodList().contains(wuXing);
    }
}
