package com.zhinan.zhouyi.date;

import com.zhinan.zhouyi.base.地支;
import com.zhinan.zhouyi.base.干支;

import java.util.List;

public class DateTimeFormatter {
    public enum DATE_FORMAT_TYPE {
        ARABIC_NUMBER, CHINESE_NUMBER, GANZHI_NUMBER, MIX_TYPE
    }

    DateTimeHolder dateTime;

    public DateTimeFormatter(DateTimeHolder dateTime) {
        this.dateTime = dateTime;
    }

    public static DateTimeFormatter getInstance(DateTimeHolder dateTime) {
        return new DateTimeFormatter(dateTime);
    }

    public String toArabicNumberString() {
        return dateTime.getYear()+"-" + dateTime.getMonth() + (dateTime.isLeap() ? "(Leap)" : "")
                + "-" + dateTime.getDay() + " " + dateTime.getHour() + ":" + dateTime.getMinute();
    }

    public String toChineseNumberString() {
        return toChineseNumber(dateTime.getYear()) + "年" + (dateTime.isLeap() ? "闰" : "")
                + MONTH_NAME[dateTime.getMonth() - 1] + "月"
                + DATE_NAME[dateTime.getDay() - 1] + " "
                + 地支.getByValue(Math.floorDiv(dateTime.getHour() + 1,  2) % 12).getName() + "时";
    }

    public String toMixTypeString() {
        return dateTime.getYear() + "年" + dateTime.getMonth() + "月" + dateTime.getDay() + "日 "
                + dateTime.getHour() + "时" + dateTime.getMinute() + "分";
    }

    public String toGanZhiString() {
        List<干支> ganzhi = dateTime.toGanZhi();
        return ganzhi.get(0) +"年" + ganzhi.get(1) + "月" + ganzhi.get(2) + "日" + ganzhi.get(3) + "时";
    }

    public String format(DATE_FORMAT_TYPE type) {
        String result;
        switch (type) {
            case CHINESE_NUMBER:
                result = toChineseNumberString();
                break;
            case MIX_TYPE:
                result = toMixTypeString();
                break;
            case GANZHI_NUMBER:
                result = toGanZhiString();
                break;
            default:
                result = toArabicNumberString();
        }
        return result;
    }

    private static String toChineseNumber(int number) {
        return number == 0 ? "" : toChineseNumber(number / 10) + 数字[number % 10];
    }


    private final static String[] 数字 = new String[] {
            "零", "一", "二", "三", "四", "五", "六", "七", "八", "九",
    };
    private final static String[] MONTH_NAME = new String[] {
            "正", "二", "三", "四", "五", "六", "七", "八", "九", "十", "冬", "腊"
    };
    private final static String[] DATE_NAME  = new String[] {
            "初一", "初二", "初三", "初四", "初五", "初六", "初七", "初八", "初九", "初十",
            "十一", "十二", "十三", "十四", "十五", "十六", "十七", "十八", "十九", "二十",
            "廿一", "廿二", "廿三", "廿四", "廿五", "廿六", "廿七", "廿八", "廿九", "三十", "卅一"
    };
}