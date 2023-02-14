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

    public 运势 getLuck() {
        return luck;
    }

    public int getScore() {
        return score;
    }

    public boolean isGood() {
        return good;
    }
}
