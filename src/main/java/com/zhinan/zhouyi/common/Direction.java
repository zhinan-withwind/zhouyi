package com.zhinan.zhouyi.common;

import com.zhinan.zhouyi.base.五行;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Getter
@AllArgsConstructor
public enum Direction {
    北   (五行.水, Arrays.asList(Region.getByName("北京"), Region.getByName("天津"), Region.getByName("济南"), Region.getByName("青岛"))),
    西南 (五行.土, Arrays.asList(Region.getByName("成都"), Region.getByName("重庆"), Region.getByName("昆明"))),
    东   (五行.木, Arrays.asList(Region.getByName("上海"), Region.getByName("杭州"), Region.getByName("苏州"), Region.getByName("南京"))),
    东南 (五行.木, Arrays.asList(Region.getByName("福建"), Region.getByName("厦门"))),
    中   (五行.土, Arrays.asList(Region.getByName("武汉"), Region.getByName("长沙"), Region.getByName("郑州"))),
    西北 (五行.金, Arrays.asList(Region.getByName("兰州"), Region.getByName("乌鲁木齐"))),
    西   (五行.金, Arrays.asList(Region.getByName("西安"), Region.getByName("西宁"))),
    东北 (五行.土, Arrays.asList(Region.getByName("沈阳"), Region.getByName("大连"), Region.getByName("哈尔滨"))),
    南   (五行.火, Arrays.asList(Region.getByName("广州"), Region.getByName("深圳")));

    五行 wuXing;
    List<Region> cities;

    public int getValue() { return ordinal(); }

    public String getName() { return name(); }

    public static List<Direction> getByWuXing(五行 wuXing) {
        return Arrays.stream(values()).filter(d -> d.wuXing.equals(wuXing)).collect(Collectors.toList());
    }
}
