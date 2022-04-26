package com.zhinan.zhouyi.effect;

import com.zhinan.zhouyi.base.五行;
import com.zhinan.zhouyi.base.元素;
import com.zhinan.zhouyi.base.地支;
import lombok.Getter;

import java.util.Arrays;
import java.util.List;

@Getter
public enum 地支六合 implements 可合化 {
    子丑合化土(new 地支[] {地支.子, 地支.丑}, 五行.土),
    寅亥合化木(new 地支[] {地支.寅, 地支.亥}, 五行.木),
    卯戌合化火(new 地支[] {地支.卯, 地支.戌}, 五行.火),
    辰酉合化金(new 地支[] {地支.辰, 地支.酉}, 五行.金),
    巳申合化水(new 地支[] {地支.巳, 地支.申}, 五行.水),
    午未合化土(new 地支[] {地支.午, 地支.未}, 五行.土);

    List<元素> elements;
    五行 result;

    地支六合(地支[] zhis, 五行 result) {
        this.elements = Arrays.asList(zhis);
        this.result   = result;
    }

    public int getValue() {return ordinal();}

    public String getName() {return name();}

    public 元素类别 getElementType() { return 元素类别.地支; }

    @Override
    public 可作用 getParent() {
        return null;
    }
}
