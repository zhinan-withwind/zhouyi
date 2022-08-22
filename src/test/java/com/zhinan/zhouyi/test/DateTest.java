package com.zhinan.zhouyi.test;

import com.zhinan.zhouyi.date.*;
import com.zhinan.zhouyi.util.DateUtil;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;

public class DateTest {

    @Test
    public void testApparentSolarTime() {
        LocalDateTime dateTime = LocalDateTime.of(1976, 2, 11, 11, 40);
        System.out.println("平太阳时：" + DateUtil.toMeanSolarTime(dateTime, "120101"));
        System.out.println("真太阳时：" + DateUtil.toApparentSolarTime(dateTime, "120101"));
    }

    @Test
    public void testCalendar() {
//        System.out.println(DateUtil.calendar());
    }

    @Test
    public void testFormat() {
        LocalDateTime dateTime = DateTimeParser.parse("2009-四月-初十 2:01", 0);
        System.out.println(DateTimeFormatter.getInstance(SolarDateTime.of(dateTime))
                .format(DateFormatType.ARABIC_NUMBER, DateType.DAY));
        System.out.println(DateTimeFormatter.getInstance(SolarDateTime.of(dateTime))
                .format(DateFormatType.ARABIC_NUMBER, DateType.SHORT_DATE));
        System.out.println(DateTimeFormatter.getInstance(SolarDateTime.of(dateTime))
                .format(DateFormatType.ARABIC_NUMBER, DateType.FULL_DATE));
        System.out.println(DateTimeFormatter.getInstance(SolarDateTime.of(dateTime))
                .format(DateFormatType.ARABIC_NUMBER, DateType.DATETIME));
    }
}
