package com.zhinan.zhouyi.date;

import java.time.LocalDateTime;

public enum SolarTerm {
    立春(42467 ), 雨水(63836 ), 惊蛰(85337 ), 春分(107014), 清明(128867), 谷雨(150921),
    立夏(173149), 小满(195551), 芒种(218072), 夏至(240693), 小暑(263343), 大暑(285989),
    立秋(308563), 处暑(331033), 白露(353350), 秋分(375494), 寒露(397447), 霜降(419210),
    立冬(440795), 小雪(462224), 大雪(483532), 冬至(504758), 小寒(525948), 大寒(527156);

    int offset;

    SolarTerm(int offset) {
        this.offset = offset;
    }

    private final static LocalDateTime baseDate = LocalDateTime.of(1900, 1, 6, 2, 5);

    private final static SolarTerm[] majorTermList = {立春, 惊蛰, 清明, 立夏, 芒种, 小暑, 立秋, 白露, 寒露, 立冬, 大雪, 小寒};

    public static SolarTerm ofMajor(int index) {
        return majorTermList[index];
    }

    public int getValue() {
        return ordinal();
    }

    public String getName() { return name(); }

    public LocalDateTime of(int year) {
        long offset = new Double(525948.76 * (year - 1900)).longValue() + this.offset;
        return baseDate.plusMinutes(offset);
    }

    public SolarTerm roll(int amount) {
        return values()[(getValue() + amount + 24) % 24];
    }

}
