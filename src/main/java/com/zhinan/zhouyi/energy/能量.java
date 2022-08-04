package com.zhinan.zhouyi.energy;

import com.zhinan.zhouyi.base.五行;
import com.zhinan.zhouyi.base.天干;
import com.zhinan.zhouyi.base.干支;
import com.zhinan.zhouyi.effect.作用元素;
import lombok.extern.slf4j.Slf4j;

import java.util.ArrayList;
import java.util.List;

@Slf4j
public class 能量 {
    private final List<干支> ganzhiList = new ArrayList<>();
    private final int[] values = new int[5];

    private 能量() {}

    public static 能量 of(List<干支> ganzhiList) {
        int start = 0;
        能量 power;
        if (ganzhiList.size() >= 4) {
            power = ofOriginal(ganzhiList.subList(0, 4));
            start = 4;
        } else {
            power = new 能量();
        }
        for (int i = start; i < ganzhiList.size(); i++) {
            power = power.add(ganzhiList.get(i), i);
        }
        return power;
    }

    public static 能量 ofOriginal(List<干支> ganzhiList) {
        能量 power = new 能量();
        for (int i = 0; i < ganzhiList.size(); i++) {
            power.add(ganzhiList.get(i), i);
        }

        return power;
    }

    public int get(五行 wuXing) {
        return values[wuXing.getValue()];
    }

    public 能量 effect(能量 power, 作用元素 orgElement, 作用元素 newElement) {
        int d = 1 << (orgElement.getDistance(newElement) - 1);
        switch (orgElement.getWuXing().compare(newElement.getWuXing())) {
            case 生:
                power.add(orgElement.getWuXing(),  5 / d);
                power.add(newElement.getWuXing(), -1 / d);
                break;
            case 同:
                power.add(orgElement.getWuXing(),  2 / d);
                power.add(newElement.getWuXing(),  2 / d);
                break;
            case 泄:
                power.add(orgElement.getWuXing(), -1 / d);
                power.add(newElement.getWuXing(),  5 / d);
                break;
            case 耗:
                power.add(orgElement.getWuXing(), -2 / d);
                power.add(newElement.getWuXing(), -5 / d);
                break;
            case 克:
                power.add(orgElement.getWuXing(), -5 / d);
                power.add(newElement.getWuXing(), -2 / d);
        }
        return power;
    }

    public 能量 add(干支 ganzhi) {
        /* 第一步: 本命能量
         *        本命每个 + 10
         */
        this.add(ganzhi.getGan().getWuXing(), 40);
        this.add(ganzhi.getZhi().getWuXing(), 70);


        /*
         * 第二步: 禄根能量
         *        如果是一柱或是禄根 + 5
         */
        if (禄根.is(ganzhi) || ganzhi.getGan().getWuXing().equals(ganzhi.getZhi().getWuXing())) {
            this.add(ganzhi.getGan().getWuXing(), 10);
        }

        /* 第三步: 藏干能量
         *        藏干除了本气以外，每个   + 5
         *        如果是天干的根，再每个   + 5
         *        如果根是本气则再        + 5
         *        如果是一柱的话再        + 5
         */
        for (int j = 0; j < ganzhi.getZhi().getHiddenGan().length; j++) {
            天干 gan = ganzhi.getZhi().getHiddenGan()[j];
            if (!gan.getWuXing().equals(ganzhi.getZhi().getWuXing())) {
                this.add(gan.getWuXing(), 5);
            }
            for (干支 column : ganzhiList) {
                if (gan.getWuXing().equals(column.getGan().getWuXing())) {
                    this.add(gan.getWuXing(), 5);
                    if (j == 0) {
                        this.add(gan.getWuXing(), 5);
                    }
                }
            }
        }

        /*
         * 第五步: 生克能量
         *        按照两个元素之间关系
         *        生：+10
         *        同：+5
         *        泄：-2
         *        耗：-5
         *        克：-10
         *        同时，生克的关系还随着距离远近的变化而变化的，距离每家1，作用减少一半
         */
        for (int i = 0; i < this.ganzhiList.size(); i++) {
            this.effect(this, 作用元素.of(this.ganzhiList.get(i).getGan(), i), 作用元素.of(ganzhi.getGan(), this.ganzhiList.size()))
                .effect(this, 作用元素.of(this.ganzhiList.get(i).getGan(), i), 作用元素.of(ganzhi.getZhi(), this.ganzhiList.size()))
                .effect(this, 作用元素.of(this.ganzhiList.get(i).getZhi(), i), 作用元素.of(ganzhi.getGan(), this.ganzhiList.size()))
                .effect(this, 作用元素.of(this.ganzhiList.get(i).getZhi(), i), 作用元素.of(ganzhi.getZhi(), this.ganzhiList.size()))
                .effect(this, 作用元素.of(ganzhi.getGan(), this.ganzhiList.size()), 作用元素.of(ganzhi.getZhi(), this.ganzhiList.size()));
        }

        this.ganzhiList.add(ganzhi);

        return this;
    }

    public 能量 add(干支 ganzhi, int index) {
        int amount = index == 1 ? 150 : 100;
        this.add(ganzhi.getGan().getWuXing(), 作用.天干.effect);
        this.add(ganzhi.getZhi().get本气().getWuXing(), amount * 作用.本气.effect / 100);
        this.add(ganzhi.getZhi().get余气().getWuXing(), amount * 作用.余气.effect / 100);
        this.add(ganzhi.getZhi().get合气().getWuXing(), amount * 作用.合气.effect / 100);
        return this;
    }

    public 能量 set(五行 wuXing, int value) {
        values[wuXing.getValue()] = value;
        return this;
    }

    public 能量 add(五行 wuXing, int value) {
        if (get(wuXing) + value < 0) {
            value = - get(wuXing);
        }
        log.debug("{} {} {}", wuXing.getName(), value < 0 ? "-" : "+", Math.abs(value));
        return set(wuXing, get(wuXing) + value);
    }

    public Integer getTotal() {
        return values[0] + values[1] + values[2] + values[3] +values[4];
    }

    @Override
    public String toString() {
        StringBuilder result = new StringBuilder("[");
        int total = 0;
        for (五行 wuXing : 五行.values()) {
            result.append(wuXing.getName()).append(" - ").append(get(wuXing)).append(" | ");
            total += get(wuXing);
        }
        result.append("总和 - ").append(total).append("]");
        return result.toString();
    }
}
