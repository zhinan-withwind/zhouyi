package com.zhinan.zhouyi.fate.bazi;

import lombok.AllArgsConstructor;

@AllArgsConstructor
public enum 旺衰 {
    弱极(40 , 80 ),
    较弱(80 , 180),
    偏弱(180, 305),
    中和(280, 340),
    偏旺(306, 440),
    较旺(440, 570),
    旺极(570, 610);

    int min;
    int max;
}
