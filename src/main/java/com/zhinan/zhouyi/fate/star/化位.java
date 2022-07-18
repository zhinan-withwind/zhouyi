package com.zhinan.zhouyi.fate.star;

import lombok.AllArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@AllArgsConstructor
public class 化位 {
    四化 change;
    int position;

    @Override
    public String toString() {
        return change.name() + ": " + position;
    }

    public static List<化位> of(int yearValue) {
        List<化位> result = new ArrayList<>();
        for (int i = 0; i < 4; i++) {
            result.add(new 化位(四化.getByValue(i), changeStarPosition[i][yearValue]));
        }
        return result;
    }

    static int[][] changeStarPosition = {
            { 5,  1,  4,  7,  8,  3,  2,  9, 11, 13},
            {13, 11,  1,  4,  7,  8,  3,  2,  0,  9},
            { 3,  0, 26,  1, 15, 11,  7, 27, 14,  7},
            { 2,  7,  5,  9,  1, 27,  4, 26,  3,  8},
    };
}
