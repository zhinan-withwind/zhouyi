package com.zhinan.zhouyi.out;

import com.zhinan.zhouyi.base.阴阳;
import com.zhinan.zhouyi.divine.八卦;
import com.zhinan.zhouyi.divine.六十四卦;

public class GuaOutputter extends Outputter {
    public static Outputter output(阴阳 yao, int color) {
        return output(color, 6, yao.equals(阴阳.阳) ? "——————" : "——  ——");
    }

    public static Outputter output(八卦 gua) {
        int color = colors[gua.getWuXing().getValue()];
        return output(gua.getYao(1), color)
                .append(output(gua.getYao(2), color), HORIZONTAL_ALIGN.LEFT, VERTICAL_ALIGN.TOP)
                .append(output(gua.getYao(3), color), HORIZONTAL_ALIGN.LEFT, VERTICAL_ALIGN.TOP);
    }

    public static Outputter output(六十四卦 gua, boolean withSeparate) {
        return output(gua.get下卦())
                .append( withSeparate ? output(TEXT_COLOR.NO_COLOR.getColor(), 6, "      ") : empty(), HORIZONTAL_ALIGN.LEFT, VERTICAL_ALIGN.TOP)
                .append(output(gua.get上卦()), HORIZONTAL_ALIGN.LEFT, VERTICAL_ALIGN.TOP)
                .append(output(colors[gua.getWuXing().getValue()], 6, gua.getName() + "\t"),
                        HORIZONTAL_ALIGN.LEFT, VERTICAL_ALIGN.BOTTOM);
    }
}
