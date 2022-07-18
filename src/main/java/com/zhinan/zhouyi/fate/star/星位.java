package com.zhinan.zhouyi.fate.star;

import com.zhinan.zhouyi.date.GanZhiDateTime;
import com.zhinan.zhouyi.date.LunarDateTime;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.ArrayList;
import java.util.List;

@Getter
@AllArgsConstructor
public class 星位 {
    星曜 star;
    int position;

    public static List<星位> of(LunarDateTime birthday, 命局 pattern) {
        List<星位> stars = new ArrayList<>();
        GanZhiDateTime ganZhiDateTime = birthday.toGanZhi();
        星位 紫微 = new 星位(星曜.紫微, masterStarPosition[pattern.wuXing.getValue()][birthday.getDay() - 1]);
        stars.add(紫微);
        int[] mainPosition = mainStarPosition[紫微.getPosition()];
        for (int i = 1; i < 14; i++) {
            stars.add(new 星位(星曜.getByValue(i), mainPosition[i - 1]));
        }

        for (int i = 14; i < 18; i++) {
            stars.add(new 星位(星曜.getByValue(i), otherStarPosition[i -14][birthday.getMonth() - 1]));
        }

        for (int i = 18; i < 23; i++) {
            stars.add(new 星位(星曜.getByValue(i), otherStarPosition[i -14][ganZhiDateTime.getGanZhiYear().getGan().getValue()]));
        }

        for (int i = 23; i < 26; i++) {
            stars.add(new 星位(星曜.getByValue(i), otherStarPosition[i -14][ganZhiDateTime.getGanZhiYear().getZhi().getValue()]));
        }

        for (int i = 26; i < 30; i++) {
            stars.add(new 星位(星曜.getByValue(i), otherStarPosition[i -14][ganZhiDateTime.getGanZhiHour().getZhi().getValue()]));
        }

        stars.add(new 星位(星曜.火星, fireStarPosition[ganZhiDateTime.getGanZhiYear().getZhi().getValue()][ganZhiDateTime.getGanZhiHour().getZhi().getValue()]));
        stars.add(new 星位(星曜.铃星, bellStarPosition[ganZhiDateTime.getGanZhiYear().getZhi().getValue()][ganZhiDateTime.getGanZhiHour().getZhi().getValue()]));

        return stars;
    }

    static int[][] masterStarPosition = {
            { 4,  1,  2,  5,  2,  3,  6,  3,  4,  7,  4,  5,  8,  5,  6,    9,  6,  7, 10,  7,  8, 11,  8,  9,  0,  9, 10,  1, 10, 11},
            { 9,  6, 11,  4,  1,  2, 10,  7,  0,  5,  2,  3, 11,  8,  1,    6,  3,  4,  0,  9,  2,  7,  4,  5,  1, 10,  3,  8,  5,  6},
            { 6, 11,  4,  1,  2,  7,  0,  5,  2,  3,  8,  1,  6,  3,  4,    9,  2,  7,  4,  5, 10,  3,  8,  5,  6, 11,  4,  9,  6,  7},
            {11,  4,  1,  2,  0,  5,  2,  3,  1,  6,  3,  4,  2,  7,  4,    5,  3,  8,  5,  6,  4,  9,  6,  7,  5, 10,  7,  8,  6, 11},
            { 1,  2,  2,  3,  3,  4,  4,  5,  5,  6,  6,  7,  7,  8,  8,    9,  9, 10, 10, 11, 11,  0,  0,  1,  1,  2,  2,  3,  3,  4},
    };

    static int[][] mainStarPosition = {
            {11,  9,  8,  7,  4,  4,  5,  6,  7,  8,  9, 10,  2},
            { 0, 10,  9,  8,  5,  3,  4,  5,  6,  7,  8,  9,  1},
            { 1, 11, 10,  9,  6,  2,  3,  4,  5,  6,  7,  8,  0},
            { 2,  0, 11, 10,  7,  1,  2,  3,  4,  5,  6,  7, 11},
            { 3,  1,  0, 11,  8,  0,  1,  2,  3,  4,  5,  6, 10},
            { 4,  2,  1,  0,  9, 11,  0,  1,  2,  3,  4,  5,  9},
            { 5,  3,  2,  1, 10, 10, 11,  0,  1,  2,  3,  4,  8},
            { 6,  4,  3,  2, 11,  9, 10, 11,  0,  1,  2,  3,  7},
            { 7,  5,  4,  3,  0,  8,  9, 10, 11,  0,  1,  2,  6},
            { 8,  6,  5,  4,  1,  7,  8,  9, 10, 11,  0,  1,  5},
            { 9,  7,  6,  5,  2,  6,  7,  8,  9, 10, 11,  0,  4},
            {10,  8,  7,  6,  3,  5,  6,  7,  8,  9, 10, 11,  3},
    };

