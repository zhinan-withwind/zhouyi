package com.zhinan.zhouyi.fate;

import com.zhinan.zhouyi.base.天干;
import com.zhinan.zhouyi.desc.命主描述器;

public class 命主 {
    天干 gan;

    private 命主(天干 gan) {
        this.gan = gan;
    }

    public static 命主 of(天干 gan) {
        return new 命主(gan);
    }

    public int getValue() {
        return gan.getValue();
    }

    public String getName() {
        return gan.getName() + gan.getWuXing().getName();
    }

    public String getDescription() {
        return 命主描述器.describe(this);
    }

}
