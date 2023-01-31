package com.zhinan.zhouyi.util;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.zhinan.zhouyi.base.干支;
import com.zhinan.zhouyi.common.Region;
import com.zhinan.zhouyi.date.GanZhiDateTime;
import com.zhinan.zhouyi.date.SolarTerm;
import lombok.extern.slf4j.Slf4j;
import net.time4j.PlainDate;
import net.time4j.calendar.ChineseCalendar;
import net.time4j.calendar.EastAsianMonth;

import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Slf4j
public class DateUtil {
    private static JSONObject calendar;

    private final static String[] MONTH_NAME = new String[] {
            "正", "二", "三", "四", "五", "六", "七", "八", "九", "十", "冬", "腊"
    };

    public static ChineseCalendar toChineseCalendar(LocalDate date) {
        return PlainDate.from(date).transform(ChineseCalendar.class);
    }

    /**
     * 将日期转为八字
     * @param dateTime  要转化的日期
     * @param flag  是否区分早晚子时，
     *              true  - 区分早晚子时，23:00 - 24:00 算当天的日子，第二天的子时。
     *              false - 不区分早晚子时，23:00 - 24:00 算第二天。
     * @return 转换后的干支时间
     */
    public static GanZhiDateTime toGanZhi(LocalDateTime dateTime, boolean flag) {
        return GanZhiDateTime.of(dateTime, flag);
    }

    public static GanZhiDateTime toGanZhi(LocalDateTime dateTime) {
        return toGanZhi(dateTime, false);
    }

    public static List<LocalDateTime> findDateTime(int start, int end, 干支 year, 干支 month, 干支 day, 干支 hour) {
        List<LocalDateTime> dateTimeList = new ArrayList<>();
        int startYear = start + (year.getValue() - toGanZhi(start).getValue() + 60) % 60;
        for (int y = startYear; y < end; y+=60) {
            int m = (month.getValue() - toGanZhi(y, 1).getValue() + 60) % 60 + 1;
            if (m <= 12) {
                LocalDateTime solarTerm = SolarTerm.ofMajor((m - 2 + 12) % 12).of(y);
                int d = (day.getValue() - toGanZhi(y, m, solarTerm.getDayOfMonth()).getValue() + 60) % 60;
                int duration = (int) Duration.between(solarTerm, SolarTerm.ofMajor(m - 1).of(y)).getSeconds() / 3600 / 24;
                if (d < duration) {
                    LocalDate date = solarTerm.toLocalDate().plusDays(d);
                    int h = (hour.getValue() - toGanZhi(date.getYear(), date.getMonthValue(), date.getDayOfMonth(), 0).getValue() + 60) % 60;
                    if (h < 12) {
                        dateTimeList.add(date.atTime(h * 2, 0));
                    }
                }
            }
        }
        return dateTimeList;
    }

    public static 干支 toGanZhi(干支 day, int hour) {
        int dayGanValue = ((day.getGan().getValue() % 5) * 2 + (hour + 1) / 2) % 10;
        int dayZhiValue = (hour + 1) / 2;
        return 干支.getByValue(dayGanValue, dayZhiValue);
    }

    public static 干支 toGanZhi(int year) {
        return 干支.getByValue(ChineseCalendar.ofQingMing(year).getYear().getNumber() - 1);
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

    private static int getSolarTermYear(LocalDateTime dateTime) {
        int year = dateTime.getYear();
        // 在元旦之后，立春之前，则需要算是上一年
        if (!dateTime.isBefore(LocalDateTime.of(year, 1, 1, 0, 0)) &&
                dateTime.isBefore(SolarTerm.立春.of(year))) {
            year -= 1;
        }
        return year;
    }

    public static SolarTerm getLastMajorSolarTerm(LocalDateTime dateTime) {
        int year = getSolarTermYear(dateTime);
        SolarTerm solarTerm = SolarTerm.立春;
        for (int i = 0; i < 12; i++) {
            LocalDateTime currentSolarTermDateTime = solarTerm.of(year);
            LocalDateTime nextSolarTermDateTime    = solarTerm.roll(2).of(i < 11 ? year : year + 1);
            if (!currentSolarTermDateTime.isAfter(dateTime) && nextSolarTermDateTime.isAfter(dateTime)) {
                break;
            }
            solarTerm = solarTerm.roll(2);
        }
        return solarTerm;
    }

    public static LocalDateTime getLastMajorSolarTermDateTime(LocalDateTime dateTime) {
        return getLastMajorSolarTerm(dateTime).of(getSolarTermYear(dateTime));
    }

    public static SolarTerm getNextMajorSolarTerm(LocalDateTime dateTime) {
        return getLastMajorSolarTerm(dateTime).roll(2);
    }

    public static LocalDateTime getNextMajorSolarTermDateTime(LocalDateTime dateTime) {
        SolarTerm solarTerm = getNextMajorSolarTerm(dateTime);
        // 如果下一个节气是立春，那么则是下一年的立春
        return solarTerm.of(getSolarTermYear(dateTime) + (solarTerm.equals(SolarTerm.立春) ? 1 : 0));
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

    public static int getFullYearAge(LocalDateTime birthday, LocalDateTime now) {
        int age = 0;
        birthday = birthday.toLocalDate().atTime(0, 0);
        while (!birthday.plusYears(1L).isAfter(now)) {
            age++;
            birthday = birthday.plusYears(1L);
        }
        return age;
    }

    public static int calculateZodiacSign(LocalDateTime birthday) {
        return GanZhiDateTime.of(birthday).getGanZhiYear().getZhi().getValue() + 1;
    }
}
