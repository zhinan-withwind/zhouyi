package com.zhinan.zhouyi.fate.bazi;

import com.zhinan.zhouyi.base.五行;
import com.zhinan.zhouyi.base.生克;
import lombok.SneakyThrows;

import java.util.ArrayList;
import java.util.List;

public enum 命局 {
    印重局("印枭思辨格", "爱思考的印重局", 种类.命强),
    命旺局("截比强旺格", "自信的命旺局"  , 种类.命强),
    伤官局("食伤灵动格", "聪明的伤官局"  , 种类.命弱),
    财旺局("财才务实格", "有目标的财旺局", 种类.命弱),
    煞重局("官杀自律格", "谨慎的煞重局"  , 种类.命弱);

    public enum 种类 {
        命强, 命弱
    }

    种类       type;
    String    name;
    String    alias;

    命局(String name, String alias, 种类 type) {
        this.name  = name;
        this.alias = alias;
        this.type  = type;
    }

    public static 命局 getByValue(int value) {
        return values()[value];
    }

    public static 命局 of(八字 bazi) {
        return getByValue(bazi.getMing().getWuXing().compare(bazi.getLing().getTianGan().getWuXing()).getValue());
    }

    public int getValue() {return ordinal();}

    public String getName() {return name();}

    public 种类 getType() {
        return type;
    }

    public static 五行 getMasterGoodGod(八字 bazi) {
        return bazi.getMasterGoodGod();
    }

    public static List<五行> getMinisterGoodGod(八字 bazi) {
        List<五行> result = new ArrayList<>();
        if (of(bazi).getType().equals(种类.命强)) {
            result.add(bazi.getMing().getWuXing().getByShengKe(生克.泄));
            result.add(bazi.getMing().getWuXing().getByShengKe(生克.耗));
            result.add(bazi.getMing().getWuXing().getByShengKe(生克.克));
        } else {
            result.add(bazi.getMing().getWuXing().getByShengKe(生克.生));
            result.add(bazi.getMing().getWuXing().getByShengKe(生克.同));
        }
        return result;
    }

    public List<五行> getGoodList(八字 bazi) {
        List<五行> result = new ArrayList<>(getMinisterGoodGod(bazi));
        if (!getMinisterGoodGod(bazi).contains(getMasterGoodGod(bazi))) {
            result.add(getMasterGoodGod(bazi));
        }
        return result;
    }

    public boolean isGood(八字 bazi, 五行 wuXing) {
        return getGoodList(bazi).contains(wuXing);
    }
}
