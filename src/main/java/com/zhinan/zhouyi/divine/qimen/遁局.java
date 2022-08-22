package com.zhinan.zhouyi.divine.qimen;

import com.zhinan.zhouyi.base.阴阳;
import lombok.AllArgsConstructor;
import lombok.Getter;

public enum 遁局 {
    阳遁一局, 阳遁二局, 阳遁三局,  阳遁四局,  阳遁五局,  阳遁六局,  阳遁七局,  阳遁八局,  阳遁九局,
    阴遁一局, 阴遁二局, 阴遁三局,  阴遁四局,  阴遁五局,  阴遁六局,  阴遁七局,  阴遁八局,  阴遁九局;

    public int getValue() {
        return ordinal() / 9 * 10 + ordinal() % 9 + 1;
    }

    public String getName() {
        return name();
    }

    public 阴阳 getYinYang() {
        return 阴阳.getByValue(1 - ordinal() / 10);
    }

    public int getNum() {
        return ordinal() % 10 + 1;
    }

    public static 遁局 getByValue(int value) {
        return values()[value / 10 * 9 + (value % 10)];
    }
}
