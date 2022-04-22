package com.zhinan.zhouyi.date;

import com.zhinan.zhouyi.base.地支;
import com.zhinan.zhouyi.base.干支;

import java.util.List;

public class DateTimeFormatter {
    public enum DATE_FORMAT_TYPE {
        ARABIC_NUMBER, CHINESE_NUMBER, GANZHI_NUMBER, MIX_TYPE
    }

    public enum DATE_TYPE {
        DATE, DAY, DATETIME
    }

    DateTimeHolder dateTime;

    public DateTimeFormatter(DateTimeHolder dateTime) {
        this.dateTime = dateTime;
    }

    public static DateTimeFormatter getInstance(DateTimeHolder dateTime) {
        return new DateTimeFormatter(dateTime);
    }

    public String toArabicNumberString(DATE_TYPE dateType) {
        String result = "";
        switch (dateType) {
            case DAY:
                result = String.valueOf(dateTime.getDay());
                break;
            case DATE:
                result = dateTime.getYear()+"-" + dateTime.getMonth() + (dateTime.isLeap() ? "(Leap)" : "")
                        + "-" + dateTime.getDay();
                break;
            case DATETIME:
                result = dateTime.getYear()+"-" + dateTime.getMonth() + (dateTime.isLeap() ? "(Leap)" : "")
                        + "-" + dateTime.getDay() + " " + dateTime.getHour() + ":" + dateTime.getMinute();
        }
        return result;
    }

    public String toChineseNumberString(DATE_TYPE dateType) {
        String result = "";
        switch (dateType) {
            case DAY:
                result = DATE_NAME[dateTime.getDay() - 1];
                break;
            case DATE:
                result = toChineseNumber(dateTime.getYear()) + "年" + (dateTime.isLeap() ? "闰" : "")
                        + MONTH_NAME[dateTime.getMonth() - 1] + "月"
                        + DATE_NAME[dateTime.getDay() - 1];
                break;
            case DATETIME:
                result = toChineseNumber(dateTime.getYear()) + "年" + (dateTime.isLeap() ? "闰" : "")
                        + MONTH_NAME[dateTime.getMonth() - 1] + "月"
                        + DATE_NAME[dateTime.getDay() - 1] + " "
                        + 地支.getByValue(Math.floorDiv(dateTime.getHour() + 1,  2) % 12).getName() + "时";
        }
        return result;
    }

    public String toMixTypeString(DATE_TYPE dateType) {
        String result = "";
        switch (dateType) {
            case DAY:
                result = dateTime.getDay() + "日";
                break;
            case DATE:
                result = dateTime.getYear() + "年" + dateTime.getMonth() + "月" + dateTime.getDay() + "日";
                break;
            case DATETIME:
                result = dateTime.getYear() + "年" + dateTime.getMonth() + "月" + dateTime.getDay() + "日 "
                        + dateTime.getHour() + "时" + dateTime.getMinute() + "分";
        }
        return result;
    }

    public String toGanZhiString(DATE_TYPE dateType) {
        List<干支> ganzhi = dateTime.toGanZhi();
        String result = "";
        switch (dateType) {
            case DAY:
                result = ganzhi.get(2) + "日";
                break;
            case DATE:
                result = ganzhi.get(0) +"年" + ganzhi.get(1) + "月" + ganzhi.get(2) + "日";
                break;
            case DATETIME:
                result = ganzhi.get(0) +"年" + ganzhi.get(1) + "月" + ganzhi.get(2) + "日" + ganzhi.get(3) + "时";
        }
        return result;
    }

    public String format(DATE_FORMAT_TYPE formatType) {
        return format(formatType, DATE_TYPE.DATETIME);
    }

    public String format(DATE_FORMAT_TYPE formatType, DATE_TYPE dateType) {
        String result;
        switch (formatType) {
            case CHINESE_NUMBER:
                result = toChineseNumberString(dateType);
                break;
            case MIX_TYPE:
                result = toMixTypeString(dateType);
                break;
            case GANZHI_NUMBER:
                result = toGanZhiString(dateType);
                break;
            default:
                result = toArabicNumberString(dateType);
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