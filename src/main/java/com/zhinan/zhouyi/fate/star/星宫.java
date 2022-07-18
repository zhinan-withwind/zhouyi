package com.zhinan.zhouyi.fate.star;

public enum 星宫 {
    命宫, 兄弟, 夫妻, 子女, 财帛, 疾厄, 迁移, 交友, 官禄, 田宅, 福德, 父母, 身宫;

    public static 星宫 getByValue(int value) {
        return values()[value % 12];
    }
}
