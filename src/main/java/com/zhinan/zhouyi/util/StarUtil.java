package com.zhinan.zhouyi.util;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.zhinan.zhouyi.base.地支;
import com.zhinan.zhouyi.fate.star.星曜;
import com.zhinan.zhouyi.fate.star.状态;
import lombok.SneakyThrows;
import org.apache.poi.ss.usermodel.*;

import java.io.FileInputStream;
import java.io.FileWriter;
import java.util.ArrayList;
import java.util.List;

public class StarUtil {
    private final static String dataFile = "/Users/withwind/Documents/work/2023/鹤望繁星/紫微/星曜庙旺平地陷V3.xlsx";
    private final static String jsonFile = "/Users/withwind/Documents/work/2023/鹤望繁星/紫微/starStatus.json";
    private final static int[]  position = {1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 27, 28, 32, 31, 17, 18, 25, 26, 22, 30, 29, 33, 19, 20, 23, 24, 15, 16};

    public static void main(String[] args) {
        exportStarStatusData();
    }

    public static void exportStarStatusData() {
        writeJsonToFile(JSON.toJSONString(readStarStatusData(dataFile), SerializerFeature.PrettyFormat, SerializerFeature.DisableCircularReferenceDetect), jsonFile);
    }

    @SneakyThrows
    public static List<List<Integer>> readStarStatusData(String filePath) {
        FileInputStream inputStream = new FileInputStream(filePath);
        Workbook workbook = WorkbookFactory.create(inputStream);

        Sheet sheet = workbook.getSheetAt(0);
        List<List<Integer>> result = new ArrayList<>();
        for (星曜 star : 星曜.values()) {
            List<Integer> starStatusList = new ArrayList<>();
            for (地支 zhi : 地支.values()) {
                String statusName = readData(sheet, 5 + zhi.getValue(), position[star.getValue()]);
                状态 status = statusName != null ? 状态.getByName(statusName) : null;
                starStatusList.add(status != null ? status.getValue() : null);
            }
            result.add(starStatusList);
        }
        return result;
    }


    private static String readData(Sheet sheet, int r, int c) {
        String result = null;
        Row row = sheet.getRow(r);
        if (row != null) {
            Cell cell = row.getCell(c);
            if (cell != null) {
                result = cell.getStringCellValue();
            }
        }
        return result;
    }

    @SneakyThrows
    public static void writeJsonToFile(String json, String filePath) {
        try (FileWriter writer = new FileWriter(filePath)) {
            writer.write(json);
        }
    }
}
