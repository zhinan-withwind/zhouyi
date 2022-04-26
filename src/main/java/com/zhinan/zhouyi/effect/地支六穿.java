package com.zhinan.zhouyi.effect;

import com.zhinan.zhouyi.base.元素;
import com.zhinan.zhouyi.base.地支;
import lombok.Getter;

import java.util.Arrays;
import java.util.List;

@Getter
public enum 地支六穿 implements 可作用 {
    子未穿(new 地支[] {地支.子, 地支.未}),
    午丑穿(new 地支[] {地支.午, 地支.丑}),
    寅巳穿(new 地支[] {地支.寅, 地支.巳}),
    卯辰穿(new 地支[] {地支.卯, 地支.辰}),
    申亥穿(new 地支[] {地支.申, 地支.亥}),
    酉戌穿(new 地支[] {地支.酉, 地支.戌});

    List<元素> elements;

    地支六穿(地支[] zhis) {
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
