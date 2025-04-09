package com.zhinan.zhouyi.test;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;

public class GanZhiTest {

    @Test
    public void testChineseCalendar() {
        LocalDateTime dateTime = LocalDateTime.of(1950, 1, 1, 0, 0);
        while (dateTime.getYear() < 2100) {
            com.zhinan.zhouyi.date.GanZhiDateTime ganZhiDateTime1 = com.zhinan.zhouyi.date.GanZhiDateTime.of(dateTime);
            run.zhinan.time.ganzhi.GanZhiDateTime ganZhiDateTime2 = run.zhinan.time.ganzhi.GanZhiDateTime.ofNoMidnight(dateTime);
            if (!ganZhiDateTime1.toString().equals(ganZhiDateTime2.toString())) {
                System.out.println(dateTime);
            }
            Assertions.assertEquals(ganZhiDateTime1.toString(), ganZhiDateTime2.toString());
            dateTime = dateTime.plusMinutes(30);
        }
    }
}
