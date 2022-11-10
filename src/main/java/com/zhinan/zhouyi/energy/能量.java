package com.zhinan.zhouyi.energy;

import com.zhinan.zhouyi.base.五行;
import com.zhinan.zhouyi.base.天干;
import com.zhinan.zhouyi.base.干支;
import com.zhinan.zhouyi.effect.作用元素;
import lombok.extern.slf4j.Slf4j;

import java.util.*;

@Slf4j
public class 能量 {
    private final List<干支> ganZhiList = new ArrayList<>();
    private final int[] values = new int[5];

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
        return power;
    }

    public static 能量 ofOriginal(List<干支> ganZhiList) {
        能量 power = new 能量();
        for (int i = 0; i < ganZhiList.size(); i++) {
            power.add(ganZhiList.get(i), i);
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

    public 能量 add(干支 ganZhi) {
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

    public 能量 add(干支 ganZhi, int index) {
        int amount = index == 1 ? 150 : 100;
        this.add(ganZhi.getGan().getWuXing(), 作用.天干.effect);
        this.add(ganZhi.getZhi().get本气().getWuXing(), amount * 作用.本气.effect / 100);
        this.add(ganZhi.getZhi().get余气().getWuXing(), amount * 作用.余气.effect / 100);
        this.add(ganZhi.getZhi().get合气().getWuXing(), amount * 作用.合气.effect / 100);
        this.ganZhiList.add(ganZhi);
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
        return getTotal() == 0 ? 0 : get(wuXing) * 100 / getTotal();
    }

    public 五行 getMing() {
        return ganZhiList.get(2).getGan().getWuXing();
    }

    public int getMyPart() {
        return get(getMing().get生()) + get(getMing().get同());
    }

    public int getOtherPart() {
        return get(getMing().get泄()) + get(getMing().get克()) + get(getMing().get耗());
    }

    public Map<String, Object> simplify() {
        Map<String, Object> power = new HashMap<>();
        for (五行 wuXing : 五行.values()) {
            HashMap<String, String> value = new HashMap<>();
            value.put("值"  , String.valueOf(this.get(wuXing)));
            value.put("占比", String.valueOf(this.getPercentage(wuXing)));
            power.put(wuXing.getName(), value);
        }
        power.put("最强", this.getMax());
        power.put("最弱", this.getMin());
        power.put("总和", this.getTotal());
        power.put("同党", this.getMyPart());
        power.put("异党", this.getOtherPart());
        return power;
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
        result.append("最高的是").append(getMax());
        result.append("最低的是").append(getMin());
        return result.toString();
    }
}
