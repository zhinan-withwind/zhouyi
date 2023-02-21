package com.zhinan.zhouyi.fortune;

import com.zhinan.zhouyi.fate.luck.运势;

import java.util.ArrayList;
import java.util.List;

public class Fortune {
    运势 luck;
    int score;
    boolean good;

    public Fortune(运势 luck, int score, boolean good) {
        this.luck = luck;
        this.score = score;
        this.good = good;
    }

    public String getLuck() {
        return luck.getName();
    }

    public int getScore() {
        return score;
    }

    public boolean isGood() {
        return good;
    }

    @Override
    public String toString() {
        return "运势『" + luck.toString() + "』的评分为：" + score + "，这是一步" + (good ? "吉" : "凶") + "运";
    }
}
