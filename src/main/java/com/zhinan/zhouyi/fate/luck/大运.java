package com.zhinan.zhouyi.fate.luck;

import com.zhinan.zhouyi.base.干支;
import com.zhinan.zhouyi.base.阴阳;
import com.zhinan.zhouyi.fate.bazi.八字;
import com.zhinan.zhouyi.util.DateUtil;

import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class 大运 extends 运势 {

    大运(干支 ganzhi, 八字 bazi, LocalDateTime startTime, LocalDateTime endTime) {
        super(ganzhi, bazi, startTime, endTime, 类型.大运);
    }

    public static 大运 of(LocalDateTime dateTime, LocalDateTime birthday, int sex) {
        八字 bazi = 八字.of(birthday, sex);
        int direction = bazi.getDirection();

        LocalDate luckDate = calculateLuckDate(birthday, sex);

        干支 ganzhi = bazi.getMonth();
        LocalDateTime startTime = birthday;
        LocalDateTime endTime = luckDate.atTime(0, 0);

        if (!dateTime.toLocalDate().isBefore(luckDate)) {
            startTime = getStartTimeOfYear(dateTime.getYear());
            endTime   = getStartTimeOfYear(dateTime.getYear() + 10);
            ganzhi    = bazi.getMonth().roll(direction * ((dateTime.getYear() - luckDate.getYear()) / 10 + 1));
        }

        return new 大运(ganzhi, bazi, startTime, endTime);
    }

    public static long calculateLuckHours(LocalDateTime birthday, int sex) {
        八字 bazi = 八字.of(birthday, sex);
        int direction = bazi.getYear().getGan().getYinYang().equals(阴阳.getByValue(sex)) ? 1: -1;

        long hours;
        if (direction > 0) {
            LocalDateTime nextMajorSolarTerm = DateUtil.getNextMajorSolarTermDateTime(birthday);
            hours = Duration.between(birthday, nextMajorSolarTerm).toHours();
        } else {
            LocalDateTime lastMajorSolarTerm = DateUtil.getLastMajorSolarTermDateTime(birthday);
            hours = Duration.between(lastMajorSolarTerm, birthday).toHours();
        }
        return hours;
    }

    public static String calculateLuckAge(LocalDateTime birthday, int sex) {
        long hours = calculateLuckHours(birthday, sex);
        long year  = hours / 72;
        long month = (hours % 72) / 6;
        long day   = (hours % 6) * 5;
        return year + "年" + month + "个月" + day + "天";
    }

    public static LocalDate calculateLuckDate(LocalDateTime birthday, int sex) {
        long hours = calculateLuckHours(birthday, sex);
        return birthday.toLocalDate().plusYears(hours / 72)
                .plusMonths((hours % 72) / 6).plusDays((hours % 6) * 5);
    }

    @Override
    public String getDate() {
        return String.valueOf(startTime.getYear());
    }

    public 运势 getParent() {
        return null;
    }

    public 大运 getNext() {
        return new 大运(roll(bazi.getDirection()), bazi,
                endTime, getStartTimeOfYear(endTime.getYear() + 10));
    }

    public static List<大运> list(LocalDateTime birthday, int sex) {
        List<大运> result = new ArrayList<>();
//        result.add(大运.of(birthday, birthday, sex));
        大运 luck = 大运.of(calculateLuckDate(birthday, sex).atTime(0, 0), birthday, sex);
        result.add(luck);
        for (int i = 0; i < 9; i++) {
            luck = luck.getNext();
            result.add(luck);
        }
        return result;
    }

    public static List<大运> list(八字 bazi) {
        return list(bazi.getBirthday(), bazi.getSex().getValue());
    }
}
