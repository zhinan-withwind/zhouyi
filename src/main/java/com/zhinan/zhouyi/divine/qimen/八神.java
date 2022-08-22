package com.zhinan.zhouyi.divine.qimen;

import com.zhinan.zhouyi.base.五行;
import com.zhinan.zhouyi.base.阴阳;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum 八神 {
    值符("符", 五行.木), 螣蛇("蛇", 五行.火),
    太阴("阴", 五行.金), 六合("六", 五行.木),
    白虎("白", 五行.金), 玄武("玄", 五行.水),
    九地("地", 五行.土), 九天("天", 五行.金);

    String shortName;
    五行 wuXing;

    public int getValue() {
        return ordinal();
    }

    public String getName() {
        return name();
    }
}
