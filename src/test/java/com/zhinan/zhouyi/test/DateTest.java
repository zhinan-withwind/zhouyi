package com.zhinan.zhouyi.test;

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
}
