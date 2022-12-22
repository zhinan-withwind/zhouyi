package com.zhinan.zhouyi.fate.util;

import com.zhinan.zhouyi.base.五行;
import com.zhinan.zhouyi.fate.bazi.八字;
import lombok.Getter;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Getter
public class 健康 {
    五行 month;
    五行 max;
    五行 min;
    List<五行> emptyList = new ArrayList<>();

    public static 健康 of(LocalDateTime birthday, int sex) {
        return of(八字.of(birthday, sex));
    }

    public static 健康 of(八字 bazi) {
        健康 health = new 健康();
        health.month = bazi.getMonth().getZhi().getWuXing();
        health.max   = bazi.getEnergy().getMax();
        health.min   = bazi.getEnergy().getMin();
        Arrays.asList(五行.values()).forEach(wuXing -> {if(bazi.getEnergy().getNumber(wuXing) == 0) {health.emptyList.add(wuXing);}});
        return health;
    }
}
