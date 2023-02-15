package com.zhinan.zhouyi.fate.bazi;

import com.zhinan.zhouyi.base.*;
import com.zhinan.zhouyi.effect.可作用;
import com.zhinan.zhouyi.effect.合化冲;
import com.zhinan.zhouyi.energy.能量;
import com.zhinan.zhouyi.fate.luck.*;
import com.zhinan.zhouyi.util.DateUtil;
import lombok.Getter;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.*;

@Getter
public class 命盘 {
    LocalDateTime birthday;
    阴阳         sex;
    天干         ming;
    命主         zhu;
    FatePattern pattern;

    List<干支>   ganZhiList     = new ArrayList<>();
    List<天干>   ganList        = new ArrayList<>();
    List<地支>   zhiList        = new ArrayList<>();
    List<十神>   ganGodList     = new ArrayList<>();
    List<天干[]> hideGanList    = new ArrayList<>();
    List<十神[]> zhiGodList     = new ArrayList<>();
    List<地支[]> emptyList      = new ArrayList<>();
    List<纳音>   soundList      = new ArrayList<>();
    List<长生>   starStatusList = new ArrayList<>();
    List<长生>   selfStatusList = new ArrayList<>();

    Map<String, List<可作用>> effects = new HashMap<>();

    String      luckAge;
    LocalDate   luckDate;

    List<大运>   decadeLuckList = new ArrayList<>();
    List<年运>   yearLuckList   = new ArrayList<>();
    List<月运>   monthLuckList  = new ArrayList<>();
    List<日运>   dayLuckList    = new ArrayList<>();
    List<时运>   hourLuckList   = new ArrayList<>();

    能量 origin;
    能量 energy;

    private 命盘() {}

    public static 命盘 of(LocalDateTime birthday, int sex) {
        命盘 pan  = new 命盘();
        八字 bazi = 八字.of(birthday, sex);

        pan.birthday  = birthday;
        pan.sex       = 阴阳.getByValue(sex);
        pan.ming      = bazi.getMing();
        pan.zhu       = 命主.of(pan.ming);
        pan.pattern   = bazi.getFatePattern();

        bazi.getFourColumn().forEach(pan::addGanZhi);

        pan.effects   = 合化冲.getEffects(bazi.getFourColumn());

        pan.luckAge   = 大运.calculateLuckAge (birthday, sex);
        pan.luckDate  = 大运.calculateLuckDate(birthday, sex);

        pan.decadeLuckList = 大运.list(birthday, sex);

        pan.origin = 能量.of(bazi.getFourColumn());
        pan.energy = 能量.of(pan .getGanZhiList());

        return pan;
    }

    public void addGanZhi(干支 ganzhi) {
        ganZhiList.add(ganzhi);
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

        if (ganZhiList.size() >= 4) {
            effects = 合化冲.getEffects(ganZhiList);
            energy  = 能量.of(ganZhiList);
        }
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
            pan.addGanZhi(yun);
            pan.monthLuckList = 月运.list(year, birthday, sex.getValue());
        }
        return pan;
    }

    public 命盘 atMonth(int year, int month) {
        命盘 pan = atYear(month == 1 ? year - 1 : year);
        运势 yun = 运势.select(monthLuckList,
                月运.of(LocalDateTime.of(year, month, 15, 0 ,0), birthday, sex.getValue()).getStartTime());
        if (yun != null) {
            pan.addGanZhi(yun);
            pan.dayLuckList = 日运.list(year, month, birthday, sex.getValue());
        }
        return pan;

    }

    public 命盘 atDay(int year, int month, int day) {
        LocalDateTime dateTime = LocalDateTime.of(year, month, day, 23, 59);
        dateTime = dateTime.isBefore(DateUtil.getMajorSolarTerm(dateTime).of(year).getDateTime()) ||
                dateTime.isBefore(运势.getStartTimeOfYear(year)) ?
                dateTime.minusMonths(1) : dateTime;
        命盘 pan = atMonth(dateTime.getYear(), dateTime.getMonthValue());
        运势 yun = 运势.select(dayLuckList, LocalDateTime.of(year, month, day, 1, 0));
        if (yun != null) {
            pan.addGanZhi(yun);
            pan.hourLuckList = 时运.list(year, month, day, birthday, sex.getValue());
        }
        return pan;
    }

    public 命盘 atHour(int year, int month, int day, int hour) {
        命盘 pan = atDay(year, month, day);
        运势 yun = 运势.select(hourLuckList, LocalDateTime.of(year, month, day, hour, 0));
        if (yun != null) {
            pan.addGanZhi(yun);
        }
        return pan;
    }

    public 简盘 simplify() {
        return new 简盘(this);
    }
}
