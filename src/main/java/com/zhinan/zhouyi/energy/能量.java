package com.zhinan.zhouyi.energy;

import com.alibaba.fastjson.JSONObject;
import com.zhinan.zhouyi.base.五行;
import com.zhinan.zhouyi.base.天干;
import com.zhinan.zhouyi.base.干支;
import com.zhinan.zhouyi.effect.作用元素;

import java.util.*;

public class 能量 {
    private final List<干支> ganZhiList = new ArrayList<>();
    private final int[] values = new int[5];
    private final int[] number = new int[5];
    private final int[] percentage = {0, 0, 0, 0, 0};

    private 能量() {}

    public static 能量 of(List<干支> ganZhiList) {
        int start = 0;
        能量 power;
        if (ganZhiList.size() >= 4) {
            power = ofOriginal(ganZhiList.subList(0, 4));
            start = 4;
        } else {
            power = new 能量();
        }
        for (int i = start; i < ganZhiList.size(); i++) {
            power = power.add(ganZhiList.get(i), i);
        }
        return power.calculatePercentage();
    }

    public static 能量 ofOriginal(List<干支> ganZhiList) {
        能量 power = new 能量();
        for (int i = 0; i < ganZhiList.size(); i++) {
            power.add(ganZhiList.get(i), i);
        }
        return power.calculatePercentage();
    }

    public 能量 calculatePercentage() {
        if (getTotal() != 0) {
            for (int i = 0; i < 5; i++) {
                this.percentage[i] = new Double(Math.floor((this.values[i] + 0.0) * 100.0 / this.getTotal() + 0.5)).intValue();
            }
            if (this.percentage[0] + this.percentage[1] + this.percentage[2] + this.percentage[3] + this.percentage[4] != 100) {
                if (isStrong()) {
                    if (getValue(getMing().get生()) > getValue(getMing())) {
                        calculateRemainder(getMing().get生().getValue());
                    } else {
                        calculateRemainder(getMing().getValue());
                    }
                } else {
                    int leakIndex = getMing().get泄().getValue();
                    int costIndex = getMing().get耗().getValue();
                    int curbIndex = getMing().get克().getValue();
                    int max = Math.max(Math.max(values[leakIndex], values[costIndex]), values[curbIndex]);
                    if (max == values[leakIndex]) {
                        calculateRemainder(leakIndex);
                    } else if (max == values[costIndex]) {
                        calculateRemainder(costIndex);
                    } else {
                        calculateRemainder(curbIndex);
                    }
                }
            }
        }
        return this;
    }

    public void calculateRemainder(int v) {
        int value = 100;
        for (int i = 0; i < 5; i++) {
            value = value - (i == v ? 0 : this.percentage[i]);
        }
        this.percentage[v] = value;
    }

    public int getValue(五行 wuXing) {
        return values[wuXing.getValue()];
    }

    public 能量 setValue(五行 wuXing, int value) {
        this.values[wuXing.getValue()] = value;
        return this;
    }

    public int getNumber(五行 wuXing) { return number[wuXing.getValue()]; }

