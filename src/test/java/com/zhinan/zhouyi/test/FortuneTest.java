package com.zhinan.zhouyi.test;

import com.zhinan.zhouyi.fate.bazi.八字;
import com.zhinan.zhouyi.fortune.*;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;

public class FortuneTest {
    @Test
    public void testFortune() {
        八字 bazi = 八字.of(LocalDateTime.of(1976, 2, 11, 11, 40), 1);
        System.out.println(StudyFortune .of(bazi));
        System.out.println(CareerFortune.of(bazi));
        System.out.println(HealthFortune.of(bazi));
        System.out.println(WealthFortune.of(bazi));
        System.out.println(MarriageFortune.of(bazi));
    }
}
