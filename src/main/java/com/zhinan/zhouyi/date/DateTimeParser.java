package com.zhinan.zhouyi.date;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

public class DateTimeParser {
    /**
     * 将字符串转化为LocalDateTime
     * @param dateTimeString    需要转化的日期字符串
     * @param dateType          日期字符串的类型：0 - 阴历, 1 - 阳历, 2 - 干支历
     * @return 对应时间的 LocalDateTime
     */
    public static LocalDateTime parse(String dateTimeString, Integer dateType) {
        LocalDate date;
        LocalTime time;

        try {
            int blank = dateTimeString.indexOf(' ');
            if (blank > 0) {
                date = parseDate(dateTimeString.substring(0, blank), dateType);
                time = parseTime(dateTimeString.substring(blank + 1));
            } else {
                date = parseDate(dateTimeString, dateType);
                time = LocalTime.of(0, 0, 0);
            }
        } catch (Exception e) {
            throw new DateFormatException("日期字符串：" + dateTimeString + "格式有问题，或该日期不存在！");
        }
        return date != null && time != null ? LocalDateTime.of(date, time) : null;
    }

    public static LocalDate parseDate(String dateString, Integer dateType) {
        LocalDate result = null;
        int year, month, day;
        String[] values = dateString.split("-");
        if (values.length == 3) {
            year = Integer.parseInt(values[0]);
            if (dateType == 0) {
                boolean isLeap = values[1].contains("闰");
                String monthName = values[1].substring(isLeap ? 1 : 0, isLeap ? 2 : 1);
                month = DateTimeConstants.MONTH_NAME.valueOf(monthName).ordinal() + 1;
                day   = DateTimeConstants.DATE_NAME .valueOf(values[2]).ordinal() + 1;
                LunarDateTime lunarDateTime = new LunarDateTime(year, month, day, 0, 0, isLeap);
                result = lunarDateTime.toLocalDateTime().toLocalDate();
            } else {
                result = LocalDate.of(year, Integer.parseInt(values[1]), Integer.parseInt(values[2]));
            }
        }
        return result;
    }

    public static LocalTime parseTime(String timeString) {
        return LocalTime.parse(timeString);
    }

}
