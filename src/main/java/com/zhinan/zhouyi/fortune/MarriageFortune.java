package com.zhinan.zhouyi.fortune;

import com.zhinan.zhouyi.base.五行;
import com.zhinan.zhouyi.base.干支;
import com.zhinan.zhouyi.effect.合化冲;
import com.zhinan.zhouyi.energy.能量;
import com.zhinan.zhouyi.fate.bazi.八字;
import com.zhinan.zhouyi.fate.luck.运势;

public class MarriageFortune extends BaseFortune {
    五行 starWuXing;
    五行 palaceWuXing;

    public MarriageFortune(八字 bazi) {
        super(bazi);
    }

    @Override
    double getScore(八字 bazi) {
        能量 energy   = 能量.of(bazi.getFourColumn());
        starWuXing   = bazi.getSex().isYang() ? energy.getMing().get耗() : energy.getMing().get克();
        palaceWuXing = bazi.getDay().getZhi().getWuXing();
        boolean appearInGan = false, appearInZhi = false;
        for (干支 ganZhi : bazi.getFourColumn()) {
            appearInGan = appearInGan || ganZhi.getGan().getWuXing().equals(starWuXing);
            appearInZhi = appearInZhi || ganZhi.getZhi().getWuXing().equals(starWuXing);
        }
        double starScore = !appearInGan && !appearInZhi ? -60 : (appearInGan ? 60 : 0) + (appearInZhi ? 40 : 0)
                * (bazi.getFatePattern().isGood(starWuXing) ? 1 : -1);
        double palaceStore = 80 * (bazi.getFatePattern().isGood(palaceWuXing) ? 1 : -1);
        return starScore * 0.6 + palaceStore * 0.4;
    }

    @Override
    boolean judge(double score) {
        return score > 0;
    }

    public static MarriageFortune of(八字 bazi) {
        return new MarriageFortune(bazi);
    }

    @Override
    public Fortune ofLuck(运势 luck) {
        double starScore = getScore(starWuXing, luck, 0.6, 0.4)
                * (bazi.getFatePattern().isGood(starWuXing) ? 1 : -1);
        double palaceScore;
        switch (合化冲.getRelation(bazi.getDay().getZhi(), luck.getZhi())) {
            case 合:
                palaceScore = 80;
                break;
            case 刑:
            case 破:
                palaceScore = -60;
                break;
            case 冲:
                palaceScore = -80;
                break;
            case 害:
                palaceScore = -100;
                break;
            case 无:
            default:
                palaceScore = 80 * (bazi.getFatePattern().isGood(palaceWuXing) ? 1 : -1);
                break;
        }
        double score = starScore * 0.6 + palaceScore * 0.4;
        if (luck.getParent() != null) {
            score = ofLuck(luck.getParent()).getScore() * 0.6 + score * 0.4;
        }
        return new Fortune(luck, toInt(score), score > 0);
    }

}
