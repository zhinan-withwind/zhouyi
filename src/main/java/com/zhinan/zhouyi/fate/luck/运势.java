package com.zhinan.zhouyi.fate.luck;

import com.zhinan.zhouyi.base.十神;
import com.zhinan.zhouyi.base.天干;
import com.zhinan.zhouyi.base.干支;
import com.zhinan.zhouyi.date.DateTimeFormatter;
import com.zhinan.zhouyi.date.SolarDateTime;
import com.zhinan.zhouyi.fate.八字;
import com.zhinan.zhouyi.util.DateUtil;
import net.time4j.calendar.SolarTerm;

import java.time.LocalDateTime;
import java.util.List;

public class 运势 extends 干支 {
    public enum 类型 {
        大运, 年运, 月运, 日运, 时运;

        public static 类型 getByValue(int value) {
            return values()[value];
        }
    }

    八字 bazi;
    LocalDateTime startTime;
    LocalDateTime endTime;
    类型 type;
    boolean selected = false;

    运势(干支 ganzhi, 八字 bazi, LocalDateTime startTime, LocalDateTime endTime, 类型 type) {
        super(ganzhi.getGan(), ganzhi.getZhi());
        this.bazi      = bazi;
        this.startTime = startTime;
        this.endTime   = endTime;
        this.type      = type;
    }

    public static LocalDateTime getStartTimeOfYear(int year) {
        return DateUtil.toLocalDate(year, SolarTerm.MINOR_01_LICHUN_315).atTime(0, 0);
    }

    public boolean contains(LocalDateTime dateTime) {
        return !startTime.isAfter(dateTime) && dateTime.isBefore(endTime);
    }

    public static 运势 select(List<? extends 运势> luckList, LocalDateTime dateTime) {
        运势 result = null;
        for (运势 luck : luckList) {
            if (luck.contains(dateTime)) {
                luck.selected = true;
                result = luck;
                break;
            }
        }
        return result;
    }

    public String getDate() {
        return DateTimeFormatter.getInstance(SolarDateTime.of(startTime)).format(DateTimeFormatter.DATE_FORMAT_TYPE.ARABIC_NUMBER);
    }

    public String getAge() {
        return String.valueOf(startTime.getYear() - bazi.getBirthday().getYear());
    }

    public 十神 getGanGod() {
        return getBazi().getMing().compare(getGan());
    }

    public 十神 getZhiGod() {
        return getBazi().getMing().compare(getZhi().getTianGan());
    }

    public 八字 getBazi() {
        return bazi;
    }

    public LocalDateTime getStartTime() {
        return startTime;
    }

    public LocalDateTime getEndTime() {
        return endTime;
    }

    public 类型 getType() {
        return type;
    }

    public boolean isSelected() {
        return selected;
    }

    public boolean isGanGodGood() {
        return bazi.getPattern().isGood(getGan().getWuXing());
    }

    public boolean isZhiGodGood() {
        return bazi.getPattern().isGood(getZhi().getWuXing());
    }
}