    static int[][] otherStarPosition = {
            {  4,  5,  6,  7,  8,  9, 10, 11,  0,  1,  2,  3},
            { 10,  9,  8,  7,  6,  5,  4,  3,  2,  1,  0, 11},
            {  9, 10, 11,  0,  1,  2,  3,  4,  5,  6,  7,  8},
            {  1,  2,  3,  4,  5,  6,  7,  8,  9, 10, 11,  0},

            {  3,  4,  6,  7,  6,  7,  8, 10,  0,  1,  0,  0},
            {  1,  2,  4,  5,  4,  5,  7,  8, 10, 11,  0,  0},
            {  1,  0, 11, 11,  1,  0,  1,  6,  3,  3,  0,  0},
            {  7,  8,  9,  9,  7,  8,  7,  2,  5,  5,  0,  0},
            {  2,  3,  5,  6,  5,  6,  8,  9, 11,  0,  0,  0},

            {  9,  8,  7,  6,  5,  4,  3,  2,  1,  0, 11, 10},
            {  3,  2,  1,  0, 11, 10,  9,  8,  7,  6,  5,  4},
            {  2, 11,  9,  5,  2, 11,  9,  5,  2, 11,  9,  5},

            { 10,  9,  8,  7,  6,  5,  4,  3,  2,  1,  0, 11},
            {  4,  5,  6,  7,  8,  9, 10, 11,  0,  1,  2,  3},
            { 11, 10,  9,  8,  7,  6,  5,  4,  3,  2,  1,  0},
            { 11,  0,  1,  2,  3,  4,  5,  6,  7,  8,  9, 10},
    };

    static int[][] fireStarPosition = {
            {  2,  3,  4,  5,  6,  7,  8,  9, 10, 11,  0,  1},
            {  3,  4,  5,  6,  7,  8,  9, 10, 11,  0,  1,  2},
            {  1,  2,  3,  4,  5,  6,  7,  8,  9, 10, 11,  0},
            {  9, 10, 11,  0,  1,  2,  3,  4,  5,  6,  7,  8},
            {  2,  3,  4,  5,  6,  7,  8,  9, 10, 11,  0,  1},
            {  3,  4,  5,  6,  7,  8,  9, 10, 11,  0,  1,  2},
            {  1,  2,  3,  4,  5,  6,  7,  8,  9, 10, 11,  0},
            {  9, 10, 11,  0,  1,  2,  3,  4,  5,  6,  7,  8},
            {  2,  3,  4,  5,  6,  7,  8,  9, 10, 11,  0,  1},
            {  3,  4,  5,  6,  7,  8,  9, 10, 11,  0,  1,  2},
            {  1,  2,  3,  4,  5,  6,  7,  8,  9, 10, 11,  0},
            {  9, 10, 11,  0,  1,  2,  3,  4,  5,  6,  7,  8},
    };

    static int[][] bellStarPosition = {
            { 10, 11,  0,  1,  2,  3,  4,  5,  6,  7,  8,  9},
            { 10, 11,  0,  1,  2,  3,  4,  5,  6,  7,  8,  9},
            {  3,  4,  5,  6,  7,  8,  9, 10, 11,  0,  1,  2},
            { 10, 11,  0,  1,  2,  3,  4,  5,  6,  7,  8,  9},
            { 10, 11,  0,  1,  2,  3,  4,  5,  6,  7,  8,  9},
            { 10, 11,  0,  1,  2,  3,  4,  5,  6,  7,  8,  9},
            {  3,  4,  5,  6,  7,  8,  9, 10, 11,  0,  1,  2},
            { 10, 11,  0,  1,  2,  3,  4,  5,  6,  7,  8,  9},
            { 10, 11,  0,  1,  2,  3,  4,  5,  6,  7,  8,  9},
            { 10, 11,  0,  1,  2,  3,  4,  5,  6,  7,  8,  9},
            {  3,  4,  5,  6,  7,  8,  9, 10, 11,  0,  1,  2},
            { 10, 11,  0,  1,  2,  3,  4,  5,  6,  7,  8,  9},
    };

}
