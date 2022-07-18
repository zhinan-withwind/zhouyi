package com.zhinan.zhouyi.date;

import com.zhinan.zhouyi.base.地支;
import com.zhinan.zhouyi.util.DateUtil;
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

    public static LunarDateTime from(LocalDateTime solarDateTime) {
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

    public GanZhiDateTime toGanZhi() {
        return DateUtil.toGanZhi(toLocalDateTime());
    }

    public 地支 getTime() {
        return 地支.getByValue((hour + 1) / 2 % 12);
    }

    @Override
    public String toString() {
        return "";
    }
}
