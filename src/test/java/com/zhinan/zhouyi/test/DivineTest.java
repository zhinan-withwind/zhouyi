package com.zhinan.zhouyi.test;

import com.zhinan.zhouyi.divine.liuyao.六爻;
import com.zhinan.zhouyi.divine.meihua.梅花易数;
import com.zhinan.zhouyi.out.LiuYaoOutputter;
import com.zhinan.zhouyi.out.MeiHuaOutputter;
import org.junit.jupiter.api.Test;

public class DivineTest {

    @Test
    public void testLiuYao() {
        System.out.println(LiuYaoOutputter.output(六爻.init("功能还有问题吗？", 六爻.QUESTION_TYPE.情感.ordinal())).output());
        System.out.println(MeiHuaOutputter.output(梅花易数.init("今天是否会下雨", (int) '晴', (int) '雨')).output());
    }
}
