package com.zhinan.zhouyi.date;

public abstract class BaseDateTime implements DateTimeHolder {
    @Override
    public boolean isLeap() {
        return false;
    }

    @Override
    public String format(DateFormatType formatType, DateType dateType) {
        return DateTimeFormatter.getInstance(this).format(formatType, dateType);
    }

    public String format(DateFormatType formatType) {
        return DateTimeFormatter.getInstance(this).format(formatType, DateType.DATETIME);
    }
}
