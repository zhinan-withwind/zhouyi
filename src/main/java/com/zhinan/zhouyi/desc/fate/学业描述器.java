package com.zhinan.zhouyi.desc.fate;

import com.zhinan.zhouyi.energy.能量;
import com.zhinan.zhouyi.fate.bazi.八字;
import com.zhinan.zhouyi.fate.bazi.格局;

import java.time.LocalDateTime;

public class 学业描述器 {
    public enum 描述类型 {
        八字维度, 大运维度, 流年维度, 文理选择
    }

    public final static String[] 八字维度 = {
            "身弱而伤食配印，日主身弱，印生助日主，为喜神，易有学业成就。",
            "身弱而印化官杀，日主因官杀而弱，印化官杀之力，易有文学成就。",
            "身强而印配伤食，日主身强喜泄，伤食耗泄日主，会起到好的作用。",
            "身强而伤食制杀，日主身强，伤食制杀，能缓和官杀对日主的作用。",
            "食伤和印绶较弱，不容易在学业上取得特别成就，更需要后天努力。",
    };

    public static String describe(LocalDateTime birthday, int sex) {
        八字 bazi   = 八字.of(birthday, sex);
        int 印绶分数 = 能量.of(bazi.getFourColumn()).get(bazi.getFate().get同());
        int 食伤分数 = 能量.of(bazi.getFourColumn()).get(bazi.getFate().get泄());
        格局 pattern = 格局.of(bazi);
        int result  = 4;
        return 八字维度[result];
    }
}
