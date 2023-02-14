package com.zhinan.zhouyi.date;

import com.zhinan.zhouyi.base.地支;
import lombok.Getter;
import net.time4j.PlainDate;
import net.time4j.calendar.ChineseCalendar;
import net.time4j.calendar.CommonElements;
import net.time4j.calendar.EastAsianMonth;
import net.time4j.calendar.EastAsianYear;

import java.time.LocalDateTime;

@Getter
public class LunarDateTime extends BaseDateTime implements DateTimeHolder {
    private final int year;
    private final int month;
    private final int day;
    private final int hour;
    private final int minute;
    private final boolean leap;

    public LunarDateTime(int year, int month, int day, int hour, int minute, boolean leap) {
        this.year = year;
        this.month = month;
        this.day = day;
        this.hour = hour;
        this.minute = minute;
        this.leap = leap;
    }

    public static LunarDateTime of(int year, int month, int day, int hour, int minute, boolean leap) {
        return new LunarDateTime(year, month, day, hour, minute, leap);
    }

    public static LunarDateTime of(String dateString) {
        int l = dateString.indexOf(" ") + 1;
        if (l == 0) {
            l = dateString.length();
        }
        String date = dateString.substring(0, l - 1);
        String time = dateString.substring(l);

        String[] ymd = date.split("-");

        int year = Integer.parseInt(ymd[0]);
        int month = Integer.parseInt(ymd[1]);
        int day = Integer.parseInt(ymd[2]);

        boolean isLeap = false;
        if (month > 20) {
            isLeap = true;
            month -= 20;
        }

        int hour = 0,minute = 0,second = 0;
        if (l != dateString.length()) {
            String[] hms = time.split(":");
            hour   = hms.length > 0 && !hms[0].equals("") ? Integer.parseInt(hms[0]) : 0;
            minute = hms.length > 1 && !hms[1].equals("") ? Integer.parseInt(hms[1]) : 0;
        }
        return LunarDateTime.of(year, month, day, hour, minute, isLeap);
    }

    public static LunarDateTime of(LocalDateTime solarDateTime) {
        ChineseCalendar calendar = PlainDate.from(solarDateTime.toLocalDate()).transform(ChineseCalendar.class);
        return LunarDateTime.of(calendar.getInt(CommonElements.RELATED_GREGORIAN_YEAR),
                calendar.getMonth().getNumber(), calendar.getDayOfMonth(),
                solarDateTime.getHour(), solarDateTime.getMinute(), calendar.getMonth().isLeap());
    }

    public LocalDateTime toLocalDateTime() {
        return ChineseCalendar.of(EastAsianYear.forGregorian(this.getYear()),
                        this.isLeap() ?
                                EastAsianMonth.valueOf(this.getMonth()).withLeap() :
                                EastAsianMonth.valueOf(this.getMonth()), this.getDay())
                .transform(PlainDate.class).toTemporalAccessor()
                .atTime(this.getHour(), this.getMinute());
    }

    @Override
    public SolarDateTime toSolarDateTime() {
        return SolarDateTime.of(toLocalDateTime());
    }

    @Override
    public LunarDateTime toLunarDateTime() {
        return this;
    }

    @Override
    public GanZhiDateTime toGanZhiDateTime() {
        return GanZhiDateTime.of(toLocalDateTime());
    }

    public 地支 getTime() {
        return 地支.getByValue((hour + 1) / 2 % 12);
    }

    @Override
    public String toString() {
        return format(DateFormatType.CHINESE_NUMBER);
    }
}
