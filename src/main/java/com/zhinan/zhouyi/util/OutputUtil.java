package com.zhinan.zhouyi.util;

import com.zhinan.zhouyi.fate.luck.*;
import com.zhinan.zhouyi.out.LuckOutputter;

import java.io.IOException;
import java.time.LocalDateTime;

public class OutputUtil {
    public static void createLuckChart(String name, LocalDateTime birthday, int sex, String dir) throws IOException {
        dir = dir == null ? "/Users/withwind/Downloads/LuckChart" : dir;
        LuckOutputter.output(name, 大运.list(birthday, sex), dir);
        LuckOutputter.output(name, 年运.list(birthday, sex), dir);
        LuckOutputter.output(name, 年运.list(LocalDateTime.now().getYear(), birthday, sex), dir);
        LuckOutputter.output(name, 月运.list(LocalDateTime.now().getYear(), birthday, sex), dir);
        LuckOutputter.output(name, 日运.list(LocalDateTime.now().getYear(),
                LocalDateTime.now().getMonthValue(), birthday, sex), dir);
        LuckOutputter.output(name, 时运.list(LocalDateTime.now().getYear(),
                LocalDateTime.now().getMonthValue(), LocalDateTime.now().getDayOfMonth(), birthday, sex), dir);
    }
}