    public 能量 setNumber(五行 wuXing, int number) {
        this.number[wuXing.getValue()] = number;
        return this;
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

    public 能量 add(干支 ganZhi) {
        /*
         * 计数
         */
        this.add(ganZhi.getGan().getWuXing());
        this.add(ganZhi.getZhi().getWuXing());

        /* 第一步: 本命能量
         *        本命每个 + 10
         */
        this.add(ganZhi.getGan().getWuXing(), 40);
        this.add(ganZhi.getZhi().getWuXing(), 70);


        /*
         * 第二步: 禄根能量
         *        如果是一柱或是禄根 + 5
         */
        if (禄根.is(ganZhi) || ganZhi.getGan().getWuXing().equals(ganZhi.getZhi().getWuXing())) {
            this.add(ganZhi.getGan().getWuXing(), 10);
        }

        /* 第三步: 藏干能量
         *        藏干除了本气以外，每个   + 5
         *        如果是天干的根，再每个   + 5
         *        如果根是本气则再        + 5
         *        如果是一柱的话再        + 5
         */
        for (int j = 0; j < ganZhi.getZhi().getHiddenGan().length; j++) {
            天干 gan = ganZhi.getZhi().getHiddenGan()[j];
            if (!gan.getWuXing().equals(ganZhi.getZhi().getWuXing())) {
                this.add(gan.getWuXing(), 5);
            }
            for (干支 column : ganZhiList) {
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
        for (int i = 0; i < this.ganZhiList.size(); i++) {
            this.effect(this, 作用元素.of(this.ganZhiList.get(i).getGan(), i), 作用元素.of(ganZhi.getGan(), this.ganZhiList.size()))
                .effect(this, 作用元素.of(this.ganZhiList.get(i).getGan(), i), 作用元素.of(ganZhi.getZhi(), this.ganZhiList.size()))
                .effect(this, 作用元素.of(this.ganZhiList.get(i).getZhi(), i), 作用元素.of(ganZhi.getGan(), this.ganZhiList.size()))
                .effect(this, 作用元素.of(this.ganZhiList.get(i).getZhi(), i), 作用元素.of(ganZhi.getZhi(), this.ganZhiList.size()))
                .effect(this, 作用元素.of(ganZhi.getGan(), this.ganZhiList.size()), 作用元素.of(ganZhi.getZhi(), this.ganZhiList.size()));
        }

        this.ganZhiList.add(ganZhi);

        return this;
    }

    public 能量 add(五行 wuXing) {
        return setNumber(wuXing, getNumber(wuXing) + 1);
    }

    public 能量 add(干支 ganZhi, int index) {
        int amount = index == 1 ? 150 : 100;
        this.add(ganZhi.getGan().getWuXing(), 作用.天干.effect);
        this.add(ganZhi.getZhi().get本气().getWuXing(), amount * 作用.本气.effect / 100);
        this.add(ganZhi.getZhi().get余气().getWuXing(), amount * 作用.余气.effect / 100);
        this.add(ganZhi.getZhi().get合气().getWuXing(), amount * 作用.合气.effect / 100);
        this.ganZhiList.add(ganZhi);
        return this;
    }

    public 能量 add(五行 wuXing, int value) {
        if (getValue(wuXing) + value < 0) {
            value = - getValue(wuXing);
        }
        // 记一下数
        add(wuXing);
        return setValue(wuXing, getValue(wuXing) + value);
    }

    public Integer getTotal() {
        return values[0] + values[1] + values[2] + values[3] +values[4];
    }

    public List<五行> sortAsc() {
        List<五行> result =  new ArrayList<>();
        List<Integer> source = new ArrayList<>();
        for (int i = 0; i < values.length; i++) {
            source.add(values[i] * 10 + i);
        }
        source.sort(Comparator.comparingInt(o -> o));
        for (Integer i : source) {
            result.add(五行.getByValue(i % 10));
        }
        return result;
    }

    public 五行 getMax() {
        return sortAsc().get(4);
    }

    public 五行 getMin() {
        return sortAsc().get(0);
    }

    public int getPercentage(五行 wuXing) {
        return percentage[wuXing.getValue()];
    }

    public 五行 getMing() {
        return ganZhiList.get(2).getGan().getWuXing();
    }

    public 五行 getLing() { return ganZhiList.get(1).getZhi().getWuXing(); }

    public int getSelfPart() {
        return getValue(getMing().get生()) + getValue(getMing().get同());
    }

    public int getOtherPart() {
        return getValue(getMing().get泄()) + getValue(getMing().get克()) + getValue(getMing().get耗());
    }

    public int getSelfPartPercentage() {
        return getPercentage(getMing().get生()) + getPercentage(getMing().get同());
    }

    public int getOtherPartPercentage() {
        return getPercentage(getMing().get泄()) + getPercentage(getMing().get克()) + getPercentage(getMing().get耗());
    }

    public boolean isSelfPart(五行 wuXing) {
        return wuXing.equals(getMing()) || wuXing.equals(getMing().get生());
    }

    public boolean isStrong() {
        return getSelfPart() == getOtherPart() ? isSelfPart(getLing()) : getSelfPart() > getOtherPart();
    }

    public JSONObject simplify() {
        JSONObject power = new JSONObject();
        for (五行 wuXing : 五行.values()) {
            power.put(wuXing.getName(), new JSONObject()
                    .fluentPut("值"  , this.getValue(wuXing))
                    .fluentPut("占比", String.valueOf(this.getPercentage(wuXing)))
                    .fluentPut("个数", this.getNumber(wuXing))
            );
        }
        power.put("最强", this.getMax());
        power.put("最弱", this.getMin());
        power.put("总和", this.getTotal());
        power.put("同党", this.getSelfPart());
        power.put("异党", this.getOtherPart());
        return power;
    }

    @Override
    public String toString() {
        StringBuilder result = new StringBuilder("[");
        int total = 0;
        for (五行 wuXing : 五行.values()) {
            result.append(wuXing.getName()).append(" - ").append(getValue(wuXing)).append(" | ");
            total += getValue(wuXing);
        }
        result.append("总和 - ").append(total).append("]");
        result.append("最高的是").append(getMax());
        result.append("最低的是").append(getMin());
        return result.toString();
    }
}
