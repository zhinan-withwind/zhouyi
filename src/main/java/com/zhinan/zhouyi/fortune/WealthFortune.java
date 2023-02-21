package com.zhinan.zhouyi.fortune;

import com.zhinan.zhouyi.base.十神;
import com.zhinan.zhouyi.base.干支;
import com.zhinan.zhouyi.energy.能量;
import com.zhinan.zhouyi.fate.bazi.八字;
import com.zhinan.zhouyi.fate.luck.运势;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.ArrayList;
import java.util.List;

/**
 * 财富运势：
 *
 * 描述一个人的财富运势状况，分以下几个方面：
 * 1. 整体财运状况评分
 * 2. 财运评估是好是坏
 * 3. 现金流状况评分
 * 4. 固定资产状况评分
 * 5. 收入来源有哪些
 *
 * 一个人的财运状况由他的身强身弱和财星强弱共同决定，
 * 财星代表了一个人获得财富的机会，如果一个人的财星很弱，那么说明他命中获得财富的机会就不多，这个人肯定不会富有
 * 身强身弱代表了一个人能够承担财富的能力（俗称：担财能力），俗话说：财大伤身，如果一个人的自身状态不足以承担巨大的财富，却得到了巨大财富，反而会为财所累。
 * 比如中了彩票反而招致杀身之祸的，就算没有被杀，却因为突然暴富，而挥霍无度，沉迷放纵，最后导致很快就挥霍一空，反而债台高筑的，比比皆是。
 * 所以在看一个人的财运的时候，对比命主能量与财星能量，
 * 身强而财弱的，你承担能力再强，奈何机会只有这么多，要按财星能量来算。
 * 身弱而财多的，你发财机会再多，奈何能承担得却很少，要按命主能量来算。
 */
public class WealthFortune extends BaseFortune {
    /**
     * 收入来源类别
     * 0 - 工资收入
     * 1 - 投资外快
     * 2 - 多有兼职
     * 3 - 不要投资
     * 4 - 多种多样
     */
    @Getter
    @AllArgsConstructor
    enum MONEY_SOURCE {
        SALARY("工作薪资"), INVEST("投资外快"), PART_TIME("多有兼职"), NO_INVEST("不要投资"), VARIED("多种多样");

        String name;

        public int getValue() { return ordinal(); }

        public static WealthFortune.MONEY_SOURCE getByValue(int value) {
            return values()[value];
        }
    }

    /**
     * 现金流评分
     */
    int cashFlowScore;
    /**
     * 固定资产评分
     */
    int fixAssetScore;
    /**
     * 破财指数
     */
    int breakFortuneRate;
    /**
     * 收入来源
     */
    List<MONEY_SOURCE> moneySources = new ArrayList<>();

    public WealthFortune(八字 bazi) {
        super(bazi);
        能量 energy = 能量.of(bazi.getFourColumn());
        int self = energy.getPercentage(energy.getMing());
        int cost = energy.getPercentage(energy.getMing().get耗());

        this.breakFortuneRate = cost * 100 / self;
        int periodicMoneyCount = 0, unplannedMoneyCount = 0;
        for (干支 ganZhi : bazi.getFourColumn()) {
            this.cashFlowScore  += ganZhi.getGan().getWuXing().equals(energy.getMing().get耗()) ? 1 : 0;
            this.fixAssetScore  += ganZhi.getZhi().getWuXing().equals(energy.getMing().get耗()) ? 1 : 0;
            periodicMoneyCount  += bazi.getMing().compare(ganZhi.getGan()).equals(十神.正财) ? 1 : 0;
            unplannedMoneyCount += bazi.getMing().compare(ganZhi.getGan()).equals(十神.偏财) ? 1 : 0;
        }
        this.cashFlowScore *= 100.0 / 3;
        this.fixAssetScore *= 100.0 / 4;
        if (periodicMoneyCount  >  2) moneySources.add(MONEY_SOURCE.SALARY);
        if (unplannedMoneyCount >  2) moneySources.add(MONEY_SOURCE.INVEST);
        if (periodicMoneyCount  == 0) moneySources.add(MONEY_SOURCE.VARIED);
        if (unplannedMoneyCount == 0) moneySources.add(MONEY_SOURCE.NO_INVEST);
        if (unplannedMoneyCount >  0 && periodicMoneyCount > 0) moneySources.add(MONEY_SOURCE.PART_TIME);
    }

    public static WealthFortune of(八字 bazi) {
        return new WealthFortune(bazi);
    }

    @Override
    public Fortune ofLuck(运势 luck) {
        double costScore = getScore(bazi.getEnergy().getMing().get耗(), luck, 0.6, 0.4);
        double selfScore = getScore(bazi.getEnergy().getMing().get同(), luck, 0.4, 0.6);
        double score;
        // 财星能量与命主能量平衡
        if (breakFortuneRate == 100) {
            score = Math.min(selfScore, costScore);
        // 财星能量弱于命主能量，需要补充财星能量
        } else if (breakFortuneRate < 100) {
            score = costScore;
        // 财星能量强于命主能量，需要补充命主能量
        } else {
            score = selfScore;
        }
        if (luck.getParent() != null) {
            score = ofLuck(luck.getParent()).getScore() * 0.6 + score * 0.4;
        }
        return new Fortune(luck, toInt(score), score > 0);
    }

    @Override
    double getScore(八字 bazi) {
        能量 energy = 能量.of(bazi.getFourColumn());
        int self = energy.getPercentage(energy.getMing());
        int cost = energy.getPercentage(energy.getMing().get耗());
        return Math.min(self, cost);
    }

    @Override
    boolean judge(double score) {
        return score > 10;
    }
}
