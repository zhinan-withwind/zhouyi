package com.zhinan.zhouyi.effect;

import com.zhinan.zhouyi.base.元素;
import com.zhinan.zhouyi.base.地支;
import lombok.Getter;

import java.util.Arrays;
import java.util.List;

@Getter
public enum 地支相破 implements 可作用 {
    子酉破(new 地支[] {地支.子, 地支.酉}),
    寅亥破(new 地支[] {地支.寅, 地支.亥}),
    卯午破(new 地支[] {地支.卯, 地支.午}),
    辰丑破(new 地支[] {地支.辰, 地支.丑}),
    巳申破(new 地支[] {地支.巳, 地支.申}),
    未戌破(new 地支[] {地支.未, 地支.戌});

    List<元素> elements;

    地支相破(地支[] zhis) {
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
