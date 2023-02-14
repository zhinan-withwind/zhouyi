package com.zhinan.zhouyi.fortune;

import com.zhinan.zhouyi.fate.bazi.八字;
import com.zhinan.zhouyi.fate.luck.运势;

public class MarriageFortune extends BaseFortune {
    public MarriageFortune(八字 bazi) {
        super(bazi);
    }

    @Override
    public Fortune ofLuck(运势 luck) {
        return null;
    }
}
