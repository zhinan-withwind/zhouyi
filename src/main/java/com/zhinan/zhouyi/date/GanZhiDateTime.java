package com.zhinan.zhouyi.date;

import com.zhinan.zhouyi.base.干支;
import com.zhinan.zhouyi.util.DateUtil;
import lombok.Setter;
import lombok.experimental.Accessors;
import net.time4j.PlainDate;
import net.time4j.calendar.ChineseCalendar;

import java.time.LocalDate;
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

    /**
     * 将日期转为干支历
     * @param dateTime  要转化的日期
     * @param flag  是否区分早晚子时，
     *              true  - 区分早晚子时，23:00 - 24:00 算当天的日子，第二天的子时。
     *              false - 不区分早晚子时，23:00 - 24:00 算第二天。
     * @return 转换后的干支时间
     */
    public static GanZhiDateTime of(LocalDateTime dateTime, boolean flag) {
        dateTime = !flag && dateTime.getHour() == 23 ? dateTime.toLocalDate().plusDays(1).atTime(0, 0) : dateTime;
        ChineseCalendar springDay = ChineseCalendar.ofNewYear(dateTime.getYear());
        ChineseCalendar calendar  = DateUtil.toChineseCalendar(dateTime.toLocalDate());
        干支 year  = 干支.getByValue(dateTime.isBefore(SolarTerm.立春.of(dateTime.getYear())) ?
                springDay.getYear().getNumber() - 2 : springDay.getYear().getNumber() - 1);
        干支 month = 干支.getByValue(dateTime.isBefore(DateUtil.getMajorSolarTerm(dateTime).of(dateTime.getYear())) ?
                calendar.getSexagesimalMonth().getNumber() - 2 : calendar.getSexagesimalMonth().getNumber() - 1);
        干支 day   = 干支.getByValue(calendar.getSexagesimalDay()  .getNumber() - 1);
        干支 hour  = DateUtil.toGanZhi(day, dateTime.getHour());
        return new GanZhiDateTime(year, month, day, hour).setDateTime(dateTime);
    }

    public static GanZhiDateTime of(LocalDateTime dateTime) {
        return of(dateTime, false);
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
    public SolarDateTime toSolarDateTime() {
        return SolarDateTime.of(toLocalDateTime());
    }

    @Override
    public LunarDateTime toLunarDateTime() {
        return LunarDateTime.of(toLocalDateTime());
    }

    @Override
    public GanZhiDateTime toGanZhiDateTime() {
        return this;
    }

    public List<干支> toGanZhiList() {
        return Arrays.asList(year, month, day, hour);
    }

    @Override
    public String toString() {
        return DateTimeFormatter.getInstance(this).format(DateFormatType.GANZHI_NUMBER);
    }
}
