package com.zhinan.zhouyi.date;

import com.zhinan.zhouyi.base.地支;

public class DateTimeFormatter {

    DateTimeHolder dateTime;

    public DateTimeFormatter(DateTimeHolder dateTime) {
        this.dateTime = dateTime;
    }

    public static DateTimeFormatter getInstance(DateTimeHolder dateTime) {
        return new DateTimeFormatter(dateTime);
    }

    public String toArabicNumberString(DateType dateType) {
        String result = "";
        switch (dateType) {
            case DAY:
                result = (dateTime.getDay() < 10 ? "0" : "") + dateTime.getDay();
                break;
            case SHORT_DATE:
                result = (dateTime.getMonth() < 10 ? "0" : "") + dateTime.getMonth() + (dateTime.isLeap() ? "(Leap)" : "") + "-" + toArabicNumberString(DateType.DAY);
                break;
            case FULL_DATE:
                result = dateTime.getYear() + "-" + toArabicNumberString(DateType.SHORT_DATE);
                break;
            case DATETIME:
                result = toArabicNumberString(DateType.FULL_DATE)
                        + " " + (dateTime.getHour()   < 10 ? "0" : "") + dateTime.getHour()
                        + ":" + (dateTime.getMinute() < 10 ? "0" : "") + dateTime.getMinute();
        }
        return result;
    }

    public String toChineseNumberString(DateType dateType) {
        String result = "";
        switch (dateType) {
            case DAY:
                result = DateTimeConstants.DATE_NAME.values()[dateTime.getDay() - 1].name();
                break;
            case SHORT_DATE:
                result = (dateTime.isLeap() ? "闰" : "") + DateTimeConstants.MONTH_NAME.values()[dateTime.getMonth() - 1].name() + "月"
                        + toChineseNumberString(DateType.DAY);
                break;
            case FULL_DATE:
                result = toChineseNumber(dateTime.getYear()) + "年" + toChineseNumberString(DateType.SHORT_DATE);
                break;
            case DATETIME:
                result = toChineseNumberString(DateType.FULL_DATE) + " "
                        + 地支.getByValue(Math.floorDiv(dateTime.getHour() + 1,  2) % 12).getName() + "时";
        }
        return result;
    }

    public String toMixTypeString(DateType dateType) {
        String result = "";
        switch (dateType) {
            case DAY:
                result = dateTime.getDay() + "日";
                break;
            case SHORT_DATE:
                result = dateTime.getMonth() + "月" + toMixTypeString(DateType.DAY);
                break;
            case FULL_DATE:
                result = dateTime.getYear() + "年" + toMixTypeString(DateType.SHORT_DATE);
                break;
            case DATETIME:
                result = toMixTypeString(DateType.FULL_DATE)
                        + dateTime.getHour() + "时" + dateTime.getMinute() + "分";
        }
        return result;
    }

    public String toGanZhiString(DateType dateType) {
        GanZhiDateTime dateTime = this.dateTime.toGanZhi();
        String result = "";
        switch (dateType) {
            case DAY:
                result = dateTime.getGanZhiDay() + "日";
                break;
            case SHORT_DATE:
                result = dateTime.getGanZhiMonth() + "月" + toGanZhiString(DateType.DAY);
                break;
            case FULL_DATE:
                result = dateTime.getGanZhiYear() +"年" + toGanZhiString(DateType.SHORT_DATE);
                break;
            case DATETIME:
                result = toGanZhiString(DateType.FULL_DATE) + dateTime.getGanZhiHour() + "时";
        }
        return result;
    }

    public String format(DateFormatType formatType) {
        return format(formatType, DateType.DATETIME);
    }

    public String format(DateFormatType formatType, DateType dateType) {
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
        return number == 0 ? "" : toChineseNumber(number / 10) + DateTimeConstants.NUMBERS.values()[number % 10].name();
    }
}