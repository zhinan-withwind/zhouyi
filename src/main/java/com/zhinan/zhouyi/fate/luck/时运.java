package com.zhinan.zhouyi.fate.luck;

import com.zhinan.zhouyi.base.干支;
import com.zhinan.zhouyi.fate.bazi.八字;
import com.zhinan.zhouyi.util.DateUtil;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;

public class 时运 extends 运势 {
    时运(干支 ganzhi, 八字 bazi, LocalDateTime startTime, LocalDateTime endTime) {
        super(ganzhi, bazi, startTime, endTime, 类型.时运);
    }

    public static 时运 of(LocalDateTime dateTime, LocalDateTime birthday, int sex) {
        干支 ganzhi = DateUtil.toGanZhi(dateTime.getYear(), dateTime.getMonthValue(), dateTime.getDayOfMonth(), dateTime.getHour());
        LocalDateTime startTime = dateTime.toLocalDate().atTime(dateTime.getHour(), 0).minusHours(1 - dateTime.getHour() % 2);
        LocalDateTime endTime   = startTime.plusHours(2);
        return new 时运(ganzhi, 八字.of(birthday, sex), startTime, endTime);
    }

    public static List<时运> list(int year, int month, int day, LocalDateTime birthday, int sex) {
        List<时运> result = new ArrayList<>();
        时运 luck = 时运.of(LocalDateTime.of(year, month, day, 0, 0), birthday, sex);
        for (int i = 0; i < 12; i++) {
            result.add(luck);
            luck = luck.getNext();
        }
        return result;
    }

    public 日运 getParent() {
        return 日运.of(this.startTime, bazi.getBirthday(), bazi.getSex().getValue());
    }

    public 时运 getNext() {
        return new 时运(roll(1), bazi,
                startTime.plus(2, ChronoUnit.HOURS), endTime.plus(2, ChronoUnit.HOURS));
    }

    @Override
    public String getDate() {
        return String.valueOf((startTime.getHour() +  1) % 24);
    }

    @Override
    public String getAge() {
        return String.valueOf((startTime.getHour() + 23) % 24);
    }
}
