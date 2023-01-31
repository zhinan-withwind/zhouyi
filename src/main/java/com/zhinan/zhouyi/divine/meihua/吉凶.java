package com.zhinan.zhouyi.divine.meihua;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum 吉凶 {
    大吉("用生体"),
    中吉("体用比和"),
    小凶("体生用"),
    小吉("体克用"),
    大凶("用克体");

    String relation;

    public int getValue() {
        return ordinal();
    }

    public String getName() {
        return name();
    }

    public static 吉凶 getByValue(int value) {
        return values()[value];
    }
}
