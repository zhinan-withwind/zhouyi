package com.zhinan.zhouyi.fate.star;

import lombok.Getter;

@Getter
public enum 星曜 {
    // 十四正星
    紫微, 天机, 太阳, 武曲, 天同, 廉贞, 天府, 太阴, 贪狼, 巨门, 天相, 天梁, 七杀, 破军,
    // 月系诸星
    左辅, 右弼, 天刑, 天姚,
    // 年干诸星
    擎羊, 陀罗, 天魁, 天钺, 禄存,
    // 年支诸星
    天喜, 红鸾, 天马,
    // 时系诸星
    文昌, 文曲, 天空, 地劫,
    // 火星铃星
    火星, 铃星;

    public static 星曜 getByValue(int value) {
        return values()[value];
    }
}
