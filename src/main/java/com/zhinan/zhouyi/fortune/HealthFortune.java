package com.zhinan.zhouyi.fortune;

import com.zhinan.zhouyi.base.五行;
import com.zhinan.zhouyi.energy.能量;
import com.zhinan.zhouyi.fate.bazi.八字;
import com.zhinan.zhouyi.fate.luck.运势;
import lombok.Getter;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Getter
public class HealthFortune extends BaseFortune {
    private final static int TOO_MUCH_VALUE = 30;
    private final static int TOO_LESS_VALUE = 10;
    private final static int  AVERAGE_VALUE = 20;

    int[] originScores = new int[] {0, 0, 0, 0, 0};

    List<五行> tooMuchList = new ArrayList<>();
    List<五行> tooLessList = new ArrayList<>();
    List<五行> emptyList   = new ArrayList<>();

    public HealthFortune(八字 bazi) {
        super(bazi);
        能量 energy = bazi.getEnergy();
        Arrays.asList(五行.values()).forEach(wuXing -> {
            int value = energy.getPercentage(wuXing);
            originScores[wuXing.getValue()] = value;

            if (energy.getNumber(wuXing) == 0) {
                emptyList.add(wuXing);
            } else {
                if (value > TOO_MUCH_VALUE) {
                    tooMuchList.add(wuXing);
                } else if (value < TOO_LESS_VALUE) {
                    tooLessList.add(wuXing);
                }
            }
        });
    }

    public static HealthFortune of(八字 bazi) { return new HealthFortune(bazi); }

    @Override
    public Fortune ofLuck(运势 luck) {
        return ofLuck(luck, bazi.getEnergy().getMing());
    }

    @Override
    public double getScore(八字 bazi) {
        return 0;
    }

    @Override
    public GOOD_BAD judge() {
        return GOOD_BAD.GOOD;
    }

    public Fortune ofLuck(运势 luck, 五行 target) {
        double score = getScore(target, luck, 0.4, 0.6);
        if (luck.getParent() != null) {
            score = ofLuck(luck.getParent(), target).getScore() * 0.6 + score * 0.4;
        }
        return new Fortune(luck, toInt(score), isGood(target, score));
    }

    private boolean isGood(五行 target, double score) {
        return originScores[target.getValue()] > AVERAGE_VALUE && score < 0 ||
               originScores[target.getValue()] < AVERAGE_VALUE && score > 0 ;
    }
}
