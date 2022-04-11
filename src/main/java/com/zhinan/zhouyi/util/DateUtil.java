package com.zhinan.zhouyi.util;

import com.zhinan.zhouyi.base.干支;
import com.zhinan.zhouyi.date.LunarDateTime;
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

    public static List<干支> toGanZhi(LocalDate date) {
        List<干支> ganzhi = new ArrayList<>();
        ChineseCalendar calendar = PlainDate.from(date).transform(ChineseCalendar.class);
        ganzhi.add(干支.getByValue(calendar.getYear().getNumber()));
        ganzhi.add(干支.getByValue(calendar.getSexagesimalMonth().getNumber()));
        ganzhi.add(干支.getByValue(calendar.getSexagesimalDay().getNumber()));
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
        return 干支.getByValue(ChineseCalendar.ofNewYear(year).getYear().getNumber());
    }

    public static 干支 toGanZhi(int year, int month) {
        return toGanZhi(LocalDate.of(year, month, 15)).get(1);
    }

    public static 干支 toGanZhi(int year, int month, int day) {
        return toGanZhi(LocalDate.of(year, month, day)).get(2);
    }

    public static 干支 toGanZhi(int year, int month, int day, int hour) {
        return toGanZhi(toGanZhi(year, month, day), hour);
    }
}
