package com.zhinan.zhouyi.effect;

public enum 作用类别 {
    无, 合, 刑, 冲, 破, 害;

    public int getValue() { return ordinal(); }

    public String getName() { return getValue() == 0 ? "无作用" : "相" + name(); }
}
