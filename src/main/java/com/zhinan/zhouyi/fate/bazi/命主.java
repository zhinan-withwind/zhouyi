package com.zhinan.zhouyi.fate.bazi;

import com.zhinan.zhouyi.base.五行;
import com.zhinan.zhouyi.base.天干;
import com.zhinan.zhouyi.base.生克;
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

    public 五行 get生() {
        return gan.getWuXing().get生();
    }

    public 五行 get同() {
        return gan.getWuXing().get同();
    }

    public 五行 get泄() {
        return gan.getWuXing().get泄();
    }

    public 五行 get耗() {
        return gan.getWuXing().get耗();
    }

    public 五行 get克() {
        return gan.getWuXing().get克();
    }

    public 五行 get(生克 shengKe) {
        return gan.getWuXing().getByShengKe(shengKe);
    }
}
