package com.zhinan.zhouyi.fate.luck;

import com.zhinan.zhouyi.base.干支;
import com.zhinan.zhouyi.fate.bazi.八字;
import com.zhinan.zhouyi.util.DateUtil;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;

public class 年运 extends 运势 {
    年运(干支 ganzhi, 八字 bazi, LocalDateTime startTime, LocalDateTime endTime) {
        super(ganzhi, bazi, startTime, endTime, 类型.年运);
    }

    public static 年运 of(LocalDateTime dateTime, LocalDateTime birthday, int sex) {
        干支 ganzhi    = DateUtil.toGanZhi(dateTime.getYear());
        LocalDateTime startTime = getStartTimeOfYear(dateTime.getYear());
        LocalDateTime endTime   = getStartTimeOfYear(dateTime.getYear() + 1);
        return new 年运(ganzhi, 八字.of(birthday, sex), startTime, endTime);
    }

    public static List<年运> list(int startYear, LocalDateTime birthday, int sex) {
        List<年运> result = new ArrayList<>();
        年运 luck = 年运.of(getStartTimeOfYear(startYear), birthday, sex);
        for (int i = 0; i < 10; i++) {
            result.add(luck);
            luck = luck.getNext();
        }
        return result;
    }

    public static List<年运> list(LocalDateTime birthday, int sex) {
        List<年运> result = new ArrayList<>();
        List<大运> luckList = 大运.list(birthday, sex);
        for (大运 luck : luckList) {
            result.addAll(list(luck.getStartTime().getYear(), birthday, sex));
        }
        return result;
    }

    public 大运 getParent() {
        return 大运.of(this.startTime, bazi.getBirthday(), bazi.getSex().getValue());
    }

    public 年运 getNext() {
        return new 年运(roll(1), bazi,
                endTime, getStartTimeOfYear(endTime.getYear() + 1));
    }
}
