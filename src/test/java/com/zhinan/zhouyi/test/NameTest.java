package com.zhinan.zhouyi.test;

import com.zhinan.zhouyi.base.五行;
import com.zhinan.zhouyi.fate.bazi.八字;
import com.zhinan.zhouyi.name.姓名;
import com.zhinan.zhouyi.name.汉字;
import com.zhinan.zhouyi.name.起名;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;

public class NameTest {

    @Test
    public void testDictionary() {
//        姓名 name = 姓名.of("廖秭耨").with(八字.of(LocalDateTime.of(2008, 9, 25, 8, 0), 1));
//        姓名 name = 姓名.of("胡随风").with(八字.of(LocalDateTime.of(1976, 2, 11, 11, 40), 1));
//        姓名 name = 姓名.of("张婷婷").with(八字.of(LocalDateTime.of(1987, 2, 20, 15, 40), 0));
//        姓名 name = 姓名.of("浦雪萍").with(八字.of(LocalDateTime.of(1977, 1, 22, 20, 40), 0));
//        System.out.println(name.getFateScore());
//        System.out.println(name.getGridScore());
//        System.out.println(name.getScore());

//        List<int[]> recommendList = 起名.getRecommendList(1, 2);
//        StringBuilder sb = new StringBuilder();
//        for (int[] CD : recommendList) {
//            sb.append(Arrays.toString(CD)).append(", ").append("\t");
//        }
//        System.out.println(sb);

//        List<String> nameList = 起名.recommendName("胡", LocalDateTime.of(1976, 2, 11, 11, 40), 1);
//        System.out.println(Arrays.toString(nameList.toArray()));
//        System.out.println(nameList.size());

//        List<汉字> charList= 汉字.get(14, 五行.火);
//        for (汉字 c : charList) {
//            System.out.println("张高" + c.getSimplified());
//        }

        int[] second = {9, 11, 13, 19, 20, 23};

        int[][] third = {
                {4, 12, 14, 16, 22, 24}, {10, }, {10, 12, 20}, {4, 14, 16}, {5, 15}, {10, 12}
        };

        for(int s = 0; s < second.length; s++) {
            List<汉字> sList = 汉字.get(second[s], 五行.火);
            for (int t = 0; t < third[s].length; t++) {
                List<汉字> tList = 汉字.get(third[s][t], 五行.土);
                System.out.println("笔画组合：" + second[s] + " - " + third[s][t]);
                int n = 0;
                for (汉字 sc : sList) {
                    for (汉字 tc : tList) {
                        System.out.print("景" + sc.getSimplified() + tc.getSimplified() + ", \t");
                        System.out.print(++n % 10 == 0 ? System.lineSeparator() : "");
                    }
                }
                System.out.println();
            }
        }
    }
}
