package com.zhinan.zhouyi.fate.bazi;

import com.zhinan.zhouyi.base.五行;
import com.zhinan.zhouyi.base.生克;
import lombok.RequiredArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@RequiredArgsConstructor
public enum 格局 {
    印枭主导的偏旺格(new 生克[] {生克.耗, 生克.泄, 生克.克}),
    截比主导的偏旺格(new 生克[] {生克.泄, 生克.耗, 生克.克}),
    官杀主导的偏弱格(new 生克[] {生克.生, 生克.同}),
    财才主导的偏弱格(new 生克[] {生克.同, 生克.生}),
    食伤主导的偏弱格(new 生克[] {生克.生, 生克.同}),

    印枭主导的从旺格(new 生克[] {生克.生, 生克.同}),
    截比主导的从旺格(new 生克[] {生克.同, 生克.生}),
    官杀主导的从弱格(new 生克[] {生克.克, 生克.耗, 生克.泄}),
    财才主导的从弱格(new 生克[] {生克.耗, 生克.泄, 生克.克}),
    食伤主导的从弱格(new 生克[] {生克.泄, 生克.克, 生克.耗});

    private final 生克[] goodShengKeList;

    List<五行> goodList = new ArrayList<>();

    public int getValue() {
        return ordinal();
    }

    public String getName() {
        return name();
    }

    public static 格局 of(八字 bazi) {
        格局 pattern;
        int 生 = bazi.getEnergy().getValue(bazi.getFate().get生());
        int 同 = bazi.getEnergy().getValue(bazi.getFate().get同());
        int 泄 = bazi.getEnergy().getValue(bazi.getFate().get泄());
        int 耗 = bazi.getEnergy().getValue(bazi.getFate().get耗());
        int 克 = bazi.getEnergy().getValue(bazi.getFate().get克());
        if (bazi.selfPart > bazi.otherPart) {
            if (bazi.otherPart >= 80.0 / 610.0) {
                if (生 >= 同) {
                    pattern = 格局.印枭主导的偏旺格;
                } else {
                    pattern = 格局.截比主导的偏旺格;
                }
            } else {
                if (生 > 同) {
                    pattern = 格局.印枭主导的从旺格;
                } else {
                    pattern = 格局.截比主导的从旺格;
                }
            }
        } else {
            int max = Math.max(Math.max(克, 耗), 泄);
            if (bazi.selfPart >= 80.0 / 610.0) {
                if (max == 克) {
                    pattern = 格局.官杀主导的偏弱格;
                } else if (max == 耗) {
                    pattern = 格局.财才主导的偏弱格;
                } else {
                    pattern = 格局.食伤主导的偏弱格;
                }
            } else {
                if (max == 克) {
                    pattern = 格局.官杀主导的从弱格;
                } else if (max == 耗) {
                    pattern = 格局.财才主导的从弱格;
                } else {
                    pattern = 格局.食伤主导的从弱格;
                }
            }
        }
        for (生克 shengKe : pattern.goodShengKeList) {
            pattern.goodList.add(bazi.getFate().get(shengKe));
        }
//        五行 masterGoodGod = bazi.getMasterGoodGod();
//        if (!pattern.goodList.contains(masterGoodGod)) {
//            pattern.goodList.add(masterGoodGod);
//        }
        return pattern;
    }

    public List<五行> getGoodList() {
        return goodList;
    }

    public 五行 getFirstGoodGod() {
        return getGoodList().get(0);
    }

    public 五行 getSecondGoodGod() {
        return getGoodList().get(1);
    }

    public boolean isGood(五行 wuXing) {
        return getGoodList().contains(wuXing);
    }

    public boolean isFirstGoodGod(五行 wuXing) {
        return wuXing.equals(getFirstGoodGod());
    }

    public boolean isSecondGoodGod(五行 wuXing) {
        return wuXing.equals(getSecondGoodGod());
    }
}
