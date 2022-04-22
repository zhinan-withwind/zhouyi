package com.zhinan.zhouyi.desc;

import com.zhinan.zhouyi.base.十神;
import com.zhinan.zhouyi.fate.命主;
import com.zhinan.zhouyi.fate.命局;

public class Descriptor {
    public static String describe(命主 zhu) {
        return 命主描述器.describe(zhu);
    }

    public static String describe(命局 pattern) {
        return 命局描述器.describe(pattern);
    }

    public static String describe(十神 shen) {
        return 十神描述器.describe(shen);
    }
}
