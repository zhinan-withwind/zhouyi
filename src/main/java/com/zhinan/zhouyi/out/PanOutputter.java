package com.zhinan.zhouyi.out;

import com.zhinan.zhouyi.base.五行;
import com.zhinan.zhouyi.fate.简盘;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class PanOutputter extends Outputter {
    private static Map<String, 五行> map;

    简盘 pan;

    private PanOutputter(简盘 pan) {
        super(TEXT_COLOR.NO_COLOR.getColor(), 0, new ArrayList<>());
        this.pan = pan;

        Outputter result = output(pan);

        this.lines  = result.lines;
        this.width  = result.width;
        this.height = this.lines.size();
    }

    public static PanOutputter of(简盘 pan) {
        return new PanOutputter(pan);
    }

    public Map<String, 五行> getWuXingMap() {
        if (map == null) {
            map = new HashMap<>();
            pan.get天干属性().forEach((key, value) -> map.put(key, 五行.valueOf(value)));
            pan.get地支属性().forEach((key, value) -> map.put(key, 五行.valueOf(value)));
            pan.get十神属性().forEach((key, value) -> map.put(key, 五行.valueOf(value)));
        }
        return map;
    }

    public int getColor(String value, boolean inverse) {
        int color = TEXT_COLOR.NO_COLOR.getColor();
        if (getWuXingMap().get(value) != null) {
            color = colors[getWuXingMap().get(value).getValue()] + (inverse ? 10 : 0);
        }
        return color;
    }

    public Outputter output(List<String> line) {
        Outputter outputter = empty();
        for (int i = 0; i < line.size(); i++) {
            String text = line.get(i);
            int color = getColor(text, false);
            switch (text.length()) {
                case 1:
                    text = "\t " + text + "\t\t";
                    break;
                case 2:
                    text = "\t"  + text + "\t\t";
                    break;
                case 3:
                    text = "   " + text + "\t";
                    break;
                default:
                    text = "  "  + text + "\t";
            }
            outputter = outputter.join(output(color, 12, text), HORIZONTAL_ALIGN.RIGHT, VERTICAL_ALIGN.TOP);

            if (i == 3) {
                outputter = outputter.join(output(TEXT_COLOR.NO_COLOR.getColor(), 4, "  |\t" ), HORIZONTAL_ALIGN.RIGHT, VERTICAL_ALIGN.TOP);
            }
        }
        return outputter;
    }

    public Outputter output(List<String[]> 藏干, List<String[]> 支神) {
        Outputter hiddenGan = empty();
        for (int i = 0; i < 藏干.size(); i++) {
            Outputter hiddenGod = empty();
            for (int j = 0; j < 藏干.get(i).length; j++) {
                int color = getColor(藏干.get(i)[j], false);
                String text = "  "  + 藏干.get(i)[j] + " " + 支神.get(i)[j] + "\t";
                hiddenGod = hiddenGod.append(output(color, 12, text), HORIZONTAL_ALIGN.LEFT, VERTICAL_ALIGN.BOTTOM);
            }
            hiddenGan = hiddenGan.join(hiddenGod, HORIZONTAL_ALIGN.RIGHT, VERTICAL_ALIGN.TOP);
            if (i == 3) {
                hiddenGan = hiddenGan.join(new Outputter(TEXT_COLOR.NO_COLOR.getColor(), 4, new String[] {"  |\t", "  |\t", "  |\t"} ),
                        HORIZONTAL_ALIGN.RIGHT, VERTICAL_ALIGN.TOP);
            }
        }
        return hiddenGan;
    }

    public Outputter outputLuckList(List<String[]> yunList) {
        Outputter outputter = empty();
        for (String[] yun : yunList) {
            int cellWidth = 12;

            outputter = outputter.join(new Outputter(TEXT_COLOR.NO_COLOR.getColor(), 12, new ArrayList<>())
                        .append(output(TEXT_COLOR.NO_COLOR.getColor(), 4, (yun[13].equals("大运") || yun[13].equals("年运") || yun[13].equals("时运") ? " " : "") + yun[0]), HORIZONTAL_ALIGN.CENTER, VERTICAL_ALIGN.BOTTOM)
                        .append(output(TEXT_COLOR.NO_COLOR.getColor(), 4, (yun[13].equals("时运") ? " " : "") + yun[1]), HORIZONTAL_ALIGN.CENTER, VERTICAL_ALIGN.BOTTOM)
                        .append(output(getColor(yun[2],false), cellWidth, "\t " + yun[2] + "\t\t"), HORIZONTAL_ALIGN.LEFT, VERTICAL_ALIGN.BOTTOM)
                        .append(output(TEXT_COLOR.NO_COLOR.getColor(), cellWidth - 7, "\t ")
                                        .join(output(getColor(yun[3],Boolean.parseBoolean(yun[6])), 2, yun[3]), HORIZONTAL_ALIGN.RIGHT, VERTICAL_ALIGN.TOP)
                                        .join(output(TEXT_COLOR.NO_COLOR.getColor(), 5, "\t\t"), HORIZONTAL_ALIGN.RIGHT, VERTICAL_ALIGN.TOP)
                                , HORIZONTAL_ALIGN.CENTER, VERTICAL_ALIGN.BOTTOM)
                        .append(output(TEXT_COLOR.NO_COLOR.getColor(), cellWidth -7, "\t ")
                                        .join(output(getColor(yun[4],Boolean.parseBoolean(yun[7])), 2, yun[3]), HORIZONTAL_ALIGN.RIGHT, VERTICAL_ALIGN.TOP)
                                        .join(output(TEXT_COLOR.NO_COLOR.getColor(), 5, "\t\t"), HORIZONTAL_ALIGN.RIGHT, VERTICAL_ALIGN.TOP)
                                , HORIZONTAL_ALIGN.CENTER, VERTICAL_ALIGN.BOTTOM)
                        .append(output(getColor(yun[5],false), cellWidth, "\t " + yun[5] + "\t\t"), HORIZONTAL_ALIGN.LEFT, VERTICAL_ALIGN.BOTTOM)
                                , HORIZONTAL_ALIGN.RIGHT, VERTICAL_ALIGN.TOP)
            ;
        }
        return outputter;
    }

    public Outputter output(简盘 pan) {
        int width = 142;
        Outputter outputter = empty()
                .append(output(TEXT_COLOR.NO_COLOR.getColor(), width), HORIZONTAL_ALIGN.LEFT, VERTICAL_ALIGN.BOTTOM)
                .append(output(TEXT_COLOR.NO_COLOR.getColor(), width,  pan.get阳历生日()), HORIZONTAL_ALIGN.LEFT, VERTICAL_ALIGN.BOTTOM)
                .append(output(TEXT_COLOR.NO_COLOR.getColor(), width,  pan.get阴历生日()), HORIZONTAL_ALIGN.LEFT, VERTICAL_ALIGN.BOTTOM)
                .append(output(TEXT_COLOR.NO_COLOR.getColor(), width), HORIZONTAL_ALIGN.LEFT, VERTICAL_ALIGN.BOTTOM)
                .append(output(pan.get干神()), HORIZONTAL_ALIGN.LEFT, VERTICAL_ALIGN.BOTTOM)
                .append(output(pan.get天干()), HORIZONTAL_ALIGN.LEFT, VERTICAL_ALIGN.BOTTOM)
                .append(output(pan.get地支()), HORIZONTAL_ALIGN.LEFT, VERTICAL_ALIGN.BOTTOM)
                .append(output(pan.get藏干(), pan.get支神()), HORIZONTAL_ALIGN.LEFT, VERTICAL_ALIGN.BOTTOM)
                .append(output(pan.get星运()), HORIZONTAL_ALIGN.LEFT, VERTICAL_ALIGN.BOTTOM)
                .append(output(pan.get自坐()), HORIZONTAL_ALIGN.LEFT, VERTICAL_ALIGN.BOTTOM)
                .append(output(pan.get空亡()), HORIZONTAL_ALIGN.LEFT, VERTICAL_ALIGN.BOTTOM)
                .append(output(pan.get纳音()), HORIZONTAL_ALIGN.LEFT, VERTICAL_ALIGN.BOTTOM)
                .append(output(TEXT_COLOR.NO_COLOR.getColor(), width), HORIZONTAL_ALIGN.LEFT, VERTICAL_ALIGN.BOTTOM);
        if (pan.get大运() != null) {
            outputter = outputter
                .append(outputLuckList(pan.get大运()), HORIZONTAL_ALIGN.LEFT, VERTICAL_ALIGN.BOTTOM)
                .append(output(TEXT_COLOR.NO_COLOR.getColor(), width), HORIZONTAL_ALIGN.LEFT, VERTICAL_ALIGN.BOTTOM);
        }
        if (pan.get流年() != null) {
            outputter = outputter
                .append(outputLuckList(pan.get流年()), HORIZONTAL_ALIGN.LEFT, VERTICAL_ALIGN.BOTTOM)
                .append(output(TEXT_COLOR.NO_COLOR.getColor(), width), HORIZONTAL_ALIGN.LEFT, VERTICAL_ALIGN.BOTTOM);
        }
        if (pan.get流月() != null) {
            outputter = outputter
                .append(outputLuckList(pan.get流月()), HORIZONTAL_ALIGN.LEFT, VERTICAL_ALIGN.BOTTOM)
                .append(output(TEXT_COLOR.NO_COLOR.getColor(), width), HORIZONTAL_ALIGN.LEFT, VERTICAL_ALIGN.BOTTOM);
        }
        if (pan.get流日() != null) {
            outputter = outputter
                .append(outputLuckList(pan.get流日().subList(0, 10)), HORIZONTAL_ALIGN.LEFT, VERTICAL_ALIGN.BOTTOM)
                .append(outputLuckList(pan.get流日().subList(10, 20)), HORIZONTAL_ALIGN.LEFT, VERTICAL_ALIGN.BOTTOM)
                .append(outputLuckList(pan.get流日().subList(20, pan.get流日().size())), HORIZONTAL_ALIGN.LEFT, VERTICAL_ALIGN.BOTTOM)
                .append(output(TEXT_COLOR.NO_COLOR.getColor(), width), HORIZONTAL_ALIGN.LEFT, VERTICAL_ALIGN.BOTTOM);
        }
        if (pan.get流时() != null) {
            outputter = outputter
                .append(outputLuckList(pan.get流时()), HORIZONTAL_ALIGN.LEFT, VERTICAL_ALIGN.BOTTOM)
                .append(output(TEXT_COLOR.NO_COLOR.getColor(), width), HORIZONTAL_ALIGN.LEFT, VERTICAL_ALIGN.BOTTOM);
        }

        return outputter.build();
    }
}
