package com.zhinan.zhouyi.name;

public enum 吉凶 {
    吉, 小吉, 中, 小凶, 凶;

    public static 吉凶 getByName(String name) {
        return valueOf(name);
    }

    public int getScore() {
        return (5 - ordinal()) * 20;
    }

    public boolean isGood() {
        return this.equals(吉);
    }

    public boolean isOK() {
        return this.ordinal() < 小凶.ordinal();
    }
}
