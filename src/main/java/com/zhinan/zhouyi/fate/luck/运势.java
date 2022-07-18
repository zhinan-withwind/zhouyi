package com.zhinan.zhouyi.fate.luck;

import com.zhinan.zhouyi.base.十神;
import com.zhinan.zhouyi.base.干支;
import com.zhinan.zhouyi.date.SolarTerm;
import com.zhinan.zhouyi.fate.bazi.八字;
import lombok.Getter;

import java.time.LocalDateTime;
import java.util.List;

@Getter
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
        return SolarTerm.立春.of(year);
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

    public 十神 getGanGod() {
        return getBazi().getMing().compare(getGan());
    }

    public 十神 getZhiGod() {
        return getBazi().getMing().compare(getZhi().getTianGan());
    }

    public String getDate() {
        return String.valueOf(startTime.getYear());
    }

    public String getAge() {
        return String.valueOf(startTime.getYear() - bazi.getBirthday().getYear() + 1);
    }

    public boolean isGanGodGood() {
        return bazi.getFatePattern().isGood(getGan().getWuXing());
    }

    public boolean isZhiGodGood() {
        return bazi.getFatePattern().isGood(getZhi().getWuXing());
    }

    public String getDescription() {
        return "";
    }
}
