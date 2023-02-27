package com.zhinan.zhouyi.fortune;

import com.zhinan.zhouyi.fate.bazi.八字;

public interface ClassifiedFortune {
    八字 getBazi();
    int getScore();
    BaseFortune.GOOD_BAD judge(double score);
    boolean isGood();
    boolean isOK();
    boolean notBad();
    boolean isBad();
}
