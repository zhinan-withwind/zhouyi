package com.zhinan.zhouyi.test;

import com.zhinan.zhouyi.fate.star.*;
import com.zhinan.zhouyi.util.DateUtil;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;

public class StarTest {

    @Test
    void testPaiPan() {
        LocalDateTime birthday = LocalDateTime.of(1998, 1, 1, 13, 5);
        LocalDateTime apparentSolarTime = DateUtil.toApparentSolarTime(birthday, "320700");
//        星盘 pan = 星盘.of(, 1);
        星盘 pan = 星盘.of(apparentSolarTime, 1);
//        星盘 pan = 星盘.of(, 0);
        System.out.println("生日：" + pan.getBirthday());
        System.out.println("命局：" + pan.getPattern().name());
        System.out.println("身宫：" + pan.getBodyPalace());
        System.out.println("四化：" + Arrays.toString(pan.getChangeList().toArray(new 化位[0])));
        List<宫位> palaceList = pan.getStarPalaceList();
        for (宫位 palace : palaceList) {
            System.out.println("-----------------------------------");
            System.out.println("名称：" + palace.getName());
            System.out.println("宫位：" + palace.getPalace().name());
            System.out.println("大限：" + palace.getStartAge() + " - " + palace.getEndAge());
            StringBuilder sb = new StringBuilder();
            for (星位 star : palace.getStars()) {
                sb.append(star.getStar().getName()).append(", ");
            }
            System.out.println(sb);
        }
        System.out.println("-----------------------------------");
    }
}
