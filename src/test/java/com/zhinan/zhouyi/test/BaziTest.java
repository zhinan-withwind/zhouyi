package com.zhinan.zhouyi.test;

import com.alibaba.fastjson.JSON;
import com.zhinan.zhouyi.common.ZhouYiAPI;
import com.zhinan.zhouyi.energy.能量;
import com.zhinan.zhouyi.fate.bazi.FatePattern;
import com.zhinan.zhouyi.fate.bazi.八字;
import com.zhinan.zhouyi.fate.luck.大运;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.time.LocalDateTime;

public class BaziTest {

    @Test
    public void testThreeColumn() {
        ZhouYiAPI.init(ZhouYiAPI.MODEL_TYPE.SCORE, System.lineSeparator());
        八字 bazi = 八字.of(LocalDate.of(1976, 2, 11), 1);
        能量 energy = 能量.of(bazi.getFourColumn());
        FatePattern pattern = bazi.getFatePattern();
        大运 luck  = 大运.of(LocalDateTime.now(), bazi.getBirthday(), bazi.getSex().getValue());
//        System.out.println(JSON.toJSONString(JSON.toJSON(energy) , true));
//        System.out.println(JSON.toJSONString(JSON.toJSON(pattern), true));
        System.out.println(luck.getName() + " - " + luck.getStartTime() + " - " + luck.getEndTime());
    }
}
