package com.zhinan.zhouyi.fortune;

import com.zhinan.zhouyi.base.五行;
import com.zhinan.zhouyi.energy.能量;
import com.zhinan.zhouyi.fate.bazi.八字;
import com.zhinan.zhouyi.fate.luck.运势;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
public class CareerFortune extends BaseFortune {
    @Getter
    @AllArgsConstructor
    enum CAREER_DIRECTION {
        OFFICIAL("从政"), RESEARCH("学术"), BUSINESS("经商"), ARTISTE("艺术");

        String name;

        public int getValue() { return ordinal(); }

        public static CAREER_DIRECTION getByValue(int value) {
            return values()[value];
        }
    }

    CAREER_DIRECTION direction;

    public CareerFortune(八字 bazi) {
        super(bazi);
        this.direction = getDirection(bazi);
    }

    public static CareerFortune of(八字 bazi) {
        return new CareerFortune(bazi);
    }

    public static CAREER_DIRECTION getDirection(八字 bazi) {
        能量 energy = bazi.getEnergy();
        五行 ming   = energy.getMing();

        int giveScore = energy.getValue(ming.get生());
        int leakScore = energy.getValue(ming.get泄());
        int costScore = energy.getValue(ming.get耗());
        int curbScore = energy.getValue(ming.get克());

        int[] values = {curbScore, giveScore, costScore, leakScore};

        int max = 0;
        int value = 0;
        for (int i = 0; i < values.length; i++) {
            if (max < values[i]) {
                max = values[i];
                value = i;
            }
        }

        return  CAREER_DIRECTION.getByValue(value);
    }

    @Override
    public Fortune ofLuck(运势 luck) {
        double score = getScore(bazi.getEnergy().getMing().get克(), luck, 0.4, 0.6);
        if (luck.getParent() != null) {
            score = ofLuck(luck.getParent()).getScore() * 0.6 + score * 0.4;
        }
        return new Fortune(luck, toInt(score), false);
    }

    @Override
    double getScore(八字 bazi) {
        能量 energy = bazi.getEnergy();
        五行 ming   = energy.getMing();
        五行 useGod = ming.get克();

        return energy.getValue(useGod) * (energy.isStrong() ? 0.8 : 0.3);
    }

    @Override
    boolean judge(double score) {
        return score > 0;
    }


}
