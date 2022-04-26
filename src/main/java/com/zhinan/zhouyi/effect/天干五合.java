package com.zhinan.zhouyi.effect;

import com.zhinan.zhouyi.base.五行;
import com.zhinan.zhouyi.base.元素;
import com.zhinan.zhouyi.base.天干;
import lombok.Getter;

import java.util.Arrays;
import java.util.List;

@Getter
public enum 天干五合 implements 可合化 {
    甲己合化土(new 天干[] {天干.甲, 天干.己}, 五行.土),
    乙庚合化金(new 天干[] {天干.乙, 天干.庚}, 五行.金),
    丙辛合化水(new 天干[] {天干.丙, 天干.辛}, 五行.水),
    丁壬合化木(new 天干[] {天干.丁, 天干.壬}, 五行.木),
    戊癸合化火(new 天干[] {天干.戊, 天干.癸}, 五行.火);

    List<元素> elements;
    五行       result;

    天干五合(天干[] gans, 五行 result) {
        this.elements = Arrays.asList(gans);
        this.result   = result;
    }

    public int getValue() {return ordinal();}

    public String getName() {return name();}

    public 元素类别 getElementType() { return 元素类别.天干; }

    @Override
    public 可作用 getParent() {
        return null;
    }


}
