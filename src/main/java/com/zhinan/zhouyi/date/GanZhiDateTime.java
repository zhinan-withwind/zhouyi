package com.zhinan.zhouyi.date;

import com.zhinan.zhouyi.base.干支;
import com.zhinan.zhouyi.util.DateUtil;
import lombok.Setter;
import lombok.experimental.Accessors;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;

@Setter
@Accessors(chain = true)
public class GanZhiDateTime extends BaseDateTime implements DateTimeHolder {
    LocalDateTime dateTime;
    干支 year;
    干支 month;
    干支 day;
    干支 hour;

    public GanZhiDateTime(干支 year, 干支 month, 干支 day, 干支 hour) {
        this.year = year;
        this.month = month;
        this.day = day;
        this.hour = hour;
    }

    public static GanZhiDateTime from(LocalDateTime dateTime) {
        return DateUtil.toGanZhi(dateTime);
    }

    public 干支 getGanZhiYear() { return year; }

    public 干支 getGanZhiMonth() { return month; }

    public 干支 getGanZhiDay() { return day; }

    public 干支 getGanZhiHour() { return hour; }

    @Override
    public int getYear() {
        return year.getZhi().getValue() + 1;
    }

    @Override
    public int getMonth() {
        return (month.getZhi().getValue() - 2 + 12) % 12 + 1;
    }

    @Override
    public int getDay() {
        return day.getZhi().getValue() + 1;
    }

    @Override
    public int getHour() {
        return hour.getZhi().getValue() + 1;
    }

    @Override
    public int getMinute() {
        return 0;
    }

    @Override
    public LocalDateTime toLocalDateTime() {
        return dateTime;
    }

    @Override
    public GanZhiDateTime toGanZhi() {
        return this;
    }

    public List<干支> toGanZhiList() {
        return Arrays.asList(year, month, day, hour);
    }
}
