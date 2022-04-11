package com.zhinan.zhouyi.date;

import com.zhinan.zhouyi.base.干支;
import com.zhinan.zhouyi.util.DateUtil;

import java.time.LocalDateTime;
import java.util.List;

public class LunarDateTime {
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

    public LocalDateTime toLocalDateTime() {
        return DateUtil.toSolar(this);
    }

    public List<干支> toGanZhi() {
        return DateUtil.toGanZhi(toLocalDateTime());
    }

    public int getYear() {
        return year;
    }

    public int getMonth() {
        return month;
    }

    public int getDay() {
        return day;
    }

    public int getHour() {
        return hour;
    }

    public int getMinute() {
        return minute;
    }

    public boolean isLeap() {
        return leap;
    }
}
