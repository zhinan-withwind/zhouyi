package com.zhinan.zhouyi.util;

import com.zhinan.zhouyi.fate.bazi.八字;
import com.zhinan.zhouyi.fortune.StudyFortune;

import java.util.List;
import java.util.Map;
import java.util.TreeMap;

public class FortuneUtil {
    public static Map<Integer, Integer> calculateFortuneScore(List<八字> baziList) {
        Map<Integer, Integer> result = new TreeMap<>();
        baziList.forEach(bazi -> {
            int score = StudyFortune.of(bazi).getScore();
            System.out.print("\r" + bazi + " - " + score);
            result.put(score, result.computeIfAbsent(score, s -> 0)+1);
        });
        result.keySet().forEach(key -> System.out.println(key + "\t - " + result.get(key)));

        return result;
    }
}
