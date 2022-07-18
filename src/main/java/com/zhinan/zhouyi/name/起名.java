package com.zhinan.zhouyi.name;

import com.zhinan.zhouyi.base.五行;
import com.zhinan.zhouyi.fate.bazi.八字;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class 起名 {
    public static List<int[]> getRecommendList(int A, int B) {
        List<int[]> result = new ArrayList<>();
        for (int C = 1; C <= 30; C++) {
            for (int D = 1; D <= 30; D++) {
                int human = B + C;
                int earth = C + D;
                int total = (A == 1 ? 0 : A) + B + C + (D == 1 ? 0 : D);
                if (数理.isGood(human) && 数理.isGood(earth) && 数理.isGood(total)) {
                    result.add(new int[] {C, D});
                }
            }
        }
        return result;
    }

    public static List<String> recommendName(String surname, LocalDateTime birthday, int sex) {
        List<String> nameList = new ArrayList<>();
        八字 bazi = 八字.of(birthday, sex);
        五行 firstGoodGod  = bazi.getGridPattern().getFirstGoodGod();
        五行 secondGoodGod = bazi.getGridPattern().getSecondGoodGod();
        int A = surname.length() == 1 ? 1 : 汉字.get(surname.substring(0, 1)).getStrokeNum();
        int B = surname.length() == 1 ? 汉字.get(surname).getStrokeNum() : 汉字.get(surname.substring(0, 1)).getStrokeNum();
        List<int[]> numberList = getRecommendList(A, B);

        for (int[] numbers : numberList) {
            List<汉字> firstCharList  = new ArrayList<>();
            List<汉字> secondCharList = new ArrayList<>();
            if (汉字.dictMap.get(numbers[0] + "-" + firstGoodGod .getValue()) != null)
                firstCharList .addAll(汉字.dictMap.get(numbers[0] + "-" + firstGoodGod .getValue()));
//            if (汉字.dictMap.get(numbers[0] + "-" + secondGoodGod.getValue()) != null)
//                firstCharList .addAll(汉字.dictMap.get(numbers[0] + "-" + secondGoodGod.getValue()));
            if (汉字.dictMap.get(numbers[1] + "-" + firstGoodGod .getValue()) != null)
                secondCharList.addAll(汉字.dictMap.get(numbers[1] + "-" + firstGoodGod .getValue()));
//            if (汉字.dictMap.get(numbers[1] + "-" + secondGoodGod.getValue()) != null)
//                secondCharList.addAll(汉字.dictMap.get(numbers[1] + "-" + secondGoodGod.getValue()));
            System.out.println(firstCharList.size());
            System.out.println(secondCharList.size());
            for (汉字 firstChar : firstCharList) {
                for (汉字 secondChar : secondCharList) {
                    nameList.add(surname + firstChar.getSimplified() + secondChar.getSimplified());
                }
            }
        }

        return nameList;
    }
}
