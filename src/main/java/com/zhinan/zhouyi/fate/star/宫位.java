package com.zhinan.zhouyi.fate.star;

import com.zhinan.zhouyi.base.地支;
import com.zhinan.zhouyi.base.天干;
import com.zhinan.zhouyi.base.干支;
import lombok.Getter;

import java.util.ArrayList;
import java.util.List;

@Getter
public class 宫位 extends 干支 {
    星宫 palace;
    List<星位> stars = new ArrayList<>();
    int startAge;
    int endAge;

    public 宫位(天干 gan, 地支 zhi) {
        super(gan, zhi);
    }
}
