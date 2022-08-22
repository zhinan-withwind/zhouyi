package com.zhinan.zhouyi.out;

import com.zhinan.zhouyi.divine.qimen.九星;
import com.zhinan.zhouyi.divine.qimen.八门;
import com.zhinan.zhouyi.divine.qimen.奇门遁甲;
import com.zhinan.zhouyi.divine.qimen.宫位;

public class QiMenOutputter extends Outputter {
    private static final Outputter separator = output(TEXT_COLOR.NO_COLOR.getColor(), 1, "│");
    private static final Outputter right     = new Outputter(TEXT_COLOR.NO_COLOR.getColor(), 1, new String[] {"│", "│", "│", "│", "│", "│"});

    public static String outputName(String name) {
        return name.charAt(0) + "　" + name.charAt(1);
    }

    public static Outputter getLine4(宫位 palace) {
        return separator
                .join(output(TEXT_COLOR.NO_COLOR.getColor(), 7, "　　　　　　　"), HORIZONTAL_ALIGN.RIGHT, VERTICAL_ALIGN.CENTER)
                .join(output(colors[palace.getSky2() == null ? 5 : palace.getSky2().getWuXing().getValue()], 1, (palace.getSky2() == null ? "　" : palace.getSky2().getName())), HORIZONTAL_ALIGN.RIGHT, VERTICAL_ALIGN.CENTER)
                .join(output(colors[palace.getSky () == null ? 5 : palace.getSky ().getWuXing().getValue()], 1, (palace.getSky () == null ? "　" : palace.getSky ().getName())), HORIZONTAL_ALIGN.RIGHT, VERTICAL_ALIGN.CENTER);
    }

    public static Outputter getLine5(宫位 palace) {
        return separator
                .join(output(colors[palace.getPalace().getAlterGua().getWuXing().getValue()], 2, palace.getPalace().getName()), HORIZONTAL_ALIGN.RIGHT, VERTICAL_ALIGN.CENTER)
                .join(output(TEXT_COLOR.NO_COLOR.getColor(), 5, "　　　　　"), HORIZONTAL_ALIGN.RIGHT, VERTICAL_ALIGN.CENTER)
                .join(output(colors[palace.getGround2() == null ? 5 : palace.getGround2().getWuXing().getValue()], 1, (palace.getGround2() == null ? "　" : palace.getGround2().getName())), HORIZONTAL_ALIGN.RIGHT, VERTICAL_ALIGN.CENTER)
                .join(output(colors[palace.getGround () == null ? 5 : palace.getGround ().getWuXing().getValue()], 1, (palace.getGround () == null ? "　" : palace.getGround ().getName())), HORIZONTAL_ALIGN.RIGHT, VERTICAL_ALIGN.CENTER);
    }

    public static Outputter outputPalace(宫位 palace) {
        Outputter line0 = separator
                .join(output(colors[palace.getHidden () == null ? 5 : palace.getHidden( ).getWuXing().getValue()], 1, (palace.getHidden () == null ? "　" : palace.getHidden ().getName())), HORIZONTAL_ALIGN.RIGHT, VERTICAL_ALIGN.CENTER)
                .join(output(colors[palace.getHidden2() == null ? 5 : palace.getHidden2().getWuXing().getValue()], 1, (palace.getHidden2() == null ? "　" : palace.getHidden2().getName())), HORIZONTAL_ALIGN.RIGHT, VERTICAL_ALIGN.CENTER)
                .join(output(TEXT_COLOR.NO_COLOR.getColor(), 5, "　　　　　"), HORIZONTAL_ALIGN.RIGHT, VERTICAL_ALIGN.CENTER)
                .join(output(colors[2], 1, palace.isHorse() ? "马" : "　"     ), HORIZONTAL_ALIGN.RIGHT, VERTICAL_ALIGN.CENTER)
                .join(output(colors[4], 1, palace.isEmpty() ? "空" : "　"     ), HORIZONTAL_ALIGN.RIGHT, VERTICAL_ALIGN.CENTER);
        Outputter line1 = separator
                .join(output(colors[palace.getShen().getWuXing().getValue()], 9, "　　　" + outputName(palace.getShen().getName())     + "　　　"), HORIZONTAL_ALIGN.RIGHT, VERTICAL_ALIGN.CENTER);
        Outputter line2 = separator
                .join(output(colors[palace.getStar().getWuXing().getValue()], 9, "　　　" + outputName(palace.getStar().getName())     + "　　　"), HORIZONTAL_ALIGN.RIGHT, VERTICAL_ALIGN.CENTER);
        Outputter line3 = separator
                .join(output(colors[palace.getDoor().getWuXing().getValue()], 9, "　　　" + outputName(palace.getDoor().getFullName()) + "　　　"), HORIZONTAL_ALIGN.RIGHT, VERTICAL_ALIGN.CENTER);

        return  line0
                .append(line1, HORIZONTAL_ALIGN.CENTER, VERTICAL_ALIGN.BOTTOM)
                .append(line2, HORIZONTAL_ALIGN.CENTER, VERTICAL_ALIGN.BOTTOM)
                .append(line3, HORIZONTAL_ALIGN.CENTER, VERTICAL_ALIGN.BOTTOM)
                .append(getLine4(palace), HORIZONTAL_ALIGN.CENTER, VERTICAL_ALIGN.BOTTOM)
                .append(getLine5(palace), HORIZONTAL_ALIGN.CENTER, VERTICAL_ALIGN.BOTTOM);
    }

