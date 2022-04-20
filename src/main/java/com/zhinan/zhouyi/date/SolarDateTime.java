package com.zhinan.zhouyi.date;

import com.zhinan.zhouyi.base.干支;
import com.zhinan.zhouyi.util.DateUtil;

import java.time.LocalDateTime;
import java.util.List;

public class SolarDateTime implements DateTimeHolder {
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
    public boolean isLeap() {
        return false;
    }

    @Override
    public LocalDateTime toLocalDateTime() {
        return dateTime;
    }

    @Override
    public List<干支> toGanZhi() {
        return DateUtil.toGanZhi(dateTime);
    }
}
