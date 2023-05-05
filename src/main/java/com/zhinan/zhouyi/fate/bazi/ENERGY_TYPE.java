package com.zhinan.zhouyi.fate.bazi;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum ENERGY_TYPE {
    STRONG("身强"), WEAK("身弱"), BALANCE("均衡");

    String name;

    public int getValue() { return ordinal(); }

    public static ENERGY_TYPE getByValue(int value) {
        return values()[value];
    }
}
