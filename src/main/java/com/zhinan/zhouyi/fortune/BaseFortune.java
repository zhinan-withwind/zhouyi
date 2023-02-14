package com.zhinan.zhouyi.fortune;

import com.zhinan.zhouyi.base.五行;
import com.zhinan.zhouyi.base.干支;
import com.zhinan.zhouyi.fate.bazi.八字;
import com.zhinan.zhouyi.fate.luck.运势;
import lombok.Getter;

import java.util.ArrayList;
import java.util.List;

@Getter
public abstract class BaseFortune {
    八字 bazi;

    double score = 0.0;
    boolean good = false;

    List<Fortune> decadeFortuneList = new ArrayList<>();

    public BaseFortune(八字 bazi) {
        this.bazi = bazi;
    }

    protected int getScore(五行 target, 五行 effect) {
        int score = 0;
        switch (target.compare(effect)) {
            case 生:
                score += 80;
                break;
            case 同:
                score += 100;
                break;
//            case 泄:
//                score -= 20;
//                break;
//            case 耗:
//                score -= 30;
//                break;
//            case 克:
//                score -= 40;
        }
        return score;
    }

    protected double getScore(五行 target, 干支 ganzhi, double ganRate, double zhiRate) {
        return getScore(target, ganzhi.getGan().getWuXing()) * ganRate +
               getScore(target, ganzhi.getZhi().getWuXing()) * zhiRate ;
    }

    protected int toInt(Double value) { return value.intValue(); }

    public abstract Fortune ofLuck(运势 luck);
}
