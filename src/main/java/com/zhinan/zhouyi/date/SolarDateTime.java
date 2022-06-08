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
    public GanZhiDateTime toGanZhi() {
        return DateUtil.toGanZhi(dateTime);
    }
}
