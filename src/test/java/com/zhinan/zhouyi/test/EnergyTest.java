package com.zhinan.zhouyi.test;

import com.zhinan.zhouyi.base.五行;
import com.zhinan.zhouyi.energy.能量;
import com.zhinan.zhouyi.fate.八字;
import com.zhinan.zhouyi.fate.命盘;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;

import static org.junit.jupiter.api.Assertions.*;

@Slf4j
class EnergyTest {
    static LocalDateTime tt   = LocalDateTime.of(1987, 2, 20,  3, 40);
    static LocalDateTime ww   = LocalDateTime.of(1976, 2, 11, 11, 40);

    @Test
    void testEnergy() {
        LocalDateTime now = LocalDateTime.now();
        for (int i = 0; i < 1; i++) {
            now = now.plus(1, ChronoUnit.DAYS);
            能量 energy = 能量.of(命盘.of(ww, 1)
//                    .atDay(now.getYear(), now.getMonthValue(), now.getDayOfMonth())
                    .getGanzhiList());
            log.info("{} - {}", now.toLocalDate(), energy.toString().replace("|", "\t|"));
        }
    }

    void outputEnergy(能量 energy) {
        for (五行 wuXing : 五行.values()) {
            log.info("{} 能量是：{}", wuXing.getName(), energy.get(wuXing));
        }
    }

    @BeforeEach
    void setUp() {
    }

    @AfterEach
    void tearDown() {
    }
}