    public static Outputter outputPalace5(宫位 palace, 九星 dutyStar, 八门 dutyDoor) {
        Outputter line1 = separator
                .join(output(TEXT_COLOR.NO_COLOR.getColor(), 9, "　　　　　　　　　"), HORIZONTAL_ALIGN.RIGHT, VERTICAL_ALIGN.CENTER);
        Outputter line2 = separator
                .join(output(TEXT_COLOR.NO_COLOR.getColor(), 9, "　" + "小执符：　" + dutyStar.getName()     + "　"), HORIZONTAL_ALIGN.RIGHT, VERTICAL_ALIGN.CENTER);
        Outputter line3 = separator
                .join(output(TEXT_COLOR.NO_COLOR.getColor(), 9, "　" + "执使门：　" + dutyDoor.getFullName() + "　"), HORIZONTAL_ALIGN.RIGHT, VERTICAL_ALIGN.CENTER);
        return  line1
                .append(line1, HORIZONTAL_ALIGN.CENTER, VERTICAL_ALIGN.BOTTOM)
                .append(line2, HORIZONTAL_ALIGN.CENTER, VERTICAL_ALIGN.BOTTOM)
                .append(line3, HORIZONTAL_ALIGN.CENTER, VERTICAL_ALIGN.BOTTOM)
                .append(getLine4(palace), HORIZONTAL_ALIGN.CENTER, VERTICAL_ALIGN.BOTTOM)
                .append(getLine5(palace), HORIZONTAL_ALIGN.CENTER, VERTICAL_ALIGN.BOTTOM);
    }

    public static Outputter outputPan(奇门遁甲 pan) {
        Outputter upper  = outputPalace(pan.getPalaceList().get(8))
                .join(outputPalace(pan.getPalaceList().get(3)), HORIZONTAL_ALIGN.LEFT,  VERTICAL_ALIGN.CENTER)
                .join(outputPalace(pan.getPalaceList().get(1)), HORIZONTAL_ALIGN.RIGHT, VERTICAL_ALIGN.CENTER)
                .join(right, HORIZONTAL_ALIGN.RIGHT, VERTICAL_ALIGN.CENTER);

        Outputter middle = outputPalace5(pan.getPalaceList().get(4), pan.getDutyStar(), pan.getDutyDoor())
                .join(outputPalace(pan.getPalaceList().get(2)), HORIZONTAL_ALIGN.LEFT,  VERTICAL_ALIGN.CENTER)
                .join(outputPalace(pan.getPalaceList().get(6)), HORIZONTAL_ALIGN.RIGHT, VERTICAL_ALIGN.CENTER)
                .join(right, HORIZONTAL_ALIGN.RIGHT, VERTICAL_ALIGN.CENTER);

        Outputter lower  = outputPalace(pan.getPalaceList().get(0))
                .join(outputPalace(pan.getPalaceList().get(7)), HORIZONTAL_ALIGN.LEFT,  VERTICAL_ALIGN.CENTER)
                .join(outputPalace(pan.getPalaceList().get(5)), HORIZONTAL_ALIGN.RIGHT, VERTICAL_ALIGN.CENTER)
                .join(right, HORIZONTAL_ALIGN.RIGHT, VERTICAL_ALIGN.CENTER);

        return output(TEXT_COLOR.NO_COLOR.getColor(), 30, "┌───────────────┬───────────────┬───────────────┐")
                .append(upper, HORIZONTAL_ALIGN.CENTER, VERTICAL_ALIGN.BOTTOM)
                .append(output(TEXT_COLOR.NO_COLOR.getColor(), 30, "├───────────────┼───────────────┼───────────────┤"), HORIZONTAL_ALIGN.CENTER, VERTICAL_ALIGN.BOTTOM)
                .append(middle, HORIZONTAL_ALIGN.CENTER, VERTICAL_ALIGN.BOTTOM)
                .append(output(TEXT_COLOR.NO_COLOR.getColor(), 30, "├───────────────┼───────────────┼───────────────┤"), HORIZONTAL_ALIGN.CENTER, VERTICAL_ALIGN.BOTTOM)
                .append(lower, HORIZONTAL_ALIGN.CENTER, VERTICAL_ALIGN.BOTTOM)
                .append(output(TEXT_COLOR.NO_COLOR.getColor(), 30, "└───────────────┴───────────────┴───────────────┘"), HORIZONTAL_ALIGN.CENTER, VERTICAL_ALIGN.BOTTOM)

                ;
    }

}
