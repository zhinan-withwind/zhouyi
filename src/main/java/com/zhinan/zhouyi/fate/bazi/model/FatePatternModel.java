package com.zhinan.zhouyi.fate.bazi.model;

import com.zhinan.zhouyi.base.五行;
import com.zhinan.zhouyi.fate.bazi.八字;

import java.util.List;

public interface FatePatternModel {
    FatePatternModel of(八字 bazi);
    int     getValue();
    String  getName();
    int     getSelfPart();
    int     getOtherPart();
    boolean isStrong();
    boolean isFollow();
    List<五行> getGoodGodList();
    List<五行> getBadGodList();
}
