package com.zhinan.zhouyi.util;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.zhinan.zhouyi.base.干支;
import com.zhinan.zhouyi.common.Region;
import com.zhinan.zhouyi.date.GanZhiDateTime;
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
    private static JSONObject calendar;

    private final static String[] MONTH_NAME = new String[] {
            "正", "二", "三", "四", "五", "六", "七", "八", "九", "十", "冬", "腊"
    };

    public static ChineseCalendar toChineseCalendar(LocalDate date) {
        return PlainDate.from(date).transform(ChineseCalendar.class);
    }

    public static GanZhiDateTime toGanZhi(LocalDateTime dateTime) {
        ChineseCalendar calendar = toChineseCalendar(dateTime.toLocalDate());
        return new GanZhiDateTime(
                干支.getByValue(calendar.getYear().getNumber() - 1),
                干支.getByValue(calendar.getSexagesimalMonth().getNumber() - 1),
                干支.getByValue(calendar.getSexagesimalDay().getNumber() - 1),
                toGanZhi(干支.getByValue(calendar.getSexagesimalDay().getNumber() - 1), dateTime.getHour()))
                .setDateTime(dateTime);
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

    public static LocalDateTime toMeanSolarTime(LocalDateTime dateTime, String regionCode) {
        Region region = Region.getByCode(regionCode);
        return region != null ? dateTime.plusSeconds((long) ((region.getLongitude() - 120) * 240)) : dateTime;
    }

    public static LocalDateTime toApparentSolarTime(LocalDateTime dateTime, String region) {
        double N0   = 79.6764 + 0.2422 *(dateTime.getYear() - 1985) - Math.floor(0.25 * (dateTime.getYear() - 1985));
        double sita = 2 * Math.PI * (dateTime.getDayOfYear() - N0) / 365.2422;
        double t = 0.0028 - 1.9857 * Math.sin(sita) + 9.9059 * Math.sin(2 * sita) - 7.0924 * Math.cos(sita) - 0.6882 * Math.cos(2 * sita);
        return toMeanSolarTime(dateTime, region)
                .plusMinutes(new Double(Math.floor(t)).longValue())
                .plusSeconds(new Double(Math.floorMod(new Double(t * 100).intValue(), 100) * 0.6).longValue());
    }

    public static JSONArray yearCalendar(int y) {
        JSONArray year = new JSONArray();
        ChineseCalendar chineseCalendar = ChineseCalendar.ofNewYear(y);
        int months = chineseCalendar.isLeapYear() ? 13 : 12;
        for (int m = 0; m < months; m++) {
            EastAsianMonth eastAsianMonth = chineseCalendar.getMonth();
            JSONObject month = new JSONObject();
            month.put("value", (eastAsianMonth.isLeap() ? 20 : 0) + eastAsianMonth.getNumber());
            month.put("name",  (eastAsianMonth.isLeap() ? "闰" : "") + MONTH_NAME[eastAsianMonth.getNumber() - 1] + "月");
            month.put("days", chineseCalendar.lengthOfMonth());
            year.add(month);
            chineseCalendar = chineseCalendar.plus(1L, ChineseCalendar.Unit.MONTHS);
        }
        return year;
    }

    public static JSONObject calendar() {
        if (calendar == null) {
            calendar = new JSONObject();
            for (int y = 1900; y < 2101; y++) {
                calendar.put(String.valueOf(y), yearCalendar(y));
            }
        }
        return calendar;
    }
}
