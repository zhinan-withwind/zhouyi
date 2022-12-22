package com.zhinan.zhouyi.divine.other;

import lombok.AllArgsConstructor;

import java.time.LocalDateTime;

@AllArgsConstructor
public class 合婚 {
    LocalDateTime mBirthday;
    LocalDateTime wBirthday;

    public static 合婚 of(LocalDateTime mBirthday, LocalDateTime wBirthday) {
        return new 合婚(mBirthday, wBirthday);
    }
}
