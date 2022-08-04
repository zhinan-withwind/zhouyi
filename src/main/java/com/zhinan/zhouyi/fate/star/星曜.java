package com.zhinan.zhouyi.fate.star;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum 星曜 {
    // 十四正星
    紫微(星级.主星), 天机(星级.主星), 太阳(星级.主星), 武曲(星级.主星), 天同(星级.主星), 廉贞(星级.主星),
    天府(星级.主星), 太阴(星级.主星), 贪狼(星级.主星), 巨门(星级.主星), 天相(星级.主星), 天梁(星级.主星),
    七杀(星级.主星), 破军(星级.主星),
    // 月系诸星
    左辅(星级.辅星), 右弼(星级.辅星), 天刑(星级.煞星), 天姚(星级.桃花),
    // 年干诸星
    擎羊(星级.凶星), 陀罗(星级.凶星), 天魁(星级.辅星), 天钺(星级.辅星), 禄存(星级.辅星),
    // 年支诸星
    天喜(星级.桃花), 红鸾(星级.桃花), 天马(星级.煞星),
    // 时系诸星
    文昌(星级.辅星), 文曲(星级.辅星), 天空(星级.煞星), 地劫(星级.煞星),
    // 火星铃星
    火星(星级.凶星), 铃星(星级.凶星);

    星级 type;

    public int getValue() {
        return ordinal();
    }

    public String getName() {
        return name();
    }

    public static 星曜 getByValue(int value) {
        return values()[value];
    }
}
