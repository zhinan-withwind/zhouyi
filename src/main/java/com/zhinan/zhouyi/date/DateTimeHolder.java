package com.zhinan.zhouyi.date;

import java.time.LocalDateTime;

public interface DateTimeHolder {
    int getYear();
    int getMonth();
    int getDay();
    int getHour();
    int getMinute();
    boolean isLeap();
    LocalDateTime  toLocalDateTime();
    SolarDateTime  toSolarDateTime();
    LunarDateTime  toLunarDateTime();
    GanZhiDateTime toGanZhiDateTime();
    String format(DateFormatType formatType, DateType dateType);
}
