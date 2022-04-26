package com.zhinan.zhouyi.effect;

import com.zhinan.zhouyi.base.五行;
import com.zhinan.zhouyi.base.元素;
import com.zhinan.zhouyi.base.地支;
import lombok.Getter;

import java.util.Arrays;
import java.util.List;

@Getter
public enum 地支半合 implements 可合化 {
    寅午半合火(new 地支[] {地支.寅, 地支.午}, 五行.火, 地支三合.寅午戌三合火),
    午戌半合火(new 地支[] {地支.午, 地支.戌}, 五行.火, 地支三合.寅午戌三合火),
    寅戌拱合火(new 地支[] {地支.寅, 地支.戌}, 五行.火, 地支三合.寅午戌三合火),

    巳酉半合金(new 地支[] {地支.巳, 地支.酉}, 五行.金, 地支三合.巳酉丑三合金),
    酉丑半合金(new 地支[] {地支.酉, 地支.丑}, 五行.金, 地支三合.巳酉丑三合金),
    巳丑拱合金(new 地支[] {地支.巳, 地支.丑}, 五行.金, 地支三合.巳酉丑三合金),

    申子半合水(new 地支[] {地支.申, 地支.子}, 五行.水, 地支三合.申子辰三合水),
    子辰半合水(new 地支[] {地支.子, 地支.辰}, 五行.水, 地支三合.申子辰三合水),
    申辰拱合水(new 地支[] {地支.申, 地支.辰}, 五行.水, 地支三合.申子辰三合水),

    亥卯半合木(new 地支[] {地支.亥, 地支.卯}, 五行.木, 地支三合.亥卯未三合木),
    卯未半合木(new 地支[] {地支.卯, 地支.未}, 五行.木, 地支三合.亥卯未三合木),
    亥未拱合木(new 地支[] {地支.亥, 地支.未}, 五行.木, 地支三合.亥卯未三合木);

    List<元素> elements;
    五行 result;
    地支三合 parent;

    地支半合(地支[] zhis, 五行 result, 地支三合 parent) {
        this.elements = Arrays.asList(zhis);
        this.result   = result;
        this.parent   = parent;
    }

    public int getValue() {return ordinal();}

    public String getName() {return name();}

    public 元素类别 getElementType() { return 元素类别.地支; }

    @Override
    public 可作用 getParent() {
        return parent;
    }
}
