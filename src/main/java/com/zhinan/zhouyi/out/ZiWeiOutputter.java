package com.zhinan.zhouyi.out;

public class ZiWeiOutputter extends Outputter {
    private static final Outputter separator = output(Outputter.TEXT_COLOR.NO_COLOR.getColor(), 1, "│");
    private static final Outputter right     = new Outputter(Outputter.TEXT_COLOR.NO_COLOR.getColor(), 1, new String[] {"│", "│", "│", "│", "│", "│"});


}
