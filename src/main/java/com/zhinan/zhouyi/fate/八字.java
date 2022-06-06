package com.zhinan.zhouyi.fate;

import com.zhinan.zhouyi.base.*;
import com.zhinan.zhouyi.energy.能量;
import com.zhinan.zhouyi.util.DateUtil;
import lombok.Getter;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Getter
public class 八字 {
    enum 种类 {寒局, 燥局, 不寒不燥}

    干支 year;
    干支 month;
    干支 day;
    干支 time;

    List<干支> fourColumn = new ArrayList<>();

    LocalDateTime birthday;

    阴阳 sex;
    能量 energy;
    命局 pattern;

    double  selfPart;
    double otherPart;

    public static List<地支> coolMonthList = Arrays.asList(地支.亥, 地支.子, 地支.丑, 地支.寅);
    public static List<地支>  hotMonthList = Arrays.asList(地支.辰, 地支.巳, 地支.午, 地支.未);

    private 八字() {}

    public static 八字 of(LocalDateTime birthday, int sex) {
        八字 bazi = new 八字();
        bazi.birthday = birthday;
        bazi.sex = 阴阳.getByValue(sex);

        bazi.fourColumn = DateUtil.toGanZhi(birthday);
        bazi.year    = bazi.fourColumn.get(0);
        bazi.month   = bazi.fourColumn.get(1);
        bazi.day     = bazi.fourColumn.get(2);
        bazi.time    = bazi.fourColumn.get(3);

        bazi.energy  = 能量.of(bazi.fourColumn);
        bazi.pattern = 命局.of(bazi);
        bazi.selfPart  = (bazi.energy.get(bazi.getMing().getWuXing().get生())
                + bazi.energy.get(bazi.getMing().getWuXing().get同())) / bazi.energy.getTotal().doubleValue();
        bazi.otherPart = (bazi.energy.get(bazi.getMing().getWuXing().get泄())
                + bazi.energy.get(bazi.getMing().getWuXing().get耗())
                + bazi.energy.get(bazi.getMing().getWuXing().get克())) / bazi.energy.getTotal().doubleValue();
        return bazi;
    }

    public 种类 getType() {
        种类 type = 种类.不寒不燥;
        if (hotMonthList.contains(getLing())) {
            type = 种类.燥局;
        } else if (coolMonthList.contains(getLing())) {
            type = 种类.寒局;
        } else if (getLing().equals(地支.戌) &&
                ( getYear().getZhi().getWuXing().equals(五行.水)
              || getMonth().getGan().getWuXing().equals(五行.水)
              ||   getDay().getZhi().getWuXing().equals(五行.水))) {
            type = 种类.寒局;
        }
        return type;
    }

    public 天干 getMing() {
        return getDay().getGan();
    }

    public 地支 getLing() {
        return getMonth().getZhi();
    }

}
