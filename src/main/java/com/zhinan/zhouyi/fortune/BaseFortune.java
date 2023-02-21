package com.zhinan.zhouyi.fortune;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.annotation.JSONField;
import com.zhinan.zhouyi.base.五行;
import com.zhinan.zhouyi.base.干支;
import com.zhinan.zhouyi.fate.bazi.八字;
import com.zhinan.zhouyi.fate.luck.大运;
import com.zhinan.zhouyi.fate.luck.运势;
import lombok.Getter;

import java.util.ArrayList;
import java.util.List;

@Getter
public abstract class BaseFortune implements ClassifiedFortune {
    @JSONField(serialize = false)
    八字 bazi;

    protected double score;
    protected boolean good;

    protected List<Fortune> decadeFortuneList;

    public BaseFortune(八字 bazi) {
        this.bazi  = bazi;
        this.score = getScore(bazi);
        this.good  = judge(this.score);
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

    abstract double  getScore(八字 bazi);

    abstract boolean judge(double score);

    public int getScore() {
        return toInt(score);
    }

    public boolean isGood() {
        return good;
    }

    public List<Fortune> getDecadeFortuneList() {
        if (decadeFortuneList == null) {
            decadeFortuneList = new ArrayList<>();
            大运.list(bazi).forEach(luck -> this.decadeFortuneList.add(ofLuck(luck)));
        }
        return decadeFortuneList;
    }

    @Override
    public String toString() {
        return this.getClass().getSimpleName() + ": " + JSON.toJSONString(this, true);
    }
}
