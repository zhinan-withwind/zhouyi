package com.zhinan.zhouyi.out;

import com.zhinan.zhouyi.base.生克;
import com.zhinan.zhouyi.desc.卦辞描述器;
import com.zhinan.zhouyi.divine.meihua.梅花易数;
import com.zhinan.zhouyi.divine.common.八卦;
import com.zhinan.zhouyi.divine.common.六十四卦;

public class MeiHuaOutputter extends GuaOutputter {

    public static Outputter output(String description) {
        return output(TEXT_COLOR.NO_COLOR.getColor(), 10 , description);
    }

    public static Outputter output(六十四卦 gua, int change) {
        Outputter right = new Outputter(TEXT_COLOR.NO_COLOR.getColor(), 2, new String[] {
                        change == 6 ? " ◎" : "  ", change == 5 ? " ◎" : "  ", change == 4 ? " ◎" : "  ", "  ",
                        change == 3 ? " ◎" : "  ", change == 2 ? " ◎" : "  ", change == 1 ? " ◎" : "  ", ""});

        八卦 t = change > 3 ? gua.get下卦() : gua.get上卦();
        八卦 y = change > 3 ? gua.get上卦() : gua.get下卦();
        生克 s = t.getWuXing().compare(y.getWuXing());
        boolean isShengKe  = s.equals(生克.生) || s.equals(生克.克);
        String  symbol     = s.equals(生克.同) ? "|" : (change > 3 ? (isShengKe ? "↓" : "↑") : (isShengKe ? "↑" : "↓"));
        boolean isShengXie = s.equals(生克.生) || s.equals(生克.泄);

        String[] 状态 = {"大吉", "中吉", "较差", "小吉", "不宜"};

        String description = 卦辞描述器.describe(gua, 卦辞描述器.描述类型.基本含义);

        Outputter name = output(TEXT_COLOR.NO_COLOR.getColor(),             8, "\t\t")
                .append(output(colors[gua.get上卦().getWuXing().getValue()], 8, " " + gua.get上卦().getName() + gua.get上卦().getWuXing().getName() + "\t"), HORIZONTAL_ALIGN.LEFT, VERTICAL_ALIGN.BOTTOM)
                .append(output(colors[gua.get上卦().getWuXing().getValue()], 8, "  " + symbol + "\t\t"), HORIZONTAL_ALIGN.LEFT, VERTICAL_ALIGN.BOTTOM)
                .append(output(s.equals(生克.同) ? TEXT_COLOR.YELLOW.getColor() : (isShengXie ? TEXT_COLOR.GREEN.getColor() : TEXT_COLOR.RED.getColor()), 8,
                        "  "  + (s.equals(生克.同) ? "同" : isShengXie ? "生" : "克") + "\t"), HORIZONTAL_ALIGN.LEFT, VERTICAL_ALIGN.BOTTOM)
                .append(output(TEXT_COLOR.NO_COLOR.getColor(),              8, "  " + symbol + "\t\t"), HORIZONTAL_ALIGN.LEFT, VERTICAL_ALIGN.BOTTOM)
                .append(output(colors[gua.get下卦().getWuXing().getValue()], 8, " "  + gua.get下卦().getName() + gua.get下卦().getWuXing().getName() + "\t"), HORIZONTAL_ALIGN.LEFT, VERTICAL_ALIGN.BOTTOM)
                .append(output(TEXT_COLOR.NO_COLOR.getColor(),              8, "\t\t"), HORIZONTAL_ALIGN.LEFT, VERTICAL_ALIGN.BOTTOM)
                .append(output(状态[s.getValue()].contains("吉") ? TEXT_COLOR.GREEN.getColor() : TEXT_COLOR.RED.getColor(), 8, " "  + 状态[s.getValue()] + "\t"), HORIZONTAL_ALIGN.LEFT, VERTICAL_ALIGN.BOTTOM)
//                .append(output(TEXT_COLOR.NO_COLOR.getColor(), 16), HORIZONTAL_ALIGN.LEFT, VERTICAL_ALIGN.BOTTOM)
//                .append(output(TEXT_COLOR.NO_COLOR.getColor(), description.length(), description), HORIZONTAL_ALIGN.LEFT, VERTICAL_ALIGN.BOTTOM)
                ;
        return  output(gua, true)
                .join(right, HORIZONTAL_ALIGN.RIGHT, VERTICAL_ALIGN.TOP)
                .join(name , HORIZONTAL_ALIGN.RIGHT, VERTICAL_ALIGN.TOP);
    }



    public static Outputter output(梅花易数 d) {
        Outputter left  = new Outputter(TEXT_COLOR.NO_COLOR.getColor(), 4,
                new String[] {"\t", (d.getChange() > 3 ? "用" : "体") + "\t", "\t", "\t", "\t", (d.getChange() > 3 ? "体" : "用") + "\t", "\t"});

        int width = 52;
        return output(d.getOriginal(), d.getChange())
                .join(output(d.getProcess(), d.getChange()), HORIZONTAL_ALIGN.RIGHT, VERTICAL_ALIGN.CENTER)
                .join(output(d.getResult(),  d.getChange()), HORIZONTAL_ALIGN.RIGHT, VERTICAL_ALIGN.CENTER)
                .join(left, HORIZONTAL_ALIGN.LEFT,  VERTICAL_ALIGN.CENTER)
                .append(output(TEXT_COLOR.NO_COLOR.getColor(), width), HORIZONTAL_ALIGN.LEFT, VERTICAL_ALIGN.TOP)
                .append(output(TEXT_COLOR.PURPLE.getColor()  ,12, "🌸 梅 花 易 数"), HORIZONTAL_ALIGN.CENTER, VERTICAL_ALIGN.TOP)
                .append(output(TEXT_COLOR.NO_COLOR.getColor(), width), HORIZONTAL_ALIGN.LEFT, VERTICAL_ALIGN.TOP)
                .append(output(TEXT_COLOR.NO_COLOR.getColor(), width), HORIZONTAL_ALIGN.LEFT, VERTICAL_ALIGN.BOTTOM);
    }
}
