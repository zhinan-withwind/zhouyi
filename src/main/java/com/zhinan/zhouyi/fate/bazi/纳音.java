package com.zhinan.zhouyi.fate.bazi;

import com.zhinan.zhouyi.base.五行;
import com.zhinan.zhouyi.base.阴阳;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum 纳音 {
    海中金(阴阳.阳, 五行.金),
    炉中火(阴阳.阳, 五行.火),
    大林木(阴阳.阳, 五行.木),
    路旁土(阴阳.阳, 五行.土),
    剑峰金(阴阳.阳, 五行.金),
    山头火(阴阳.阳, 五行.火),
    涧下水(阴阳.阳, 五行.水),
    城头土(阴阳.阳, 五行.土),
    白腊金(阴阳.阳, 五行.金),
    杨柳木(阴阳.阳, 五行.木),
    泉中水(阴阳.阳, 五行.水),
    屋上土(阴阳.阳, 五行.土),
    霹雳火(阴阳.阳, 五行.火),
    松柏木(阴阳.阳, 五行.木),
    长流水(阴阳.阳, 五行.水),
    砂石金(阴阳.阳, 五行.金),
    山下火(阴阳.阳, 五行.火),
    平地木(阴阳.阳, 五行.木),
    壁上土(阴阳.阳, 五行.土),
    金薄金(阴阳.阳, 五行.金),
    覆灯火(阴阳.阳, 五行.火),
    天河水(阴阳.阳, 五行.水),
    大驿土(阴阳.阳, 五行.土),
    钗环金(阴阳.阳, 五行.金),
    桑拓木(阴阳.阳, 五行.木),
    大溪水(阴阳.阳, 五行.水),
    沙中土(阴阳.阳, 五行.土),
    天上火(阴阳.阳, 五行.火),
    石榴木(阴阳.阳, 五行.木),
    大海水(阴阳.阳, 五行.水);

    阴阳 yinYang;
    五行 wuXing;

    public int getValue() {
        return ordinal();
    }

    public String getName() {
        return name();
    }

    public static 纳音 getByValue(int value) {
        return values()[value];
    }
}
