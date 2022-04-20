package com.zhinan.zhouyi.date;

import com.zhinan.zhouyi.base.干支;

import java.time.LocalDateTime;
import java.util.List;

public interface DateTimeHolder {
    int getYear();
    int getMonth();
    int getDay();
    int getHour();
    int getMinute();
    boolean isLeap();
    LocalDateTime toLocalDateTime();
    List<干支> toGanZhi();
}
