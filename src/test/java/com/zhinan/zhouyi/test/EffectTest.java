package com.zhinan.zhouyi.test;

import com.zhinan.zhouyi.effect.可作用;
import com.zhinan.zhouyi.effect.可合化;
import com.zhinan.zhouyi.effect.合化冲;
import com.zhinan.zhouyi.fate.bazi.八字;
import com.zhinan.zhouyi.fate.bazi.命盘;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@Slf4j
class EffectTest {

    @Test
    void testEffect() {
        List<可作用> effects = 合化冲.getValues();
        for (可作用 effect : effects) {
            int num = effect.getElements().size();
            String name = effect.getName();
            for (int i = 0; i < num; i++) {
                assertEquals(name.substring(i, i + 1), effect.getElements().get(i).getName());
            }
            if (effect instanceof 可合化) {
                assertEquals(name.substring(name.length() - 1), ((可合化) effect).getResult().getName());
            }
        }
    }

    @Test
    void testApply() {
        LocalDateTime birthday = LocalDateTime.of(1976, 2, 11, 11, 40);
        LocalDateTime now      = LocalDateTime.now();
        八字 bazi = 八字.of(birthday, 1);
        log.info(合化冲.getEffects(bazi.getFourColumn()).toString());
        log.info(合化冲.getEffects(
                命盘.of(birthday, 1).atHour(now.getYear(), now.getMonthValue(), now.getDayOfMonth(), now.getHour())
                        .getGanzhiList()).toString());
    }
}