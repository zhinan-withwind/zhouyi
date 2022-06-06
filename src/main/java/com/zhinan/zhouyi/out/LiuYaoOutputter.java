package com.zhinan.zhouyi.out;

import com.zhinan.zhouyi.base.阴阳;
import com.zhinan.zhouyi.divine.liuyao.六爻;
import com.zhinan.zhouyi.divine.liuyao.卦爻;

public class LiuYaoOutputter extends GuaOutputter {
    public static Outputter output(六爻 gua) {
        Outputter changes    = output(TEXT_COLOR.NO_COLOR.getColor(), 8, getEmptyString(8));
        Outputter animals    = output(TEXT_COLOR.NO_COLOR.getColor(), 8, getEmptyString(8));
        Outputter hiddenGod  = output(TEXT_COLOR.NO_COLOR.getColor(), 8, getEmptyString(8));
        Outputter originalZ  = output(TEXT_COLOR.NO_COLOR.getColor(), 4, getEmptyString(4));
        Outputter originalR  = output(TEXT_COLOR.NO_COLOR.getColor(), 4, getEmptyString(4));
        Outputter originalSY = output(TEXT_COLOR.NO_COLOR.getColor(), 8, getEmptyString(8));
        Outputter resultZ    = output(TEXT_COLOR.NO_COLOR.getColor(), 4, getEmptyString(4));
        Outputter resultR    = output(TEXT_COLOR.NO_COLOR.getColor(), 4, getEmptyString(4));
        for (int i = 0; i < 6; i++) {
            卦爻 oYao = gua.getOriginalGua().getYaoList().get(i);
            卦爻 rYao = gua.getResultGua()  .getYaoList().get(i);
            String change = oYao.isChange() ? " " + (oYao.getYinYang().equals(阴阳.阳) ? "◎ → " : "✕ → ") + "\t\t" : "     \t\t";
            changes = changes.append(
                    output(change.contains("◎") ? TEXT_COLOR.RED.getColor() : TEXT_COLOR.CYAN.getColor(), 4, change),
                    HORIZONTAL_ALIGN.LEFT, VERTICAL_ALIGN.TOP);
            originalZ = originalZ.append(
                    output(colors[oYao.getWuXing().getValue()], 4, oYao.getZhi().getName() + oYao.getWuXing().getName() + " "),
                    HORIZONTAL_ALIGN.LEFT, VERTICAL_ALIGN.TOP);
            originalR = originalR.append(
                    output(colors[oYao.getWuXing().getValue()], 4, oYao.getRelation().getName() + " "),
                    HORIZONTAL_ALIGN.LEFT, VERTICAL_ALIGN.TOP);
            originalSY = originalSY.append(
                    output(TEXT_COLOR.WHITE.getColor(), 4, " " + (oYao.isShi() ? "世\t" : oYao.isYing() ? "应\t" : "\t")),
                    HORIZONTAL_ALIGN.LEFT, VERTICAL_ALIGN.TOP);
            animals = animals.append(
                    output(colors[oYao.getAnimal().getWuXing().getValue()], 8, oYao.getAnimal().name() + "  \t"),
                    HORIZONTAL_ALIGN.LEFT, VERTICAL_ALIGN.TOP);
            卦爻 hidden = gua.getOriginalGua().getHiddenMap().get(i);
            hiddenGod  = hiddenGod.append((hidden != null ?
                            output(colors[hidden.getWuXing().getValue()], 8,
                                    hidden.getZhi().getName() + hidden.getWuXing().getName() + "\t" + hidden.getRelation().getName() + "\t")
                            : output(TEXT_COLOR.NO_COLOR.getColor(), 8, getEmptyString(8))),
                    HORIZONTAL_ALIGN.LEFT, VERTICAL_ALIGN.TOP);
            resultZ = resultZ.append(
                    output(colors[rYao.getWuXing().getValue()], 4, " " + rYao.getZhi().getName() + rYao.getWuXing().getName()),
                    HORIZONTAL_ALIGN.LEFT, VERTICAL_ALIGN.TOP);
            resultR = resultR.append(
                    output(colors[rYao.getWuXing().getValue()], 4, " " + rYao.getRelation().name()),
                    HORIZONTAL_ALIGN.LEFT, VERTICAL_ALIGN.TOP);
        }

        int width = 64;
        return output(gua.getOriginalGua().getGua(), false)
                .join(originalZ,  HORIZONTAL_ALIGN.LEFT,  VERTICAL_ALIGN.TOP)
                .join(originalR,  HORIZONTAL_ALIGN.LEFT,  VERTICAL_ALIGN.TOP)
                .join(hiddenGod,  HORIZONTAL_ALIGN.LEFT,  VERTICAL_ALIGN.TOP)
                .join(animals,    HORIZONTAL_ALIGN.LEFT,  VERTICAL_ALIGN.TOP)
                .join(originalSY, HORIZONTAL_ALIGN.RIGHT, VERTICAL_ALIGN.TOP)
                .join(changes,    HORIZONTAL_ALIGN.RIGHT, VERTICAL_ALIGN.TOP)
                .join(output(gua.getResultGua().getGua(), false), HORIZONTAL_ALIGN.RIGHT, VERTICAL_ALIGN.TOP)
                .join(resultZ,    HORIZONTAL_ALIGN.RIGHT, VERTICAL_ALIGN.TOP)
                .join(resultR,    HORIZONTAL_ALIGN.RIGHT, VERTICAL_ALIGN.TOP)
                .append(output(TEXT_COLOR.NO_COLOR.getColor(), width), HORIZONTAL_ALIGN.LEFT, VERTICAL_ALIGN.TOP)
                .append(output(TEXT_COLOR.WHITE.getColor(),    width, gua.getDate() + " 问：" + gua.getQuestion()), HORIZONTAL_ALIGN.LEFT, VERTICAL_ALIGN.TOP)
                .append(output(TEXT_COLOR.NO_COLOR.getColor(), width), HORIZONTAL_ALIGN.LEFT, VERTICAL_ALIGN.TOP)
                .append(output(TEXT_COLOR.YELLOW.getColor(), 7, "六 爻 排 卦"), HORIZONTAL_ALIGN.CENTER, VERTICAL_ALIGN.TOP)
                .append(output(TEXT_COLOR.NO_COLOR.getColor(), width), HORIZONTAL_ALIGN.LEFT, VERTICAL_ALIGN.TOP)
                .append(output(TEXT_COLOR.NO_COLOR.getColor(), width), HORIZONTAL_ALIGN.LEFT, VERTICAL_ALIGN.BOTTOM);

    }
}
