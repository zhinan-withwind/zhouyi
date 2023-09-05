package com.zhinan.zhouyi.fate.star;

import com.zhinan.zhouyi.base.地支;
import lombok.Getter;
import run.zhinan.time.ganzhi.GanZhiDateTime;
import run.zhinan.time.lunar.LunarDateTime;

import java.util.ArrayList;
import java.util.List;

import static com.zhinan.zhouyi.base.地支.*;

@Getter
public class 星位 {
    星曜 star;
    状态 status;
    int position;

    public 星位(星曜 star, int position) {
        this.star     = star;
        this.position = position;
        this.status   = 状态.getByStarAndPalace(this);
    }

    public static List<星位> of(LunarDateTime birthday, 命局 pattern) {
        List<星位> stars = new ArrayList<>();
        GanZhiDateTime ganZhiDateTime = birthday.toGanZhiDateTime();

        // 安紫微星
        星位 紫微 = new 星位(星曜.紫微, masterStarPosition[pattern.wuXing.getValue()][birthday.getDay() - 1].getValue());
        stars.add(紫微);
        地支[] mainPosition = mainStarPosition[紫微.getPosition()];

        // 安十四正星
        for (int i = 1; i < 14; i++) {
            stars.add(new 星位(星曜.getByValue(i), mainPosition[i - 1].getValue()));
        }

        int month = birthday.toLunarDate().getLunarMonth().isLeap() ?
                birthday.toLunarDate().getLunarMonth().getIndex() : birthday.getMonth();
        // 安月系诸星
        for (int i = 14; i < 18; i++) {
            stars.add(new 星位(星曜.getByValue(i), otherStarPosition[i -14][month - 1].getValue()));
        }

        for (int i = 18; i < 23; i++) {
            stars.add(new 星位(星曜.getByValue(i), otherStarPosition[i -14][ganZhiDateTime.getGanZhiYear().getGan().getValue() - 1].getValue()));
        }

        for (int i = 23; i < 26; i++) {
            stars.add(new 星位(星曜.getByValue(i), otherStarPosition[i -14][ganZhiDateTime.getGanZhiYear().getZhi().getValue() - 1].getValue()));
        }

        for (int i = 26; i < 30; i++) {
            stars.add(new 星位(星曜.getByValue(i), otherStarPosition[i -14][ganZhiDateTime.getGanZhiTime().getZhi().getValue() - 1].getValue()));
        }

        stars.add(new 星位(星曜.火星, fireStarPosition[ganZhiDateTime.getGanZhiYear().getZhi().getValue() - 1][ganZhiDateTime.getGanZhiTime().getZhi().getValue() - 1].getValue()));
        stars.add(new 星位(星曜.铃星, bellStarPosition[ganZhiDateTime.getGanZhiYear().getZhi().getValue() - 1][ganZhiDateTime.getGanZhiTime().getZhi().getValue() - 1].getValue()));

        return stars;
    }

    static 地支[][] masterStarPosition = {
            {辰, 丑, 寅, 巳, 寅, 卯, 午, 卯, 辰, 未, 辰, 巳, 申, 巳, 午, 酉, 午, 未, 戌, 未, 申, 亥, 申, 酉, 子, 酉, 戌, 丑, 戌, 亥},
            {酉, 午, 亥, 辰, 丑, 寅, 戌, 未, 子, 巳, 寅, 卯, 亥, 申, 丑, 午, 卯, 辰, 子, 酉, 寅, 未, 辰, 巳, 丑, 戌, 卯, 申, 巳, 午},
            {午, 亥, 辰, 丑, 寅, 未, 子, 巳, 寅, 卯, 申, 丑, 午, 卯, 辰, 酉, 寅, 未, 辰, 巳, 戌, 卯, 申, 巳, 午, 亥, 辰, 酉, 午, 未},
            {亥, 辰, 丑, 寅, 子, 巳, 寅, 卯, 丑, 午, 卯, 辰, 寅, 未, 辰, 巳, 卯, 申, 巳, 午, 辰, 酉, 午, 未, 巳, 戌, 未, 申, 午, 亥},
            {丑, 寅, 寅, 卯, 卯, 辰, 辰, 巳, 巳, 午, 午, 未, 未, 申, 申, 酉, 酉, 戌, 戌, 亥, 亥, 子, 子, 丑, 丑, 寅, 寅, 卯, 卯, 辰},
    };

    static 地支[][] mainStarPosition = {
            {亥, 酉, 申, 未, 辰, 辰, 巳, 午, 未, 申, 酉, 戌, 寅},
            {子, 戌, 酉, 申, 巳, 卯, 辰, 巳, 午, 未, 申, 酉, 丑},
            {丑, 亥, 戌, 酉, 午, 寅, 卯, 辰, 巳, 午, 未, 申, 子},
            {寅, 子, 亥, 戌, 未, 丑, 寅, 卯, 辰, 巳, 午, 未, 亥},
            {卯, 丑, 子, 亥, 申, 子, 丑, 寅, 卯, 辰, 巳, 午, 戌},
            {辰, 寅, 丑, 子, 酉, 亥, 子, 丑, 寅, 卯, 辰, 巳, 酉},
            {巳, 卯, 寅, 丑, 戌, 戌, 亥, 子, 丑, 寅, 卯, 辰, 申},
            {午, 辰, 卯, 寅, 亥, 酉, 戌, 亥, 子, 丑, 寅, 卯, 未},
            {未, 巳, 辰, 卯, 子, 申, 酉, 戌, 亥, 子, 丑, 寅, 午},
            {申, 午, 巳, 辰, 丑, 未, 申, 酉, 戌, 亥, 子, 丑, 巳},
            {酉, 未, 午, 巳, 寅, 午, 未, 申, 酉, 戌, 亥, 子, 辰},
            {戌, 申, 未, 午, 卯, 巳, 午, 未, 申, 酉, 戌, 亥, 卯},
    };

