package com.zhinan.zhouyi.fate.bazi.model;

import com.zhinan.zhouyi.base.五行;
import com.zhinan.zhouyi.base.生克;
import com.zhinan.zhouyi.energy.能量;
import com.zhinan.zhouyi.fate.bazi.八字;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@NoArgsConstructor
public class ScoreBasedModel implements FatePatternModel {
    enum FATE_PATTERN {
        印枭思辨格, 截比强旺格, 食伤灵动格, 财才务实格, 官杀自律格
    }

    @RequiredArgsConstructor
    enum GRID_PATTERN {
        印枭主导的偏旺格(new 生克[] {生克.耗, 生克.泄, 生克.克}),
        截比主导的偏旺格(new 生克[] {生克.泄, 生克.耗, 生克.克}),
        食伤主导的偏弱格(new 生克[] {生克.生, 生克.同}),
        财才主导的偏弱格(new 生克[] {生克.同, 生克.生}),
        官杀主导的偏弱格(new 生克[] {生克.生, 生克.同}),

        印枭主导的从旺格(new 生克[] {生克.生, 生克.同}),
        截比主导的从旺格(new 生克[] {生克.同, 生克.生}),
        食伤主导的从弱格(new 生克[] {生克.泄, 生克.克, 生克.耗}),
        财才主导的从弱格(new 生克[] {生克.耗, 生克.泄, 生克.克}),
        官杀主导的从弱格(new 生克[] {生克.克, 生克.耗, 生克.泄});

        private final 生克[] goodShengKeList;

        public static GRID_PATTERN of(生克 shengKe, boolean follow) {
            return values()[(follow ? 1 : 0) * 5 + shengKe.getValue()];
        }

        public int getValue() {
            return ordinal();
        }

        public String getName() {
            return name();
        }
    }

    八字 bazi;
    能量 energy;
    GRID_PATTERN gridPattern;
    int value;
    boolean strong;
    List<五行> goodGodList = new ArrayList<>();
    List<五行>  badGodList = new ArrayList<>();

    public ScoreBasedModel of(八字 bazi) {
        this.bazi   = bazi;
        this.energy = 能量.of(bazi.getFourColumn());
        this.value  = bazi.getMing().getWuXing().compare(energy.getMax()).getValue();
        this.strong = getSelfPart() == getOtherPart() && isSelfPart(bazi.getLing().getWuXing()) || getSelfPart() > getOtherPart();
        this.gridPattern = GRID_PATTERN.of(bazi.getMing().getWuXing().compare(this.energy.getMax()), isFollow());

        Arrays.asList(gridPattern.goodShengKeList).forEach(shengKe -> this.goodGodList.add(bazi.getMing().getWuXing().getByShengKe(shengKe)));
        Arrays.asList(五行.values()).forEach(wuXing -> {if (!this.goodGodList.contains(wuXing)) this.badGodList.add(wuXing);});
        return this;
    }

    @Override
    public int getValue() {
        return value;
    }

    @Override
    public String getName() {
        return FATE_PATTERN.values()[value].name();
    }

    @Override
    public int getSelfPart() {
        return energy.getSelfPartPercentage();
    }

    @Override
    public int getOtherPart() {
        return energy.getOtherPartPercentage();
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
        return getSelfPart() <= 80 * 100 / 610;
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
