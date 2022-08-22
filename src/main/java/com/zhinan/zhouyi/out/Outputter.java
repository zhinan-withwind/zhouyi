package com.zhinan.zhouyi.out;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Outputter {
    enum DIRECTION_TYPE   {SINGLE, HORIZONTAL, VERTICAL}
    enum VERTICAL_ALIGN   {TOP, CENTER, BOTTOM}
    enum HORIZONTAL_ALIGN {LEFT, CENTER, RIGHT}
    enum TEXT_COLOR {
        BLACK, RED, GREEN, YELLOW, BLUE, PURPLE, CYAN, GRAY, WHITE, NO_COLOR;

        public static int getColor(int index) {
            return index + 30;
        }

        public int getColor() {
            return TEXT_COLOR.getColor(ordinal());
        }
    }

    public static int[] colors = new int[] {
            TEXT_COLOR.GREEN.getColor(), TEXT_COLOR.RED.getColor(), TEXT_COLOR.YELLOW.getColor(), TEXT_COLOR.WHITE.getColor(), TEXT_COLOR.BLUE.getColor(), TEXT_COLOR.NO_COLOR.getColor()};

    int color;
    int width;
    int height;
    List<String> lines;

    DIRECTION_TYPE   type   = DIRECTION_TYPE.SINGLE;
    VERTICAL_ALIGN   vAlign = VERTICAL_ALIGN.CENTER;
    HORIZONTAL_ALIGN hAlign = HORIZONTAL_ALIGN.CENTER;

    List<Outputter> outputters = new ArrayList<>();

    public Outputter() {
        this.color = TEXT_COLOR.NO_COLOR.getColor();
        this.width = 0;
        this.height = 0;
        this.lines = new ArrayList<>();
    }

    public Outputter(DIRECTION_TYPE type, HORIZONTAL_ALIGN hAlign, VERTICAL_ALIGN vAlign, List<Outputter> outputter) {
        this.type = type;
        this.vAlign = vAlign;
        this.hAlign = hAlign;
        this.outputters = outputter;
    }

    public Outputter(int color, int width, String[] lines) {
        this.color  = color;
        this.width  = width;
        this.lines  = Arrays.asList(lines);
        this.height = lines.length;
    }

    public Outputter(int color, int width, List<String> lines) {
        this.color  = color;
        this.width  = width;
        this.lines  = lines;
        this.height = lines.size();
    }

    public Outputter join(Outputter o, HORIZONTAL_ALIGN hAlign, VERTICAL_ALIGN vAlign) {
        o.vAlign = vAlign;
        Outputter outputter;
        if (this.type.equals(DIRECTION_TYPE.HORIZONTAL)) {
            if (o.type.equals(DIRECTION_TYPE.HORIZONTAL)) {
                if (hAlign.equals(HORIZONTAL_ALIGN.LEFT)) {
                    this.outputters.addAll(0, o.outputters);
                } else {
                    this.outputters.addAll(o.outputters);
                }
            } else {
                if (hAlign.equals(HORIZONTAL_ALIGN.LEFT)) {
                    this.outputters.add(0, o);
                } else {
                    this.outputters.add(o);
                }
            }
            outputter = this;
        } else {
            List<Outputter> outputters = new ArrayList<>();
            if (hAlign.equals(HORIZONTAL_ALIGN.LEFT)) {
                outputters.add(o);
                outputters.add(this);
            } else {
                outputters.add(this);
                outputters.add(o);
            }
            outputter = new Outputter(DIRECTION_TYPE.HORIZONTAL, HORIZONTAL_ALIGN.LEFT, vAlign, outputters);
        }
        outputter.width  = this.width + o.width;
        outputter.height = Math.max(this.height, o.height);
        return outputter;
    }

    public Outputter append(Outputter o, HORIZONTAL_ALIGN hAlign, VERTICAL_ALIGN vAlign) {
        Outputter outputter;
        if (this.type.equals(DIRECTION_TYPE.VERTICAL)) {
            if (o.type.equals(DIRECTION_TYPE.VERTICAL)) {
                if (vAlign.equals(VERTICAL_ALIGN.TOP)) {
                    this.outputters.addAll(0, o.outputters);
                } else {
                    this.outputters.addAll(o.outputters);
                }
            } else {
                if (vAlign.equals(VERTICAL_ALIGN.TOP)) {
                    this.outputters.add(0, o);
                } else {
                    this.outputters.add(o);
                }
            }
            outputter = this;
        } else {
            List<Outputter> outputters = new ArrayList<>();
            if (vAlign.equals(VERTICAL_ALIGN.TOP)) {
                outputters.add(o);
                outputters.add(this);
            } else {
                outputters.add(this);
                outputters.add(o);
            }
            outputter = new Outputter(DIRECTION_TYPE.VERTICAL, hAlign, vAlign, outputters);
        }
        outputter.width = Math.max(this.width, o.width);
        outputter.height = this.height + o.height;
        return outputter;
    }

    public Outputter build() {
        List<String> lines = new ArrayList<>();
        switch (this.type) {
            case SINGLE:
                if (this.color != TEXT_COLOR.NO_COLOR.getColor()) {
                    for (String line : this.lines) {
                        lines.add("\033[1;" + this.color + "m" + line + "\033[0m");
                    }
                } else {
                    lines = this.lines;
                }
                break;
            case HORIZONTAL:
                for (int i = 0; i < height; i++) {
                    StringBuilder line = new StringBuilder();
                    for (Outputter o : outputters) {
                        o = o.build();
                        if (o.height < this.height) {
                            switch (o.vAlign) {
                                case TOP:
                                    line.append(i < o.lines.size() ? o.lines.get(i) : getEmptyString(o.width));
                                    break;
                                case CENTER:
                                    line.append(i >= (this.height - o.height) / 2 && i < (this.height - o.height) / 2 + o.height ?
                                            o.lines.get(i - (this.height - o.height) / 2) : getEmptyString(o.width));
                                    break;
                                case BOTTOM:
                                    line.append(i >= this.height - o.height ? o.lines.get(i - (this.height - o.height)) : getEmptyString(o.width));
                            }
                        } else {
                            line.append(o.lines.get(i));
                        }
                    }
                    lines.add(line.toString());
                }
                break;
            case VERTICAL:
                for (Outputter o : outputters) {
                    o = o.build();
                    for (String line : o.lines) {
                        if (o.width < this.width) {
                            switch (o.hAlign) {
                                case LEFT:
                                    line = line + getEmptyString(this.width - o.width);
                                    break;
                                case CENTER:
                                    line = getEmptyString((this.width - o.width) / 2) + line
                                            + getEmptyString(this.width - line.length() - (this.width - o.width) / 2);
                                    break;
                                case RIGHT:
                                    line = getEmptyString(this.width - o.width) + line;
                            }

                        }
                        lines.add(line);
                    }
                }
        }
        return new Outputter(TEXT_COLOR.NO_COLOR.getColor(), this.width, lines);
    }

    public String output() {
        StringBuilder sb = new StringBuilder();
        for (String s : this.build().lines) {
            sb.append(s).append(System.lineSeparator());
        }
        return sb.toString();
    }

    public static String getEmptyString(int width) {
        StringBuilder line = new StringBuilder();
        for (int i = 0; i < width % 4; i++) {
            line.append(" ");
        }
        for (int i = 0; i < width / 4; i++) {
            line.append("\t");
        }
        return line.toString();
    }

    public static Outputter empty() {
        return new Outputter(TEXT_COLOR.NO_COLOR.getColor(), 0, new ArrayList<>());
    }

    public static Outputter output(int color, int width, String line) {
        return new Outputter(color, width, new String[] {line});
    }

    public static Outputter output(int color, int width) {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < (width + 1) / 2; i++) {
            sb.append("——");
        }
        return output(color, width, sb.toString());
    }

}
