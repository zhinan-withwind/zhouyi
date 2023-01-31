package com.zhinan.zhouyi.name;

import com.zhinan.zhouyi.fate.bazi.八字;
import lombok.Getter;

@Getter
public class 姓名 {
    String name;
    String familyName;
    String givenName;
    boolean isCompoundSurname;
    汉字[] chars;
    boolean[] isGood;
    五格 fiveGrid;

    八字 bazi;

    int score = 0;
    int fateScore = 0;
    int gridScore = 0;

    public static 姓名 of(String name) {
        姓名 result = new 姓名();

        result.name = name;
        result.isCompoundSurname = 复姓.is(name);
        result.familyName = result.isCompoundSurname ? name.substring(0, 2) : name.substring(0, 1);
        result.givenName  = result.isCompoundSurname || name.length() == 4 ? name.substring(2) : name.substring(1);
        result.chars = new 汉字[name.length()];
        for (int i = 0; i < name.length(); i++) {
            result.chars[i] = 汉字.get(String.valueOf(name.charAt(i)));
        }
        result.fiveGrid = 五格.of(name);
        result.gridScore = result.fiveGrid.getScore();
        result.score = result.bazi == null ? result.gridScore : (result.gridScore + result.fateScore) / 2;
        return result;
    }

    public 姓名 with(八字 bazi) {
        this.bazi = bazi;
        for (汉字 c : this.chars) {
            int cScore = 10;
            if (bazi.getFatePattern().isGood(c.getWuXing())) {
                if (c.getWuXing().equals(bazi.getFatePattern().getFirstGoodGod())) {
                    cScore = 100;
                } else if (c.getWuXing().equals(bazi.getFatePattern().getSecondGoodGod())) {
                    cScore = 80;
                } else {
                    cScore = 60;
                }
            }
            fateScore += cScore / chars.length;
        }
        score = (gridScore + fateScore) / 2;
        return this;
    }
}
