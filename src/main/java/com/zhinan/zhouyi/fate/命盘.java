package com.zhinan.zhouyi.fate;

import com.zhinan.zhouyi.base.*;
import com.zhinan.zhouyi.fate.luck.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class 命盘 {
    LocalDateTime birthday;
    阴阳         sex;
    天干         ming;
    命主         zhu;
    命局         pattern;

    List<天干>   ganList        = new ArrayList<>();
    List<地支>   zhiList        = new ArrayList<>();
    List<十神>   ganGodList     = new ArrayList<>();
    List<天干[]> hideGanList    = new ArrayList<>();
    List<十神[]> zhiGodList     = new ArrayList<>();
    List<地支[]> emptyList      = new ArrayList<>();
    List<纳音>   soundList      = new ArrayList<>();
    List<长生>   starStatusList = new ArrayList<>();
    List<长生>   selfStatusList = new ArrayList<>();

    String      luckAge;
    LocalDate   luckDate;

    List<大运>   decadeLuckList;
    List<年运>   yearLuckList;
    List<月运>   monthLuckList;
    List<日运>   dayLuckList;
    List<时运>   hourLuckList;

    private 命盘() {}

    public static 命盘 of(LocalDateTime birthday, int sex) {
        命盘 pan  = new 命盘();
        八字 bazi = 八字.of(birthday, sex);

        pan.birthday = birthday;
        pan.sex      = 阴阳.getByValue(sex);
        pan.ming     = bazi.getMing();
        pan.zhu      = 命主.of(pan.ming);
        pan.pattern  = bazi.getPattern();

        bazi.getFourColumn().forEach(pan::addGanZhi);

        pan.luckAge  = 大运.calculateLuckAge (birthday, sex);
        pan.luckDate = 大运.calculateLuckDate(birthday, sex);

        pan.decadeLuckList = 大运.list(birthday, sex);

        return pan;
    }

    public void addGanZhi(干支 ganzhi) {
        ganList.add(ganzhi.getGan());
        zhiList.add(ganzhi.getZhi());
        ganGodList.add(ming.compare(ganzhi.getGan()));
        hideGanList.add(ganzhi.getZhi().getHiddenGan());
        List<十神> zhiShenList = new ArrayList<>();
        Arrays.asList(ganzhi.getZhi().getHiddenGan()).forEach(gan -> zhiShenList.add(ming.compare(gan)));
        zhiGodList.add(zhiShenList.toArray(new 十神[0]));
        emptyList.add(ganzhi.getEmpty());
        soundList.add(ganzhi.getSound());
        selfStatusList.add(ganzhi.getStatus());
        starStatusList.add(new 干支(ming, ganzhi.getZhi()).getStatus());
    }

    public 命盘 atDecade(int year) {
        运势 yun = 运势.select(decadeLuckList, 运势.getStartTimeOfYear(year));
        if (yun != null) {
            addGanZhi(yun);
            yearLuckList = 年运.list(yun.getStartTime().getYear(), birthday, sex.getValue());
        }
        return this;
    }

    public 命盘 atYear(int year) {
        命盘 pan = atDecade(year);
        运势 yun = 运势.select(yearLuckList, 运势.getStartTimeOfYear(year));
        if (yun != null) {
            addGanZhi(yun);
            monthLuckList = 月运.list(year, birthday, sex.getValue());
        }
        return pan;
    }

    public 命盘 atMonth(int year, int month) {
        命盘 pan = atYear(year);
        运势 yun = 运势.select(monthLuckList,
                月运.of(LocalDateTime.of(year, month, 15, 0 ,0), birthday, sex.getValue()).getStartTime());
        if (yun != null) {
            addGanZhi(yun);
            dayLuckList = 日运.list(year, month, birthday, sex.getValue());
        }
        return pan;
    }

    public 命盘 atDay(int year, int month, int day) {
        命盘 pan = atMonth(year, month);
        运势 yun = 运势.select(dayLuckList, LocalDateTime.of(year, month, day, 0, 0));
        if (yun != null) {
            addGanZhi(yun);
            hourLuckList = 时运.list(year, month, day, birthday, sex.getValue());
        }
        return pan;
    }

    public 命盘 atHour(int year, int month, int day, int hour) {
        命盘 pan = atDay(year, month, day);
        运势 yun = 运势.select(hourLuckList, LocalDateTime.of(year, month, day, hour, 0));
        if (yun != null) {
            addGanZhi(yun);
        }
        return pan;
    }

    public 简盘 simplify() {
        return new 简盘(this);
    }
}
