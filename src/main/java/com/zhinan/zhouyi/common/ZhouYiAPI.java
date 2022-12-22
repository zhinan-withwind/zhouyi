package com.zhinan.zhouyi.common;

import com.zhinan.zhouyi.desc.周易描述器;
import com.zhinan.zhouyi.desc.基础描述器;

public class ZhouYiAPI {
    public static void init(Source source, String lineSeparator) {
        Source.init(source);
        周易描述器.init();
        if (lineSeparator != null) 基础描述器.lineSeparator = lineSeparator;
    }
}
