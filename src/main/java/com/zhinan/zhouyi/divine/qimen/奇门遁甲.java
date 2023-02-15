package com.zhinan.zhouyi.divine.qimen;

import com.zhinan.zhouyi.base.*;
import com.zhinan.zhouyi.date.GanZhiDateTime;
import com.zhinan.zhouyi.divine.common.占卜;
import com.zhinan.zhouyi.effect.地支三合;
import lombok.Getter;
import run.zhinan.time.solar.SolarTerm;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Getter
public class 奇门遁甲 extends 占卜 {
    private static final 天干[] 六仪三奇 = {天干.戊, 天干.己, 天干.庚, 天干.辛, 天干.壬, 天干.癸, 天干.丁, 天干.丙, 天干.乙};

    GanZhiDateTime ganZhiDateTime;
    遁局 pattern;

    九星 dutyStar;
    八门 dutyDoor;
    int doorNum;

    List<宫位> palaceList = new ArrayList<>();

    public static 奇门遁甲 init(String question, LocalDateTime divineTime) {
        return init(question, divineTime, calculatePattern(divineTime));
    }

    public static int calculatePattern(LocalDateTime divineTime) {
        GanZhiDateTime ganZhiDateTime = GanZhiDateTime.of(divineTime);
        阴阳 yinYang = !SolarTerm.Z05_XIAZHI.of(divineTime.getYear()).getDateTime().isAfter(divineTime)
                && SolarTerm.Z11_DONGZHI.of(divineTime.getYear()).getDateTime().isAfter(divineTime) ? 阴阳.阴 : 阴阳.阳;
        return (1 - yinYang.getValue()) * 10 +
               (ganZhiDateTime.getGanZhiYear() .getGan().getValue() + 1 +
                ganZhiDateTime.getGanZhiMonth().getGan().getValue() + 1 +
                ganZhiDateTime.getGanZhiDay()  .getGan().getValue() + 1 +
                ganZhiDateTime.getGanZhiHour() .getGan().getValue() + 1) % 9 - 1;
    }

    public static 奇门遁甲 init(String question, LocalDateTime divineTime, Integer pattern) {
        奇门遁甲 pan = new 奇门遁甲();
        divineTime = divineTime == null ? LocalDateTime.now() : divineTime;
        pan.ganZhiDateTime = GanZhiDateTime.of(divineTime);
        if (pattern == null) pattern = calculatePattern(divineTime);
        Map<String, String> initInfo = new HashMap<>();
        initInfo.put("pattern", pattern.toString());
        pan.init(question, divineTime, initInfo);

        for (int i = 0; i < 9; i++) {
            宫位 palace = new 宫位(九宫.values()[i]);
            pan.palaceList.add(palace);
        }

        // 第一步 找时干
        干支 hour = pan.ganZhiDateTime.getGanZhiHour();

        // 第二步 找空亡
        地支[] empty = hour.getEmpty();

        // 第三步 找符头1
        地支 head1 = 地支.getByValue((empty[1].getValue() + 1) % 12);

        // 第四步 找符头2
        天干 head2 = 六仪.getByZhi(head1).gan;

        // 第五步 定几局
        pan.pattern = 遁局.getByValue(pattern);
        int type = pan.pattern.getNum();

        // 第六步 定阴阳
        阴阳 yinYang = pan.pattern.getYinYang();

        // 第七步 地盘干
        宫位 palace = pan.getPalace(九宫.getPalace(type));
        for (int i = 0; i < 9; i++) {
            palace.ground = 六仪三奇[i];
            if (yinYang.equals(阴阳.阳)) {
                palace = pan.getPalace(palace.getPalace().getNextPalace());
            } else {
                palace = pan.getPalace(palace.getPalace().getLastPalace());
            }
        }
        pan.palaceList.get(1).ground2 = pan.palaceList.get(4).ground;

        // 第八步 天地定位
        palace = 宫位.find(pan.palaceList, 宫位::getGround, hour.getGan());
        palace.shen = 八神.值符;
        palace.sky = head2;

        // 第九步 找小执符星和执使门
        palace = 宫位.find(pan.palaceList, 宫位::getGround, head2);
        pan.dutyStar = palace.getPalace().getStar();
        pan.dutyDoor = palace.getPalace().getDoor();
        pan.doorNum  = palace.getPalace().getAlterValue();

        // 第十步 定小执符星和执使门落宫
        palace = 宫位.find(pan.palaceList, 宫位::getShen, 八神.值符);
        palace.star  = pan.dutyStar;

        palace = pan.getPalace(九宫.getPalace(((hour.getZhi().getValue() - head1.getValue() + 12) % 12 + pan.doorNum) % 9));
        palace.door = pan.dutyDoor;

        // 第十一步 排八神
        palace = 宫位.find(pan.palaceList, 宫位::getShen, 八神.值符);
        for (八神 s : 八神.values()) {
            palace.shen = s;
            if (yinYang.isYang()) {
                assert palace.getPalace().getPalaceAfter() != null;
                palace = pan.getPalace(palace.getPalace().getPalaceAfter());
            } else {
                assert palace.getPalace().getPalaceBefore() != null;
                palace = pan.getPalace(palace.getPalace().getPalaceBefore());
            }
        }

        // 第十二步 排九星
        palace = 宫位.find(pan.palaceList, 宫位::getStar, pan.dutyStar);
        for (int i = 0; i < 8; i++) {
            palace.star = 九星.getByValue(pan.dutyStar.getValue() + i);
            assert palace.getPalace().getPalaceAfter() != null;
            palace = pan.getPalace(palace.getPalace().getPalaceAfter());
        }

        // 第十三步 排八门
        palace = 宫位.find(pan.palaceList, 宫位::getDoor, pan.dutyDoor);
        for (int i = 0; i < 8; i++) {
            palace.door = 八门.getByValue(pan.dutyDoor.getValue() + i);
            assert palace.getPalace().getPalaceAfter() != null;
            palace = pan.getPalace(palace.getPalace().getPalaceAfter());
        }

        // 第十四步 排天盘干
        palace = 宫位.find(pan.palaceList, 宫位::getSky , head2);
        宫位 ground = 宫位.find(pan.palaceList, 宫位::getGround, head2);
        for (int i = 0; i < 8; i++) {
            palace.sky  = ground.ground;
            palace.sky2 = ground.ground2;
            palace = pan.getPalace(palace.getPalace().getPalaceAfter());
            ground = pan.getPalace(ground.getPalace().getPalaceAfter());
        }

        // 第十五步 画空亡
        for (地支 z : empty) {
            pan.getPalace(九宫.getByZhi(z)).isEmpty = true;
        }

        // 第十六步 找马星
        for (地支三合 effect: 地支三合.values()) {
            for (元素 zhi : effect.getElements()) {
                if (zhi.equals(pan.ganZhiDateTime.getGanZhiHour().getZhi())) {
                    palace = pan.getPalace(九宫.getByZhi((地支) effect.getElements().get(0)).getOppositePalace());
                    palace.isHorse = true;
                    break;
                }
            }
            if (palace.isHorse) break;
        }

        // 第十七步 找暗干
        for (宫位 p : pan.palaceList) {
            if (!p.getPalace().equals(九宫.中五)) {
                palace = 宫位.find(pan.palaceList, 宫位::getDoor, p.palace.door);
            }
            palace.hidden = p.ground;
            palace.hidden2 = p.ground2;
        }

        return pan;
    }

    public 宫位 getPalace(九宫 palace) {
        return palaceList.get(palace.alterValue - 1);
    }
}
