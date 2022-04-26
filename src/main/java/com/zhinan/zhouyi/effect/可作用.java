package com.zhinan.zhouyi.effect;

import com.zhinan.zhouyi.base.元素;

import java.util.List;

public interface 可作用 {
    int       getValue();
    String    getName();

    元素类别   getElementType ();

    List<元素> getElements();
    可作用     getParent();
}

