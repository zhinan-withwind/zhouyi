package com.zhinan.zhouyi.fate.star;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.zhinan.zhouyi.util.FileUtil;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum 状态 {
    庙(3), 旺(2), 地(1), 利(0), 平(-1), 失(-2), 陷(-3);

    int effect;

    public int getValue() {
        return ordinal();
    }

    public String getName() {
        return name();
    }

    public static 状态 getByName(String name) {
        状态 result = null;
        for (状态 status : values()) {
            if (status.getName().equals(name)) {
                result = status;
            }
        }
        return result;
    }

    public static 状态 getByValue(Integer value) {
        return value == null ? null : values()[value];
    }

    public static 状态 getByStarAndPalace(星位 starPalace) {
//        return getStarPalaceStatus()[starPalace.getStar().ordinal()][(starPalace.getPosition() + 10) % 12];
        return getStarPalaceStatus()[starPalace.getStar().ordinal()][starPalace.getPosition() % 12];
    }

    private static 状态[][] getStarPalaceStatus() {
        if (!loaded) {
            JSONArray data = JSON.parseArray(FileUtil.loadResource("data/starStatus.json"));
            for (int s = 0; s < data.size(); s++) {
                for (int z = 0; z < 12; z++) {
                    Integer value = (Integer) ((JSONArray) data.get(s)).get(z);
                    starPalaceStatus[s][z] = getByValue(value);
                }
            }
            loaded = true;
        }
        return starPalaceStatus;
    }

    @Override
    public String toString() {
        return getName();
    }

    private static boolean loaded = false;

    private final static 状态[][] starPalaceStatus = new 状态[星曜.values().length][12];

    private final static 状态[][] STAR_PALACE_STATUS = {
            // 子      丑       寅       卯      辰      巳      午      未       申      酉      戌       亥
            { 状态.平, 状态.庙, 状态.庙, 状态.旺, 状态.地, 状态.旺, 状态.庙, 状态.庙, 状态.旺, 状态.旺, 状态.地, 状态.旺, }, //紫 微 星     1
            { 状态.庙, 状态.陷, 状态.地, 状态.旺, 状态.利, 状态.平, 状态.庙, 状态.陷, 状态.地, 状态.旺, 状态.利, 状态.平, }, //天 机 星     2
            { 状态.陷, 状态.失, 状态.旺, 状态.庙, 状态.旺, 状态.旺, 状态.庙, 状态.地, 状态.利, 状态.平, 状态.陷, 状态.陷, }, //太 阳 星     3
            { 状态.旺, 状态.庙, 状态.地, 状态.利, 状态.庙, 状态.平, 状态.旺, 状态.庙, 状态.地, 状态.利, 状态.庙, 状态.平, }, //武 曲 星     4
            { 状态.旺, 状态.失, 状态.利, 状态.庙, 状态.平, 状态.庙, 状态.陷, 状态.失, 状态.旺, 状态.平, 状态.平, 状态.庙, }, //天 同 星     5
            { 状态.平, 状态.利, 状态.庙, 状态.平, 状态.利, 状态.陷, 状态.平, 状态.利, 状态.庙, 状态.平, 状态.利, 状态.陷, }, //廉 贞 星     6
            { 状态.庙, 状态.庙, 状态.庙, 状态.地, 状态.旺, 状态.地, 状态.旺, 状态.庙, 状态.地, 状态.旺, 状态.旺, 状态.地, }, //天 府 星     7
            { 状态.庙, 状态.庙, 状态.失, 状态.陷, 状态.陷, 状态.陷, 状态.陷, 状态.平, 状态.利, 状态.旺, 状态.旺, 状态.庙, }, //太 阴 星     8
            { 状态.旺, 状态.庙, 状态.平, 状态.地, 状态.庙, 状态.陷, 状态.旺, 状态.庙, 状态.平, 状态.利, 状态.庙, 状态.陷, }, //贪 狼 星     9
            { 状态.旺, 状态.失, 状态.庙, 状态.庙, 状态.平, 状态.平, 状态.旺, 状态.平, 状态.庙, 状态.庙, 状态.平, 状态.旺, }, //巨 门 星    10
            { 状态.庙, 状态.庙, 状态.庙, 状态.陷, 状态.平, 状态.地, 状态.地, 状态.地, 状态.庙, 状态.陷, 状态.地, 状态.地, }, //天 相 星    11
            { 状态.庙, 状态.旺, 状态.庙, 状态.旺, 状态.庙, 状态.陷, 状态.庙, 状态.旺, 状态.陷, 状态.地, 状态.庙, 状态.陷, }, //天 梁 星    12
            { 状态.旺, 状态.庙, 状态.庙, 状态.旺, 状态.地, 状态.平, 状态.旺, 状态.庙, 状态.庙, 状态.旺, 状态.庙, 状态.平, }, //七 杀 星    13
            { 状态.庙, 状态.旺, 状态.地, 状态.陷, 状态.旺, 状态.平, 状态.庙, 状态.旺, 状态.地, 状态.陷, 状态.旺, 状态.平, }, //破 军 星    14

            { 状态.旺, 状态.庙, 状态.旺, 状态.庙, 状态.庙, 状态.旺, 状态.旺, 状态.庙, 状态.旺, 状态.旺, 状态.庙, 状态.旺, }, //左 辅 星    27
            { 状态.旺, 状态.庙, 状态.旺, 状态.庙, 状态.庙, 状态.旺, 状态.旺, 状态.庙, 状态.旺, 状态.旺, 状态.庙, 状态.旺, }, //右 弼 星    28
            { null  , null  , null  , null  , null  , null  , null  , null  , null  , null  , null  , null  , }, //天 刑 星    32
            { null  , null  , null  , null  , null  , null  , null  , null  , null  , null  , null  , null  , }, //天 姚 星    31

            { 状态.旺, 状态.庙, null  , 状态.陷, 状态.庙, null  , 状态.陷, 状态.庙, null  , 状态.旺, 状态.庙, null  , }, //擎 羊 星    17
            { null  , 状态.庙, 状态.陷, null  , 状态.庙, 状态.陷, null  , 状态.庙, 状态.陷, null  , 状态.庙, 状态.陷, }, //陀 罗 星    18
            { 状态.庙, 状态.旺, 状态.庙, 状态.庙, 状态.庙, 状态.庙, 状态.庙, 状态.旺, 状态.庙, 状态.庙, 状态.庙, 状态.庙, }, //天 魁 星    25
            { 状态.庙, 状态.旺, 状态.庙, 状态.庙, 状态.庙, 状态.庙, 状态.庙, 状态.旺, 状态.庙, 状态.庙, 状态.庙, 状态.庙, }, //天 钺 星    26
            { null  , null  , null  , null  , null  , null  , null  , null  , null  , null  , null  , null  , }, //禄 存 性    22
            { null  , null  , null  , null  , null  , null  , null  , null  , null  , null  , null  , null  , }, //天 喜 星    30
            { null  , null  , null  , null  , null  , null  , null  , null  , null  , null  , null  , null  , }, //红 鸾 星    29
            { null  , null  , null  , null  , null  , null  , null  , null  , null  , null  , null  , null  , }, //天 马 星    33

            { 状态.地, 状态.庙, 状态.陷, 状态.旺, 状态.地, 状态.庙, 状态.陷, 状态.利, 状态.地, 状态.庙, 状态.陷, 状态.利, }, //文 昌 星    19
            { 状态.地, 状态.庙, 状态.平, 状态.旺, 状态.地, 状态.庙, 状态.陷, 状态.旺, 状态.地, 状态.庙, 状态.陷, 状态.旺, }, //文 曲 星    20
            { null  , null  , null  , null  , null  , null  , null  , null  , null  , null  , null  , null  , }, //天 空 星    23
            { null  , null  , null  , null  , null  , null  , null  , null  , null  , null  , null  , null  , }, //地 劫 星    24

            { 状态.陷, 状态.地, 状态.庙, 状态.利, 状态.陷, 状态.地, 状态.庙, 状态.利, 状态.陷, 状态.地, 状态.庙, 状态.利, }, //火    星    15
            { 状态.陷, 状态.地, 状态.庙, 状态.利, 状态.陷, 状态.地, 状态.庙, 状态.利, 状态.陷, 状态.地, 状态.庙, 状态.利, }, //铃    星    16
    };
}
