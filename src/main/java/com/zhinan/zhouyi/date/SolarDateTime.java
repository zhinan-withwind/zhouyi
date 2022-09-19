package com.zhinan.zhouyi.date;

import com.zhinan.zhouyi.util.DateUtil;

import java.time.LocalDateTime;

public class SolarDateTime extends BaseDateTime implements DateTimeHolder {
    LocalDateTime dateTime;

    public SolarDateTime(LocalDateTime dateTime) {
        this.dateTime = dateTime;
    }

    public static SolarDateTime of(LocalDateTime dateTime) {
        return new SolarDateTime(dateTime);
    }

    @Override
    public int getYear() {
        return dateTime.getYear();
    }

    @Override
    public int getMonth() {
        return dateTime.getMonthValue();
    }

    @Override
    public int getDay() {
        return dateTime.getDayOfMonth();
    }

    @Override
    public int getHour() {
        return dateTime.getHour();
    }

    @Override
    public int getMinute() {
        return dateTime.getMinute();
    }

    @Override
    public LocalDateTime toLocalDateTime() {
        return dateTime;
    }

    @Override
    public SolarDateTime toSolarDateTime() {
        return this;
    }

    @Override
    public LunarDateTime toLunarDateTime() {
        return LunarDateTime.of(toLocalDateTime());
    }

    @Override
    public GanZhiDateTime toGanZhiDateTime() {
        return GanZhiDateTime.of(toLocalDateTime());
    }

    @Override
    public String toString() {
        return format(DateFormatType.ARABIC_NUMBER);
    }
}
