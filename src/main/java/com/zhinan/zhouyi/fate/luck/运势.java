package com.zhinan.zhouyi.fate.luck;

import com.zhinan.zhouyi.base.十神;
import com.zhinan.zhouyi.base.干支;
import com.zhinan.zhouyi.date.SolarTerm;
import com.zhinan.zhouyi.fate.bazi.八字;
import com.zhinan.zhouyi.util.DateUtil;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;

import java.time.LocalDateTime;
import java.util.List;

@Slf4j
@Getter
public abstract class 运势 extends 干支 {
    @Getter
    @AllArgsConstructor
    public enum 类型 {
        大运("十年"), 年运("一年"), 月运("一月"), 日运("一天"), 时运("时辰");

        String unit;

        public static 类型 getByValue(int value) {
            return values()[value];
        }
    }

    @Getter
    @AllArgsConstructor
    public enum 级别 {
        优("极旺"), 良("较好"), 中("中平"), 差("较低");
        String name;

        @Override
        public String toString() {
            return name;
        }

        public static 级别 getByScore(int score) {
            级别 result = 级别.差;
            if (score >= 80) {
                result = 级别.优;
            } else if (score >= 60) {
                result = 级别.良;
            } else if (score > 30) {
                result = 级别.中;
            }
            return result;
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
//        return String.valueOf(startTime.getYear() - bazi.getBirthday().getYear() + 1);
        return String.valueOf(DateUtil.getFullYearAge(bazi.getBirthday(), startTime));
    }

    public boolean isGanGodGood() {
        return bazi.getFatePattern().isGood(getGan().getWuXing());
    }

    public boolean isZhiGodGood() {
        return bazi.getFatePattern().isGood(getZhi().getWuXing());
    }

    abstract 运势 getParent();

    public int getScore() {
        int score = new Double(40 * (isGanGodGood() ? 0.8 : 0.3) + 60 * (isZhiGodGood() ? 0.8 : 0.3)).intValue();
        运势 parent = getParent();
        int parentScore = parent == null ? score : parent.getScore();
        return new Double(parentScore * 0.4 + score * 0.6).intValue();
    }
}
