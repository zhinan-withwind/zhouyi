package com.zhinan.zhouyi.test;

import com.zhinan.zhouyi.date.DateFormatType;
import com.zhinan.zhouyi.date.DateTimeFormatter;
import com.zhinan.zhouyi.date.DateType;
import com.zhinan.zhouyi.fate.star.*;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;

public class StarTest {

    @Test
    void testPaiPan() {
        星盘 pan = 星盘.of(LocalDateTime.of(1976, 2, 11, 11, 40), 0);
//        星盘 pan = 星盘.of(LocalDateTime.of(1987, 2, 20, 15, 40), 0);
        System.out.println("生日：" + DateTimeFormatter.getInstance(pan.getBirthday()).format(DateFormatType.CHINESE_NUMBER, DateType.DATETIME));
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
