package com.zhinan.zhouyi.effect;

import com.zhinan.zhouyi.base.元素;
import com.zhinan.zhouyi.base.地支;
import lombok.Getter;

import java.util.Arrays;
import java.util.List;

@Getter
public enum 地支相刑 implements 可作用 {
    子卯刑(new 地支[] {地支.子, 地支.卯}),
    寅巳申三刑(new 地支[] {地支.寅, 地支.巳, 地支.申}),
    丑未戌三刑(new 地支[] {地支.丑, 地支.未, 地支.戌}),
    丑戌刑(new 地支[] {地支.丑, 地支.戌}),
    未戌刑(new 地支[] {地支.未, 地支.戌}),
    辰辰自刑(new 地支[] {地支.辰, 地支.辰}),
    午午自刑(new 地支[] {地支.午, 地支.午}),
    酉酉自刑(new 地支[] {地支.酉, 地支.酉}),
    亥亥自刑(new 地支[] {地支.亥, 地支.亥});

    List<元素> elements;

    地支相刑(地支[] zhis) {
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
