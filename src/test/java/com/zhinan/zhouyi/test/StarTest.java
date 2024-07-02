package com.zhinan.zhouyi.test;

import com.zhinan.zhouyi.fate.star.*;
import com.zhinan.zhouyi.util.DateUtil;
import lombok.SneakyThrows;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import org.junit.jupiter.api.Test;
import org.testng.Assert;
import run.zhinan.time.ganzhi.GanZhiYear;

import java.io.FileInputStream;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;

public class StarTest {

//    @Test
    void testPaiPan() {
//        LocalDateTime birthday = LocalDateTime.of(1976, 2, 11, 11, 40);
//        LocalDateTime birthday = LocalDateTime.of(1987, 2, 20, 15, 40);
        LocalDateTime birthday = LocalDateTime.of(2008, 9, 28, 20, 0);
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
                sb.append(star.getStar().getName()).append("-").append(star.getStatus());
                for (化位 c : pan.getChangeList()) {
                    if (c.getPosition() == star.getStar().getValue()) {
                        sb.append("[").append(c.getChange().getName()).append("]");
                    }
                }
                sb.append("  ");
            }
            System.out.println(sb);
        }
        System.out.println("-----------------------------------");
    }

    @Test
    @SneakyThrows
    void testPaiPanWithUseCase() {
        String filePath = "/Users/withwind/Documents/work/2023/鹤望繁星/紫微/测试用例-V1.xlsx";

        FileInputStream inputStream = new FileInputStream(filePath);
        Workbook workbook = WorkbookFactory.create(inputStream);
        Sheet sheet = workbook.getSheetAt(0);
        for (int r = 1; r < 32; r++) {
//            if (r == 16 || r == 17 || r == 18 || r == 20 || r == 21 || r == 29 || r == 30) continue;
            Row row = sheet.getRow(r);
            if (row != null) validData(row);
        }
    }

    void validData(Row row) {
        int index = new Double(row.getCell(0).getNumericCellValue()).intValue();
        System.out.print("Test Case " + index + " begin: ");
        try {
            LocalDateTime birthday = row.getCell(1).getLocalDateTimeCellValue();
            int sex = row.getCell(2).getStringCellValue().equals("男") ? 1 : 0;
            星盘 pan = 星盘.of(birthday, sex);
            List<宫位> palaceList = pan.getStarPalaceList();
            Assert.assertEquals(GanZhiYear.of(pan.getBirthday().getYear()) + pan.getBirthday().toString().substring(4), row.getCell(3).getStringCellValue());
            Assert.assertEquals(pan.getPattern().getName(), row.getCell(4).getStringCellValue());
            宫位 bodyPalace = palaceList.get(pan.getBodyPalace());
            Assert.assertEquals(bodyPalace.getName(), row.getCell(5).getStringCellValue());
            for (int i = 0; i < palaceList.size(); i++) {
                宫位 palace = palaceList.get(i);
                Assert.assertEquals(palace.getName(), row.getCell(i * 4 + 6).getStringCellValue());
                Assert.assertEquals(palace.getPalace().getName(), row.getCell(i * 4 + 7).getStringCellValue());
                Assert.assertEquals(palace.getStartAge() + "-" + palace.getEndAge(), row.getCell(i * 4 + 8).getStringCellValue());
                StringBuilder sb = new StringBuilder();
                for (星位 star : palace.getStars()) {
                    sb.append(star.getStar().getName())
                            .append(star.getStatus() == null ? "" : "-")
                            .append(star.getStatus() == null ? "" : star.getStatus().getName()).append(",");
                    for (化位 change : pan.getChangeList()) {
                        if (change.getPosition() == star.getStar().getValue()) {
                            sb.append(change.getChange().getName()).append(",");
                        }
                    }
                }
                Assert.assertEquals(sb.toString(),
                        row.getCell(i * 4 + 9) == null ? "" : row.getCell(i * 4 + 9).getStringCellValue() +
                                (sb.length() == 0 ? "" : ","));
                System.out.print(".");
            }
            System.out.println("Test Case " + index + " Passed!!!");
        } catch (AssertionError error) {
            error.printStackTrace();
            System.out.println("Test Case " + index + " Failed!!!");
        }
    }
}
