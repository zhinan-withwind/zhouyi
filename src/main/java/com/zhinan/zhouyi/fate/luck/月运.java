package com.zhinan.zhouyi.fate.luck;

import com.zhinan.zhouyi.base.干支;
import com.zhinan.zhouyi.fate.八字;
import com.zhinan.zhouyi.util.DateUtil;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;

public class 月运 extends 运势 {
    月运(干支 ganzhi, 八字 bazi, LocalDateTime startTime, LocalDateTime endTime) {
        super(ganzhi, bazi, startTime, endTime, 类型.月运);
    }

    public static 月运 of(LocalDateTime dateTime, LocalDateTime birthday, int sex) {
        干支 ganzhi    = DateUtil.toGanZhi(dateTime.getYear(), dateTime.getMonthValue());
        LocalDateTime startTime = DateUtil.getLastMajorSolarTerm(dateTime);
        LocalDateTime endTime   = startTime.plusMonths(1);
        return new 月运(ganzhi, 八字.of(birthday, sex), startTime, endTime);
    }

    public static List<月运> list(int year, LocalDateTime birthday, int sex) {
        List<月运> result = new ArrayList<>();
        月运 luck = 月运.of(getStartTimeOfYear(year), birthday, sex);
        for (int i = 0; i < 12; i++) {
            result.add(luck);
            luck = luck.getNext();
        }
        return result;
    }

    public 年运 getParent() {
        return 年运.of(this.startTime, bazi.getBirthday(), bazi.getSex().getValue());
    }

    public 月运 getNext() {
        return new 月运(roll(1), bazi,
                startTime.plus(1, ChronoUnit.MONTHS), endTime.plus(1, ChronoUnit.MONTHS));
    }

    @Override
    public String getDate() {
        return startTime.getMonthValue() + "." + startTime.getDayOfMonth();
    }

    @Override
    public String getAge() {
        return DateUtil.getMajorSolarTerm(startTime).getName();
    }
}