    static 地支[][] otherStarPosition = {
            { 辰, 巳, 午, 未, 申, 酉, 戌, 亥, 子, 丑, 寅, 卯},  // 已核对
            { 戌, 酉, 申, 未, 午, 巳, 辰, 卯, 寅, 丑, 子, 亥},  // 已核对
            { 酉, 戌, 亥, 子, 丑, 寅, 卯, 辰, 巳, 午, 未, 申},  // 已核对
            { 丑, 寅, 卯, 辰, 巳, 午, 未, 申, 酉, 戌, 亥, 子},  // 已核对

            { 卯, 辰, 午, 未, 午, 未, 酉, 戌, 子, 丑, 子, 子},  // 已核对
            { 丑, 寅, 辰, 巳, 辰, 巳, 未, 申, 戌, 亥, 子, 子},  // 已核对
            { 丑, 子, 亥, 亥, 丑, 子, 丑, 午, 卯, 卯, 子, 子},  // 已核对
            { 未, 申, 酉, 酉, 未, 申, 未, 寅, 巳, 巳, 子, 子},  // 已核对
            { 寅, 卯, 巳, 午, 巳, 午, 申, 酉, 亥, 子, 子, 子},  // 已核对

            { 酉, 申, 未, 午, 巳, 辰, 卯, 寅, 丑, 子, 亥, 戌},  // 已核对
            { 卯, 寅, 丑, 子, 亥, 戌, 酉, 申, 未, 午, 巳, 辰},  // 已核对
            { 寅, 亥, 申, 巳, 寅, 亥, 申, 巳, 寅, 亥, 申, 巳},  // 已核对

            { 戌, 酉, 申, 未, 午, 巳, 辰, 卯, 寅, 丑, 子, 亥},  // 已核对
            { 辰, 巳, 午, 未, 申, 酉, 戌, 亥, 子, 丑, 寅, 卯},  // 已核对
            { 亥, 戌, 酉, 申, 未, 午, 巳, 辰, 卯, 寅, 丑, 子},  // 已核对
            { 亥, 子, 丑, 寅, 卯, 辰, 巳, 午, 未, 申, 酉, 戌},  // 已核对
    };

    static 地支[][] fireStarPosition = {
            { 寅, 卯, 辰, 巳, 午, 未, 申, 酉, 戌, 亥, 子, 丑},  // 已核对
            { 卯, 辰, 巳, 午, 未, 申, 酉, 戌, 亥, 子, 丑, 寅},  // 已核对
            { 丑, 寅, 卯, 辰, 巳, 午, 未, 申, 酉, 戌, 亥, 子},  // 已核对
            { 酉, 戌, 亥, 子, 丑, 寅, 卯, 辰, 巳, 午, 未, 申},  // 已核对
            { 寅, 卯, 辰, 巳, 午, 未, 申, 酉, 戌, 亥, 子, 丑},  // 已核对
            { 卯, 辰, 巳, 午, 未, 申, 酉, 戌, 亥, 子, 丑, 寅},  // 已核对
            { 丑, 寅, 卯, 辰, 巳, 午, 未, 申, 酉, 戌, 亥, 子},  // 已核对
            { 酉, 戌, 亥, 子, 丑, 寅, 卯, 辰, 巳, 午, 未, 申},  // 已核对
            { 寅, 卯, 辰, 巳, 午, 未, 申, 酉, 戌, 亥, 子, 丑},  // 已核对
            { 卯, 辰, 巳, 午, 未, 申, 酉, 戌, 亥, 子, 丑, 寅},  // 已核对
            { 丑, 寅, 卯, 辰, 巳, 午, 未, 申, 酉, 戌, 亥, 子},  // 已核对
            { 酉, 戌, 亥, 子, 丑, 寅, 卯, 辰, 巳, 午, 未, 申},  // 已核对
    };

    static 地支[][] bellStarPosition = {
            { 戌, 亥, 子, 丑, 寅, 卯, 辰, 巳, 午, 未, 申, 酉},
            { 戌, 亥, 子, 丑, 寅, 卯, 辰, 巳, 午, 未, 申, 酉},
            { 卯, 辰, 巳, 午, 未, 申, 酉, 戌, 亥, 子, 丑, 寅},
            { 戌, 亥, 子, 丑, 寅, 卯, 辰, 巳, 午, 未, 申, 酉},
            { 戌, 亥, 子, 丑, 寅, 卯, 辰, 巳, 午, 未, 申, 酉},
            { 戌, 亥, 子, 丑, 寅, 卯, 辰, 巳, 午, 未, 申, 酉},
            { 卯, 辰, 巳, 午, 未, 申, 酉, 戌, 亥, 子, 丑, 寅},
            { 戌, 亥, 子, 丑, 寅, 卯, 辰, 巳, 午, 未, 申, 酉},
            { 戌, 亥, 子, 丑, 寅, 卯, 辰, 巳, 午, 未, 申, 酉},
            { 戌, 亥, 子, 丑, 寅, 卯, 辰, 巳, 午, 未, 申, 酉},
            { 卯, 辰, 巳, 午, 未, 申, 酉, 戌, 亥, 子, 丑, 寅},
            { 戌, 亥, 子, 丑, 寅, 卯, 辰, 巳, 午, 未, 申, 酉},
    };
}
