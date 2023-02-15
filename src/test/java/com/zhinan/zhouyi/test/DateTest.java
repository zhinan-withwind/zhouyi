package com.zhinan.zhouyi.test;

import com.zhinan.zhouyi.common.Region;
import com.zhinan.zhouyi.util.DateUtil;
import org.junit.jupiter.api.Test;
import org.testng.Assert;
import run.zhinan.time.ganzhi.GanZhiDateTime;

import java.time.Duration;
import java.time.LocalDateTime;

public class DateTest {
    @Test
    public void testRegion() {
        System.out.println(Region.getByCode("650101"));
    }

    @Test
    public void testApparentSolarTime() {
        LocalDateTime dateTime = LocalDateTime.of(1976, 2, 11, 11, 40);
        System.out.println("平太阳时：" + DateUtil.toMeanSolarTime(dateTime, "120101"));
        System.out.println("真太阳时：" + DateUtil.toApparentSolarTime(dateTime, "120101"));
    }

//    @Test
//    public void testCalendar() {
//        System.out.println(DateUtil.calendar());
//    }

    @Test
    public void testTranslateDate() {
        LocalDateTime dateTime = LocalDateTime.of(1998, 1, 1, 14, 5);
        System.out.println(GanZhiDateTime.of(dateTime));
    }

    @Test
    public void testCalculateMajorSolarTerm() {
        LocalDateTime dateTime = LocalDateTime.of(1900, 1, 1, 22, 0);
        System.out.println(DateUtil.getNextMajorSolarTermDateTime(dateTime));
        System.out.println(DateUtil.getLastMajorSolarTermDateTime(dateTime));

        for (int i = 0; i < 73200; i++) {
            LocalDateTime d = dateTime.plusDays(i);
            LocalDateTime lastMajorSolarTerm = DateUtil.getLastMajorSolarTermDateTime(d);
            LocalDateTime nextMajorSolarTerm = DateUtil.getNextMajorSolarTermDateTime(d);
            if (!(lastMajorSolarTerm.isBefore(nextMajorSolarTerm) &&
                    Duration.between(lastMajorSolarTerm, nextMajorSolarTerm).getSeconds() < 32 * 24 * 3600)) {
                System.out.println("当前日期：" + d);
                System.out.println("上个节气：" + lastMajorSolarTerm);
                System.out.println("下个节气：" + nextMajorSolarTerm);
                System.out.println("====================================：");
            }
            Assert.assertTrue(lastMajorSolarTerm.isBefore(nextMajorSolarTerm) &&
                    Duration.between(lastMajorSolarTerm, nextMajorSolarTerm).getSeconds() < 32 * 24 * 3600);
        }
    }

    @Test
    public void testMidnight() {
        LocalDateTime dateTime = LocalDateTime.of(1998, 1, 2, 0, 34);
        System.out.println(DateUtil.toGanZhi(dateTime));
    }

    @Test
    public void testBazi() {
        LocalDateTime birthday = LocalDateTime.of(1988, 8, 7, 8, 58);
        System.out.println(GanZhiDateTime.of(birthday));
    }
}
