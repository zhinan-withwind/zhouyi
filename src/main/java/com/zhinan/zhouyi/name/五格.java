package com.zhinan.zhouyi.name;

import lombok.Getter;

@Getter
public class 五格 {
    int[] values;
    int   score;

    public 五格(int sky, int human, int earth, int total, int extra) {
        values = new int[] {sky, human, earth, total, extra};
        score  = new Double(get天格().jiXiong.getScore() * 0.03
                + get人格().jiXiong.getScore() * 0.4
                + get地格().jiXiong.getScore() * 0.3
                + get总格().jiXiong.getScore() * 0.2
                + get外格().jiXiong.getScore() * 0.07).intValue();
    }

    public static 五格 of(String name) {
        五格 result;
        boolean isCompoundSurname = 复姓.is(name);
        String familyName = isCompoundSurname || name.length() == 4 ? name.substring(0, 2) : name.substring(0, 1);
        String givenName  = isCompoundSurname || name.length() == 4 ? name.substring(2) : name.substring(1);
        int A = familyName.length() > 1 ? 汉字.get(familyName.substring(0, 1)).getStrokeNum() : 1;
        int B = familyName.length() > 1 ? 汉字.get(familyName.substring(1, 2)).getStrokeNum() :
                汉字.get(familyName.substring(0, 1)).getStrokeNum();
        int C = 汉字.get(givenName.substring(0, 1)).getStrokeNum();
        int D = givenName.length()  > 1 ? 汉字.get(givenName.substring(1, 2)).getStrokeNum() : 1;
        int sky, human, earth, total, extra;
        if (name.length() == 4 && !isCompoundSurname) {
            sky   = A + 1;
            human = A + B;
            earth = B + C + D;
            total = A + B + C + D;
            extra = C + D + 1;
        } else {
            sky   = A + B;
            human = B + C;
            earth = C + D;
            total = (A == 1 ? 0 : A) + B + C + (D == 1 ? 0 : D);
            extra = A + D;
        }
        result = new 五格(sky, human, earth, total, extra);
        return result;
    }

    public 数理 get天格() {
        return 数理.getByScore(values[0]);
    }

    public 数理 get人格() {
        return 数理.getByScore(values[1]);
    }

    public 数理 get地格() {
        return 数理.getByScore(values[2]);
    }

    public 数理 get总格() {
        return 数理.getByScore(values[3]);
    }

    public 数理 get外格() {
        return 数理.getByScore(values[4]);
    }

    @Override
    public String toString() {
        return
                System.lineSeparator() + "天格：" + get天格() + "，" +
                System.lineSeparator() + "人格：" + get人格() + "，" +
                System.lineSeparator() + "地格：" + get地格() + "，" +
                System.lineSeparator() + "总格：" + get总格() + "，" +
                System.lineSeparator() + "外格：" + get外格() + "，";
    }
}
