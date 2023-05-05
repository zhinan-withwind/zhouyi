package com.zhinan.zhouyi.fate.bazi.model;

import com.zhinan.zhouyi.base.五行;
import com.zhinan.zhouyi.base.地支;
import com.zhinan.zhouyi.base.生克;
import com.zhinan.zhouyi.fate.bazi.八字;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@NoArgsConstructor
public class MonthBasedModel implements FatePatternModel {
    @AllArgsConstructor
    enum FATE_PATTERN {
        印重局(true,  "爱思考的印重局",  Arrays.asList(生克.泄, 生克.克, 生克.耗)),
        命旺局(true,  "自信的命旺局",    Arrays.asList(生克.泄, 生克.克, 生克.耗)),
        伤官局(false, "聪明的伤官局",    Arrays.asList(生克.生, 生克.同)),
        财旺局(false, "有目标的财旺局",  Arrays.asList(生克.生, 生克.同)),
        煞重局(false, "谨慎的煞重局",    Arrays.asList(生克.生, 生克.同));

        boolean strong;
        String  name;
        List<生克> goodShengKeList;

        static FATE_PATTERN getByValue(int value) {
            return values()[value];
        }
    }

    @AllArgsConstructor
    enum COLD_HOTNESS {
        寒(Arrays.asList(地支.亥, 地支.子, 地支.丑, 地支.寅), 五行.火),
        燥(Arrays.asList(地支.辰, 地支.巳, 地支.午, 地支.未), 五行.水),
        不寒不燥(Arrays.asList(地支.卯, 地支.申, 地支.酉, 地支.戌), null);

        List<地支> monthList;
        五行 good;

        public static COLD_HOTNESS of(八字 bazi) {
            COLD_HOTNESS pattern = 不寒不燥;
            for (COLD_HOTNESS p : values()) {
                if (p.monthList.contains(bazi.getLing())) {
                    if (bazi.getLing().equals(地支.戌)
                            && bazi.getYear ().getZhi().getWuXing().equals(五行.水)
                            || bazi.getMonth().getGan().getWuXing().equals(五行.水)
                            || bazi.getDay  ().getZhi().getWuXing().equals(五行.水)) {
                        pattern = 寒;
                    } else {
                        pattern = p;
                    }
                }
            }
            return pattern;
        }
    }

    八字 bazi;
    FATE_PATTERN pattern;
    COLD_HOTNESS hotCold;
    int value;
    int selfPart  = 0;
    boolean strong;
    boolean follow;
    List<五行> goodGodList = new ArrayList<>();
    List<五行>  badGodList = new ArrayList<>();

    public MonthBasedModel of(八字 bazi) {
        this.bazi      = bazi;
        this.value     = bazi.getMing().getWuXing().compare(bazi.getLing().getWuXing()).getValue();
        this.pattern   = FATE_PATTERN.getByValue(this.value);
        this.hotCold   = COLD_HOTNESS.of(bazi);
        this.strong    = pattern.strong;

        this.selfPart += (isSelfPart(bazi.getYear ().getGan().getWuXing()) ? 10 : 0);
        this.selfPart += (isSelfPart(bazi.getYear ().getZhi().getWuXing()) ? 10 : 0);
        this.selfPart += (isSelfPart(bazi.getMonth().getGan().getWuXing()) ? 10 : 0);
        this.selfPart += (isSelfPart(bazi.getMonth().getZhi().getWuXing()) ? 30 : 0);
        this.selfPart += (isSelfPart(bazi.getDay  ().getGan().getWuXing()) ? 10 : 0);
        this.selfPart += (isSelfPart(bazi.getDay  ().getZhi().getWuXing()) ? 10 : 0);
        this.selfPart += (isSelfPart(bazi.getTime ().getGan().getWuXing()) ? 10 : 0);
        this.selfPart += (isSelfPart(bazi.getTime ().getZhi().getWuXing()) ? 10 : 0);

        if ( this.strong && getSelfPart() <= getOtherPart()) {
            this.selfPart  = 51;
        }
        if (!this.strong && getSelfPart() >= getOtherPart()) {
            this.selfPart  = 49;
        }

        pattern.goodShengKeList.forEach(shengKe -> this.goodGodList.add(bazi.getMing().getWuXing().getByShengKe(shengKe)));
        if (getMasterGoodGod() != null && !this.goodGodList.contains(getMasterGoodGod())) this.goodGodList.add(getMasterGoodGod());
        Arrays.asList(五行.values()).forEach(wuXing -> {if (!this.goodGodList.contains(wuXing)) this.badGodList.add(wuXing);});

        return this;
    }

    public 五行 getMasterGoodGod() {
        return this.hotCold.good;
    }

    @Override
    public int getValue() {
        return value;
    }

    @Override
    public String getName() {
        return pattern.name;
    }

    @Override
    public int getSelfPart() {
        return selfPart;
    }

    @Override
    public int getOtherPart() {
        return 100 - getSelfPart();
    }

    public boolean isSelfPart(五行 wuXing) {
        return wuXing.equals(bazi.getMing().getWuXing()) || wuXing.equals(bazi.getMing().getWuXing().get生());
    }

    @Override
    public boolean isStrong() {
        return strong;
    }

    @Override
    public boolean isFollow() {
        return follow;
    }

    @Override
    public List<五行> getGoodGodList() {
        return goodGodList;
    }

    @Override
    public List<五行> getBadGodList() {
        return badGodList;
    }
}
