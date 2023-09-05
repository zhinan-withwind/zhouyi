package com.zhinan.zhouyi.test;

import com.zhinan.zhouyi.date.DateFormatType;
import com.zhinan.zhouyi.date.DateTimeFormatter;
import com.zhinan.zhouyi.date.DateType;
import com.zhinan.zhouyi.fate.star.*;
import com.zhinan.zhouyi.util.DateUtil;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;

public class StarTest {

    @Test
    void testPaiPan() {
//        LocalDateTime birthday = LocalDateTime.of(1976, 2, 11, 11, 40);
//        LocalDateTime birthday = LocalDateTime.of(1987, 2, 20, 15, 40);
        LocalDateTime birthday = LocalDateTime.of(2020, 05, 26, 0, 48);
        LocalDateTime apparentSolarTime = DateUtil.toApparentSolarTime(birthday, null);
        星盘 pan = 星盘.of(apparentSolarTime, 1);
        System.out.println("生日：" + pan.getBirthday().toString());
        System.out.println("生日：" + pan.getBazi().toString());
        System.out.println("命局：" + pan.getPattern().name());
        System.out.println("身宫：" + pan.getBodyPalace());
        System.out.println("命主：" + pan.getFateMaster().getName());
        System.out.println("身主：" + pan.getBodyMaster().getName());
        System.out.println("四化：" + Arrays.toString(pan.getChangeList().toArray(new 化位[0])));
        List<宫位> palaceList = pan.getStarPalaceList();
        for (宫位 palace : palaceList) {
            System.out.println("-----------------------------------");
            System.out.println("名称：" + palace.getName());
            System.out.println("宫位：" + palace.getPalace().name());
            System.out.println("大限：" + palace.getStartAge() + " - " + palace.getEndAge());
            StringBuilder sb = new StringBuilder();
            for (星位 star : palace.getStars()) {
                sb.append(star.getStar().getName()).append("-").append(star.getStatus()).append(", ");
            }
            System.out.println(sb);
        }
        System.out.println("-----------------------------------");
    }
}
