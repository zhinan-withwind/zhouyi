package com.zhinan.zhouyi.health;

import com.zhinan.zhouyi.base.五行;
import com.zhinan.zhouyi.base.天干;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum 部位 {
    头(五行.木, 天干.甲),
    颈(五行.木, 天干.乙),
    肩(五行.火, 天干.丙),
    心(五行.火, 天干.丁),
    肋(五行.土, 天干.戊),
    腹(五行.土, 天干.己),
    脐(五行.金, 天干.庚),
    股(五行.金, 天干.辛),
    胫(五行.水, 天干.壬),
    足(五行.水, 天干.癸);

    五行 wuXing;
    天干 gan;
}
