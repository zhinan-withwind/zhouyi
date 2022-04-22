package com.zhinan.zhouyi.util;

import com.zhinan.zhouyi.base.干支;
import com.zhinan.zhouyi.date.LunarDateTime;
import com.zhinan.zhouyi.date.SolarTerm;
import lombok.extern.slf4j.Slf4j;
import net.time4j.PlainDate;
import net.time4j.calendar.ChineseCalendar;
import net.time4j.calendar.CommonElements;
import net.time4j.calendar.EastAsianMonth;
import net.time4j.calendar.EastAsianYear;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

@Slf4j
public class DateUtil {

    public static LunarDateTime toLunar(LocalDateTime solarDate) {
        ChineseCalendar calendar = PlainDate.from(solarDate.toLocalDate()).transform(ChineseCalendar.class);
        return LunarDateTime.of(calendar.getInt(CommonElements.RELATED_GREGORIAN_YEAR),
                calendar.getMonth().getNumber(), calendar.getDayOfMonth(),
                solarDate.getHour(), solarDate.getMinute(), calendar.getMonth().isLeap());
    }

    public static LocalDateTime toSolar(LunarDateTime lunarDateTime) {
        return ChineseCalendar.of(EastAsianYear.forGregorian(lunarDateTime.getYear()),
                        lunarDateTime.isLeap() ?
                                EastAsianMonth.valueOf(lunarDateTime.getMonth()).withLeap() :
                                EastAsianMonth.valueOf(lunarDateTime.getMonth()), lunarDateTime.getDay())
                .transform(PlainDate.class).toTemporalAccessor()
                .atTime(lunarDateTime.getHour(), lunarDateTime.getMinute());
    }

    public static ChineseCalendar toChineseCalendar(LocalDate date) {
        return PlainDate.from(date).transform(ChineseCalendar.class);
    }

    public static List<干支> toGanZhi(LocalDate date) {
        ChineseCalendar calendar = toChineseCalendar(date);
        List<干支> ganzhi = new ArrayList<>();
        ganzhi.add(干支.getByValue(calendar.getYear().getNumber() - 1));
        ganzhi.add(干支.getByValue(calendar.getSexagesimalMonth().getNumber() - 1));
        ganzhi.add(干支.getByValue(calendar.getSexagesimalDay().getNumber() - 1));
        return ganzhi;
    }

    public static List<干支> toGanZhi(LocalDateTime dateTime) {
        List<干支> ganzhi =  toGanZhi(dateTime.toLocalDate());
        ganzhi.add(toGanZhi(ganzhi.get(2), dateTime.getHour()));
        return ganzhi;
    }

    public static 干支 toGanZhi(干支 day, int hour) {
        return 干支.getByValue(
                ((day.getGan().getValue() % 5) * 2 + (hour + 1) / 2) % 10,
                (hour + 1) / 2);
    }

    public static 干支 toGanZhi(int year) {
        return 干支.getByValue(ChineseCalendar.ofNewYear(year).getYear().getNumber() - 1);
    }

    public static 干支 toGanZhi(int year, int month) {
        return 干支.getByValue(toChineseCalendar(LocalDate.of(year, month, 15)).getSexagesimalMonth().getNumber() - 1);
    }

    public static 干支 toGanZhi(int year, int month, int day) {
        return 干支.getByValue(toChineseCalendar(LocalDate.of(year, month, day)).getSexagesimalDay().getNumber() - 1);
    }

    public static 干支 toGanZhi(int year, int month, int day, int hour) {
        return toGanZhi(toGanZhi(year, month, day), hour);
    }

    public static SolarTerm getMajorSolarTerm(LocalDateTime dateTime) {
        return SolarTerm.ofMajor((dateTime.getMonthValue() - 2 + 12) % 12);
    }

    public static LocalDateTime getLastMajorSolarTerm(LocalDateTime dateTime) {
        int year = dateTime.getYear();
        SolarTerm solarTerm = getMajorSolarTerm(dateTime);
        if (solarTerm.of(year).isAfter(dateTime)) {
            if (solarTerm.getValue() - 2 < 0) {
                year -= 1;
            }
            solarTerm = solarTerm.roll(-2);
        }
        return solarTerm.of(year);
    }

    public static LocalDateTime getNextMajorSolarTerm(LocalDateTime dateTime) {
        int year = dateTime.getYear();
        SolarTerm solarTerm = getMajorSolarTerm(dateTime);
        if (solarTerm.of(year).isBefore(dateTime)) {
            if (solarTerm.getValue() + 2 > 23) {
                year += 1;
            }
            solarTerm = solarTerm.roll(2);
        }
        return solarTerm.of(year);
    }
}
