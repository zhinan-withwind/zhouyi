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

        int[] second = {1, 3, 9, 11, 13};

        int[][] third = {
                {10, 20}, {14, 22}, {4, 12, 24}, {10, }, {10}
        };
    }
}
