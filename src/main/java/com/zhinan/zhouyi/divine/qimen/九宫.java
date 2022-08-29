package com.zhinan.zhouyi.divine.qimen;

import com.zhinan.zhouyi.base.地支;
import com.zhinan.zhouyi.divine.common.八卦;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum 九宫 {
    坎一(1, 八卦.坎, 8, 八卦.坤, 地支.子, 地支.子, 九星.天蓬, 八门.休, new int[] {1, 1, 6, 6}),
    坤二(2, 八卦.坤, 5, 八卦.巽, 地支.未, 地支.申, 九星.天芮, 八门.死, new int[] {2, 5, 8, 10}),
    震三(3, 八卦.震, 3, 八卦.离, 地支.卯, 地支.卯, 九星.天冲, 八门.伤, new int[] {3, 3, 4, 8}),
    巽四(4, 八卦.巽, 2, 八卦.兑, 地支.辰, 地支.巳, 九星.天辅, 八门.杜, new int[] {3, 4, 5, 8}),
    中五(5, 八卦.坤, 0, null, null, null, 九星.天禽, null, new int[] {5, 5, 10, 10}),
    乾六(6, 八卦.乾, 7, 八卦.艮, 地支.戌, 地支.亥, 九星.天心, 八门.开, new int[] {1, 4, 6, 9}),
    兑七(7, 八卦.兑, 6, 八卦.坎, 地支.酉, 地支.酉, 九星.天柱, 八门.惊, new int[] {2, 4, 7, 9}),
    艮八(8, 八卦.艮, 4, 八卦.震, 地支.丑, 地支.寅, 九星.天任, 八门.生, new int[] {5, 7, 8, 10}),
    离九(9, 八卦.离, 1, 八卦.乾, 地支.午, 地支.午, 九星.天英, 八门.景, new int[] {2, 3, 7, 9});

    public final static 九宫[] valuesByClockwise = new 九宫[] {坎一, 艮八, 震三, 巽四, 离九, 坤二, 兑七, 乾六};

    int alterValue;
    八卦 alterGua;
    int initValue;
    八卦 initGua;
    地支 zhi1;
    地支 zhi2;
    九星 star;
    八门 door;
    int[] numbers;

    public int getValue() {
        return ordinal();
    }

    public String getName() {
        return name();
    }

    public String getShortName() {
        return getAlterGua().getName();
    }

    public String getFullName() {
        return getName() + "宫";
    }

    public static 九宫 getPalace(int value) {
        return values()[(value - 1 + 9) % 9];
    }

    public static 九宫 getByZhi(地支 zhi) {
        九宫 palace = null;
        for (九宫 p : 九宫.values()) {
            if (zhi.equals(p.zhi1) || zhi.equals(p.zhi2)) {
                palace = p;
                break;
            }
        }
        return palace;
    }

    /**
     * 按照数值取下一个
     * @return 下一个宫位
     */
    public 九宫 getNextPalace() {
        return getPalace(alterValue + 1);
    }

    /**
     * 按照数值取上一个
     * @return 上一个宫位
     */
    public 九宫 getLastPalace() {
        return getPalace(alterValue - 1);
    }

    /**
     * 按照位置顺时针取下一个
     * @return 顺时针下一个宫位
     */
    public 九宫 getPalaceAfter() {
        for (int i = 0; i < 8; i++) {
            if (valuesByClockwise[i].alterValue == alterValue) {
                return valuesByClockwise[(i + 1) % 8];
            }
        }
        return 中五;
    }

    /**
     * 按照位置逆时针取下一个
     * @return 逆时针下一个宫位
     */
    public 九宫 getPalaceBefore() {
        for (int i = 0; i < 8; i++) {
            if (valuesByClockwise[i].alterValue == alterValue) {
                return valuesByClockwise[(i - 1 + 8) % 8];
            }
        }
        return 中五;
    }

    public 九宫 getOppositePalace() {
        return getPalaceAfter().getPalaceAfter().getPalaceAfter().getPalaceAfter();
    }

}
