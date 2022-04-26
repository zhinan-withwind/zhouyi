package com.zhinan.zhouyi.effect;

import com.zhinan.zhouyi.base.元素;
import com.zhinan.zhouyi.base.天干;
import lombok.Getter;

import java.util.Arrays;
import java.util.List;

@Getter
public enum 天干四冲 implements 可作用 {
    甲庚冲(new 天干[] {天干.甲, 天干.庚}),
    乙辛冲(new 天干[] {天干.乙, 天干.辛}),
    丙壬冲(new 天干[] {天干.丙, 天干.壬}),
    丁癸冲(new 天干[] {天干.丁, 天干.癸});

    List<元素> elements;

    天干四冲(天干[] gans) {
        this.elements = Arrays.asList(gans);
    }

    public int getValue() {return ordinal();}

    public String getName() {return name();}

    public 元素类别 getElementType() { return 元素类别.天干; }

    @Override
    public 可作用 getParent() {
        return null;
    }
}
