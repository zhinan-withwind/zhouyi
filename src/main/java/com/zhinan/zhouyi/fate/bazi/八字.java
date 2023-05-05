package com.zhinan.zhouyi.fate.bazi;

import com.zhinan.zhouyi.base.*;
import com.zhinan.zhouyi.date.GanZhiDateTime;
import com.zhinan.zhouyi.energy.能量;
import lombok.Getter;
import run.zhinan.time.ganzhi.GanZhiDate;

import java.time.LocalDate;
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

    FatePattern fatePattern;

    int  selfPart;
    int otherPart;

    public static List<地支> coolMonthList = Arrays.asList(地支.亥, 地支.子, 地支.丑, 地支.寅);
    public static List<地支>  hotMonthList = Arrays.asList(地支.辰, 地支.巳, 地支.午, 地支.未);

    private 八字() {}

    public static 八字 of(LocalDateTime birthday, int sex) {
        return of(GanZhiDateTime.of(birthday), sex);
    }

    public static 八字 of(GanZhiDateTime birthday, int sex) {
        八字 bazi = new 八字();
        bazi.birthday = birthday.toLocalDateTime();
        bazi.sex = 阴阳.getByValue(sex);

        bazi.fourColumn = birthday.toGanZhiList();
        bazi.year    = bazi.fourColumn.get(0);
        bazi.month   = bazi.fourColumn.get(1);
        bazi.day     = bazi.fourColumn.get(2);
        bazi.time    = bazi.fourColumn.get(3);

        bazi.fatePattern = FatePattern.of(bazi);
        bazi.selfPart    = bazi.fatePattern.getSelfPart();
        bazi.otherPart   = bazi.fatePattern.getOtherPart();
        bazi.energy      = 能量.of(bazi.fourColumn);

        return bazi;
    }

    public static 八字 of(LocalDate birthdate, int sex) {
        八字 bazi = new 八字();
        bazi.birthday = birthdate.atTime(0, 0);
        bazi.sex = 阴阳.getByValue(sex);

        GanZhiDate ganZhiDate = GanZhiDate.of(birthdate);

        bazi.year  = 干支.of(ganZhiDate.getGanZhiYear ());
        bazi.month = 干支.of(ganZhiDate.getGanZhiMonth());
        bazi.day   = 干支.of(ganZhiDate.getGanZhiDay  ());
        bazi.time  = null;
        bazi.fourColumn = Arrays.asList(bazi.year, bazi.getMonth(), bazi.getDay(), bazi.getTime());

        bazi.fatePattern = FatePattern.of(bazi);
        bazi.selfPart    = bazi.fatePattern.getSelfPart();
        bazi.otherPart   = bazi.fatePattern.getOtherPart();
        bazi.energy      = 能量.of(bazi.fourColumn);

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

    public 命主 getFate() {
        return 命主.of(getMing());
    }

    /**
     * 计算大运的排序方向：阳年阳造，阴年阴造为顺排，阳年阴造，阴年阳造为逆排
     * @return 顺排返回 1， 逆排返回 01
     */
    public int getDirection() {
        return getYear().getGan().getYinYang().equals(sex) ? 1 : -1;
    }

    @Override
    public String toString() {
        return year + " " + month + " " + day + " " + time;
    }
}
