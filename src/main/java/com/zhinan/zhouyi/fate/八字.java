package com.zhinan.zhouyi.fate;

import com.zhinan.zhouyi.base.干支;
import com.zhinan.zhouyi.base.阴阳;
import com.zhinan.zhouyi.util.DateUtil;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class 八字 {
    干支 year;
    干支 month;
    干支 day;
    干支 time;

    List<干支> 四柱 = new ArrayList<>();

    LocalDateTime birthday;

    阴阳 sex;

    private 八字() {}

    public static 八字 of(LocalDateTime birthday, int sex) {
        八字 bazi = new 八字();
        bazi.birthday = birthday;
        bazi.sex = 阴阳.getByValue(sex);

        bazi.四柱 = DateUtil.toGanZhi(birthday);
        bazi.year  = bazi.四柱.get(0);
        bazi.month = bazi.四柱.get(1);
        bazi.day   = bazi.四柱.get(2);
        bazi.time  = bazi.四柱.get(3);

        return bazi;
    }

    public 干支 getYear() {
        return year;
    }

    public 干支 getMonth() {
        return month;
    }

    public 干支 getDay() {
        return day;
    }

    public 干支 getTime() {
        return time;
    }

    public List<干支> get四柱() {
        return 四柱;
    }

    public LocalDateTime getBirthday() {
        return birthday;
    }

    public 阴阳 getSex() {
        return sex;
    }
}
