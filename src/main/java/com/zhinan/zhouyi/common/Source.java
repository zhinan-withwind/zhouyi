package com.zhinan.zhouyi.common;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public enum Source {
    明易, 星鹤;

    private static Source current = 星鹤;

    public static void init(Source source) {
        current = source;
        log.info("周易体系已设置为 {}", current.getName());
    }

    public static Source getCurrent() {
        return current;
    }

    public int getValue() { return ordinal(); }

    public String getName() { return name(); }
}
