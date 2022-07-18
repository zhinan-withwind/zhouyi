package com.zhinan.zhouyi.service;

import com.zhinan.zhouyi.fate.bazi.命盘;
import com.zhinan.zhouyi.fate.bazi.简盘;
import com.zhinan.zhouyi.fate.star.星盘;

import java.time.LocalDateTime;

public class ZhouYiService {

    public static 简盘 bazi(LocalDateTime birthday, int sex) {
        return 命盘.of(birthday, sex).simplify();
    }

    public static 星盘 ziwei(LocalDateTime birthday, int sex) {
        return 星盘.of(birthday, sex);
    }
}
