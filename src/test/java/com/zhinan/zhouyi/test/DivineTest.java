package com.zhinan.zhouyi.test;

import com.zhinan.zhouyi.divine.liuyao.六爻排盘;
import com.zhinan.zhouyi.divine.meihua.梅花易数;
import com.zhinan.zhouyi.out.LiuYaoOutputter;
import com.zhinan.zhouyi.out.MeiHuaOutputter;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;

public class DivineTest {

    @Test
    public void testLiuYao() {
        System.out.println(LiuYaoOutputter.output(六爻排盘.init("功能还有问题吗？", 六爻排盘.QUESTION_TYPE.情感.ordinal())).output());
        System.out.println(MeiHuaOutputter.output(梅花易数.init("今天是否会下雨", LocalDateTime.now (), null, null, null)).output());
    }
}
