package com.zhinan.zhouyi.fate.bazi;

import com.zhinan.zhouyi.base.五行;
import com.zhinan.zhouyi.common.ZhouYiAPI;
import com.zhinan.zhouyi.fate.bazi.model.FatePatternModel;
import lombok.Getter;

import java.util.List;

@Getter
public class FatePattern {
    int value;
    String name;

    int selfPart;
    int otherPart;

    boolean strong;
    boolean follow;

    List<五行> goodGodList;
    List<五行>  badGodList;

    public static FatePattern of(八字 bazi) {
        FatePattern pattern = new FatePattern();
        FatePatternModel model = ZhouYiAPI.getModel(bazi);
        pattern.value       = model.getValue();
        pattern.name        = model.getName();
        pattern.selfPart    = model.getSelfPart ();
        pattern.otherPart   = model.getOtherPart();
        pattern.follow      = model.isFollow();
        pattern.strong      = model.isStrong();
        pattern.goodGodList = model.getGoodGodList();
        pattern. badGodList = model.getBadGodList();
        return pattern;
    }

    public boolean isGood(五行 wuXing) {
        return getGoodGodList().contains(wuXing);
    }

    public boolean isBad(五行 wuXing) {
        return !isGood(wuXing);
    }

    public 五行 getFirstGoodGod() {
        return getGoodGodList().size() > 0 ? getGoodGodList().get(0) : null;
    }

    public 五行 getSecondGoodGod() {
        return getGoodGodList().size() > 1 ? getGoodGodList().get(1) : null;
    }

    public 五行 getThirdGoodGod() {
        return getGoodGodList().size() > 2 ? getGoodGodList().get(2) : null;
    }

    public 五行 getFirstBadGod() {
        return getBadGodList().size() > 0 ? getBadGodList().get(0) : null;
    }

    public 五行 getSecondBadGod() {
        return getBadGodList().size() > 1 ? getBadGodList().get(1) : null;
    }

    public 五行 getThirdBadGod() {
        return getBadGodList().size() > 2 ? getBadGodList().get(2) : null;
    }
}
