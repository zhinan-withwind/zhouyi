package com.zhinan.zhouyi.effect;

import com.zhinan.zhouyi.base.元素;
import com.zhinan.zhouyi.base.地支;
import lombok.Getter;

import java.util.Arrays;
import java.util.List;

@Getter
public enum 地支六冲 implements 可作用 {
    子午冲(new 地支[] {地支.子, 地支.午}),
    丑未冲(new 地支[] {地支.丑, 地支.未}),
    寅申冲(new 地支[] {地支.寅, 地支.申}),
    卯酉冲(new 地支[] {地支.卯, 地支.酉}),
    辰戌冲(new 地支[] {地支.辰, 地支.戌}),
    巳亥冲(new 地支[] {地支.巳, 地支.亥});

    List<元素> elements;

    地支六冲(地支[] zhis) {
        this.elements = Arrays.asList(zhis);
    }

    public int getValue() {return ordinal();}

    public String getName() {return name();}

    public 元素类别 getElementType() { return 元素类别.地支; }

    @Override
    public 可作用 getParent() {
        return null;
    }
}
