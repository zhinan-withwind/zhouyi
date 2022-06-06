package com.zhinan.zhouyi.effect;

import lombok.Getter;

import java.util.List;

/**
 * 一组元素相互作用的关系
 * effect   作用项
 * elements 参与作用的元素
 * success  作用是否成功
 */
@Getter
public class 作用关系 {
    可作用        effect;
    boolean      success;

    List<作用元素> elements;

    public 作用关系(可作用 effect, List<作用元素> elements) {
        this.effect   = effect;
        this.elements = elements;

        /*
         * 计算元素之间的最大距离，若所有元素都距离为1，则作用成功
         */
        int distance = 0;
        for (作用元素 element : elements) {
            for (作用元素 other : elements) {
                distance = Math.max(distance, element.getDistance(other));
            }
        }
        if (distance == 1) {
             success = true;
        }
    }

    public boolean isOriginal() {
        boolean result = true;
        for (作用元素 element : elements) {
            result = result && element.isOriginal();
        }
        return result;
    }

    public boolean contains(作用关系 relation) {
        boolean result = this.effect.equals(relation.effect.getParent());
        if (result) {
            for (作用元素 e : relation.elements) {
                result = result && this.elements.contains(e);
            }
        }
        return result;
    }

    @Override
    public boolean equals(Object object) {
        boolean result = object instanceof 作用关系
                && ((作用关系) object).effect.equals(this.effect)
                && ((作用关系) object).elements.size() == this.elements.size();
        if (result) {
            for (作用元素 e : ((作用关系) object).elements) {
                result = result && this.elements.contains(e);
            }
        }
        return result;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        elements.forEach(e -> sb.append(e.toString()));
        return effect.toString() + " - " + sb;
    }
}
