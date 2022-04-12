package com.zhinan.zhouyi.fate;

import com.zhinan.zhouyi.base.地支;
import com.zhinan.zhouyi.base.天干;
import com.zhinan.zhouyi.base.干支;
import com.zhinan.zhouyi.base.阴阳;
import com.zhinan.zhouyi.util.DateUtil;
import net.time4j.calendar.SolarTerm;

import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

public class 运势 extends 干支 {
    public enum 类型 {
        大运, 年运, 月运, 日运, 时运;

        public static 类型 getByValue(int value) {
            return values()[value];
        }
    }
    public enum 方向 {
        正排, 逆排;
    }

    LocalDateTime startTime;
    LocalDateTime endTime;
    类型 type;

    public 运势(天干 gan, 地支 zhi, 类型 type, LocalDateTime startTime, LocalDateTime endTime) {
        super(gan, zhi);
        this.startTime = startTime;
        this.endTime   = endTime;
        this.type = type;
    }

    public static 方向 getLuckDirection(LocalDateTime birthday, int sex) {
        return 八字.of(birthday, sex).year.getGan().getYinYang().equals(阴阳.getByValue(sex)) ? 方向.逆排 : 方向.正排;
    }

    public static LocalDate calculateLuckDate(LocalDateTime birthday, int sex) {
        long hours;
        if (getLuckDirection(birthday, sex).equals(方向.逆排)) {
            hours = Duration.between(DateUtil.getLastMajorSolarTerm(birthday.toLocalDate()), birthday).toHours();
        } else {
            hours = Duration.between(birthday, DateUtil.getNextMajorSolarTerm(birthday.toLocalDate())).toHours();
        }

        return birthday.toLocalDate().plusYears(hours / 72).plusMonths((hours % 72) / 6).plusDays((hours % 6) * 5);
    }

    public static 运势 of(LocalDateTime birthday, int sex, LocalDateTime dateTime, int type) {
        干支 ganzhi = null;
        LocalDateTime startTime = null;
        LocalDateTime endTime = null;
        switch (类型.getByValue(type)) {
            case 大运:
                LocalDate luckDate = calculateLuckDate(birthday, sex);
                八字 bazi = 八字.of(birthday, sex);
                if (birthday.toLocalDate().isBefore(luckDate)) {
                    ganzhi    = bazi.month;
                    startTime = birthday;
                    endTime   = luckDate.atTime(0, 0);
                } else {
                    startTime = dateTime.minusYears((dateTime.getYear() - luckDate.getYear()) % 10);
                    endTime   = startTime.plusYears(10);
                    ganzhi    = bazi.month.roll((
                            getLuckDirection(birthday, sex).equals(方向.正排) ? 1 : -1) *
                            (dateTime.getYear() - luckDate.getYear()) / 10);
                }
                break;

            case 年运:
                ganzhi    = DateUtil.toGanZhi(dateTime.getYear());
                startTime = DateUtil.toLocalDate(dateTime.getYear(), SolarTerm.MINOR_01_LICHUN_315).atTime(0, 0);
                endTime   = startTime.plusYears(1);
                break;

            case 月运:
                ganzhi    = DateUtil.toGanZhi(dateTime.getYear(), dateTime.getMonthValue());
                startTime = DateUtil.getLastMajorSolarTerm(dateTime.toLocalDate()).atTime(0, 0);
                endTime   = startTime.plusMonths(1);
                break;

            case 日运:
                ganzhi    = DateUtil.toGanZhi(dateTime.getYear(), dateTime.getMonthValue(), dateTime.getDayOfMonth());
                startTime = dateTime.toLocalDate().atTime(0, 0);
                endTime   = dateTime.plusDays(1);
                break;

            case 时运:
                ganzhi    = DateUtil.toGanZhi(dateTime.getYear(), dateTime.getMonthValue(), dateTime.getDayOfMonth(), dateTime.getHour());
                startTime = dateTime.toLocalDate().atTime(dateTime.getHour(), 0).minusHours(1 - dateTime.getHour() % 2);
                endTime   = startTime.plusHours(2);
        }
        return new 运势(ganzhi.getGan(), ganzhi.getZhi(), 类型.getByValue(type), startTime, endTime);
    }



}
