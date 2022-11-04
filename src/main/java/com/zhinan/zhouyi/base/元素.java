package com.zhinan.zhouyi.base;

/**
 * 可以参与合化冲作用的元素总称
 * 元素包含十 天干，十二 地支
 */
public interface 元素 {
    int getValue();
    String getName();
    五行 getWuXing();
    阴阳 getYinYang();
}
