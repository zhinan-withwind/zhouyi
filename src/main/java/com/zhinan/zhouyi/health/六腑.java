package com.zhinan.zhouyi.health;

import com.zhinan.zhouyi.base.五行;
import com.zhinan.zhouyi.base.地支;
import com.zhinan.zhouyi.base.天干;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum 六腑 {
    胆 (五行.木, 天干.乙, 地支.亥),
    胃 (五行.土, 天干.戊, 地支.未),
    小肠(五行.火, 天干.丙, 地支.酉),
    大肠(五行.金, 天干.庚, 地支.申),
    膀胱(五行.水, 天干.壬, 地支.子),
    三焦(五行.火, 天干.壬, 地支.子);

    五行 wuXing;
    天干 gan;
    地支 zhi;
}
