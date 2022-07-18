package com.zhinan.zhouyi;

import com.zhinan.zhouyi.fate.bazi.命盘;
import com.zhinan.zhouyi.fate.bazi.简盘;
import com.zhinan.zhouyi.out.PanOutputter;

import java.time.LocalDateTime;

public class ZhouYi {
    public static void main(String[] args) {
        LocalDateTime now = LocalDateTime.now();
        简盘 pan =  命盘.of(LocalDateTime.of(1976, 2, 11, 11, 40), 1)
                .atHour(now.getYear(), now.getMonthValue(), now.getDayOfMonth(), now.getHour()).simplify();
//        简盘 pan =  命盘.of(LocalDateTime.of(1976, 2, 11, 11, 40), 1)
//                .atYear(now.getYear()).simplify();
        System.out.println(PanOutputter.of(pan).output());
    }
}
