package com.zhinan.zhouyi.effect;

import com.zhinan.zhouyi.base.五行;
import com.zhinan.zhouyi.base.元素;
import com.zhinan.zhouyi.base.阴阳;
import lombok.Getter;

@Getter
public class 作用元素 implements 元素 {
    enum 位置 {
        年柱, 月柱, 日柱, 时柱, 大运, 流年, 流月, 流日, 流时;

        public static 位置 getByValue(int value) {
            return values()[value];
        }
    }

    元素 element;
    位置 position;
    int effect = 0;

    public 作用元素(元素 element, 位置 position) {
        this.element  = element;
        this.position = position;
    }

    @Override
    public 五行 getWuXing() {
        return element.getWuXing();
    }

    @Override
    public 阴阳 getYinYang() {
        return element.getYinYang();
    }

    @Override
    public int getValue() {
        return element.getValue();
    }

    @Override
    public String getName() {
        return element.getName();
    }

    public boolean isOriginal() {
        return this.position.ordinal() - 位置.时柱.ordinal() <= 0;
    }

    public int getDistance(作用元素 other) {
        return this.isOriginal() && other.isOriginal() ? Math.abs(this.position.ordinal() - other.position.ordinal()) : 1;
    }

    @Override
    public boolean equals(Object object) {
        return object instanceof 作用元素 && ((作用元素) object).element.equals(this.element) && ((作用元素) object).position.equals(this.position);
    }

    @Override
    public String toString() {
        return element.toString() + "(" + position.ordinal() + ")";
    }
}
