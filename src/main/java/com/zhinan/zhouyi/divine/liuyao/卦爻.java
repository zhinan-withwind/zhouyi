package com.zhinan.zhouyi.divine.liuyao;

import com.zhinan.zhouyi.base.五行;
import com.zhinan.zhouyi.base.地支;
import com.zhinan.zhouyi.base.阴阳;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;

@Data
@AllArgsConstructor
public class 卦爻 {
    地支 zhi;
    阴阳 yinYang;
    五行 wuXing;
    六亲 relation;
    六神 animal;
    位置 position;

    boolean shi;
    boolean ying;
    boolean change;
    boolean fly;

}
