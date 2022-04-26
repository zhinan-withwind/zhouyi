package com.zhinan.zhouyi.effect;

import com.zhinan.zhouyi.base.元素;
import com.zhinan.zhouyi.base.地支;
import lombok.Getter;

import java.util.Arrays;
import java.util.List;

@Getter
public enum 地支暗合 implements 可作用 {
    寅丑暗合(new 地支[] {地支.寅, 地支.丑}),
    卯申暗合(new 地支[] {地支.卯, 地支.申}),
    亥午暗合(new 地支[] {地支.亥, 地支.午});

    List<元素> elements;

    地支暗合(地支[] zhis) {
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
