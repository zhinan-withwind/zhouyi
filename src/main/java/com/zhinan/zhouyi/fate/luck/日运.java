package com.zhinan.zhouyi.fate.luck;

import com.zhinan.zhouyi.base.干支;
import com.zhinan.zhouyi.date.DateFormatType;
import com.zhinan.zhouyi.date.DateType;
import com.zhinan.zhouyi.date.LunarDateTime;
import com.zhinan.zhouyi.fate.bazi.八字;
import com.zhinan.zhouyi.util.DateUtil;

import java.time.Duration;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;

public class 日运  extends 运势 {
    日运(干支 ganzhi, 八字 bazi, LocalDateTime startTime, LocalDateTime endTime) {
        super(ganzhi, bazi, startTime, endTime, 类型.日运);
    }

    public static 日运 of(LocalDateTime dateTime, LocalDateTime birthday, int sex) {
        干支 ganzhi    = DateUtil.toGanZhi(dateTime.getYear(), dateTime.getMonthValue(), dateTime.getDayOfMonth());
        LocalDateTime startTime = dateTime.toLocalDate().atTime(1, 0);
        LocalDateTime endTime   = startTime.plusDays(1);
        return new 日运(ganzhi, 八字.of(birthday, sex), startTime, endTime);
    }

    public static List<日运> list(int year, int month, LocalDateTime birthday, int sex) {
        List<日运> result = new ArrayList<>();
        月运 monthLuck = 月运.of(LocalDateTime.of(year, month, 15, 0, 0), birthday, sex);
        日运 luck = 日运.of(monthLuck.getStartTime(), birthday, sex);
        int length = (int) Duration.between(monthLuck.getStartTime(), monthLuck.endTime).toHours() / 24;
        for (int i = 0; i < length; i++) {
            result.add(luck);
            luck = luck.getNext();
        }
        return result;
    }

    public 月运 getParent() {
        return 月运.of(this.startTime, bazi.getBirthday(), bazi.getSex().getValue());
    }

    public 日运 getNext() {
        return new 日运(roll(1), bazi,
                startTime.plus(1, ChronoUnit.DAYS), endTime.plus(1, ChronoUnit.DAYS));
    }

    @Override
    public String getDate() {
        return startTime.getMonthValue() + "." + startTime.getDayOfMonth();
    }

    @Override
    public String getAge() {
        return LunarDateTime.of(startTime).format(DateFormatType.CHINESE_NUMBER, DateType.DAY);
    }
}
