package com.zhinan.zhouyi.fate.util;

import com.zhinan.zhouyi.base.*;
import com.zhinan.zhouyi.common.ColorSeries;
import com.zhinan.zhouyi.common.Direction;
import com.zhinan.zhouyi.common.Region;
import com.zhinan.zhouyi.effect.元素类别;
import com.zhinan.zhouyi.effect.地支六冲;
import com.zhinan.zhouyi.fate.bazi.*;
import com.zhinan.zhouyi.fate.luck.运势;
import lombok.Getter;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Getter
public class 财运 {
    八字 bazi;
    命主 fate;
    FatePattern pattern;
    五行 wealthWuXing;
    ColorSeries color;
    天干 mainWealthGan, partialWealthGan;
    地支 mainWealthZhi, partialWealthZhi;
    地支 wealthBank;
    boolean hasBank;
    boolean isBankAlwaysOpen;
    boolean isGood;

    List<List<List<元素类别>>> wealthElements;

    List<运势> decadeLuckList = new ArrayList<>();
    List<运势> yearLuckList   = new ArrayList<>();
    List<运势> bankOpenList   = new ArrayList<>();

    List<Direction> directions = new ArrayList<>();
    List<Region>    regions    = new ArrayList<>();

    public static 财运 of(LocalDateTime birthday, int sex) {
        return of(八字.of(birthday, sex));
    }

    public static 财运 of(八字 bazi) {
        财运 wealth  = new 财运();

        wealth.fate    = bazi.getFate();
        wealth.pattern = bazi.getFatePattern();

        wealth.bazi = bazi;
        wealth.wealthWuXing     = wealth.fate.get耗();
        wealth.color = ColorSeries.getByWuXing(wealth.wealthWuXing);
        wealth.mainWealthGan    = 天干.getByYYWX(wealth.fate.getGan().getYinYang().inverse(), wealth.wealthWuXing);
        wealth.partialWealthGan = 天干.getByYYWX(wealth.fate.getGan().getYinYang(), wealth.wealthWuXing);
        阴阳 mainYinYang = wealth.wealthWuXing.equals(五行.火) ||  wealth.wealthWuXing.equals(五行.水) ?
                wealth.fate.getGan().getYinYang() : wealth.fate.getGan().getYinYang().inverse();

        wealth.mainWealthZhi    = 地支.getByYYWX(mainYinYang, wealth.wealthWuXing);
        wealth.partialWealthZhi = 地支.getByYYWX(mainYinYang.inverse(), wealth.wealthWuXing);
        wealth.wealthBank       = 地支.getBank(wealth.wealthWuXing);

        wealth.isGood  = wealth.pattern.isGood(wealth.wealthWuXing);

        命盘 pan = 命盘.of(bazi.getBirthday(), bazi.getSex().getValue()).atDecade(LocalDateTime.now().getYear());
        wealth.hasBank = wealth.wealthBank != null
                && bazi.getFourColumn().stream().anyMatch(ganZhi -> ganZhi.getZhi().equals(wealth.wealthBank));
        if (wealth.hasBank) {
            元素 element = 地支六冲.getByElement(wealth.wealthBank).getOther(wealth.wealthBank);
            wealth.isBankAlwaysOpen = bazi.getFourColumn().stream().anyMatch(ganZhi -> ganZhi.getZhi().equals(element));
            wealth.bankOpenList = pan.getDecadeLuckList().stream().filter(ganZhi -> ganZhi.getZhi().equals(element))
                    .collect(Collectors.toList());
        }

        // 身弱
        if (!wealth.pattern.isStrong()) {
            List<运势> decadeHealthGoodList = find(pan.getDecadeLuckList(),
                    天干.getByYYWX(阴阳.阴, wealth.fate.get生()), 天干.getByYYWX(阴阳.阳, wealth.fate.get生()),
                    天干.getByYYWX(阴阳.阴, wealth.fate.get同()), 天干.getByYYWX(阴阳.阳, wealth.fate.get同()),
                    地支.getByYYWX(阴阳.阴, wealth.fate.get生()), 地支.getByYYWX(阴阳.阳, wealth.fate.get生()),
                    地支.getByYYWX(阴阳.阴, wealth.fate.get同()), 地支.getByYYWX(阴阳.阳, wealth.fate.get同())
            );

            List<运势> yearHealthGoodList = find(pan.getYearLuckList(),
                    天干.getByYYWX(阴阳.阴, wealth.fate.get生()), 天干.getByYYWX(阴阳.阳, wealth.fate.get生()),
                    天干.getByYYWX(阴阳.阴, wealth.fate.get同()), 天干.getByYYWX(阴阳.阳, wealth.fate.get同()),
                    地支.getByYYWX(阴阳.阴, wealth.fate.get生()), 地支.getByYYWX(阴阳.阳, wealth.fate.get生()),
                    地支.getByYYWX(阴阳.阴, wealth.fate.get同()), 地支.getByYYWX(阴阳.阳, wealth.fate.get同())
            );

            if (isWealthGood(bazi)) { // 身弱财旺 - 逢生同
                wealth.decadeLuckList.addAll(decadeHealthGoodList);
                wealth.yearLuckList  .addAll(yearHealthGoodList  );
            } else {    // 身弱财弱 - 生同+偏财
                wealth.decadeLuckList.addAll(find(decadeHealthGoodList, wealth.partialWealthGan, wealth.mainWealthZhi));
                wealth.yearLuckList  .addAll(find(yearHealthGoodList  , wealth.partialWealthGan, wealth.mainWealthZhi));
            }
        } else {    // 身强 - 不论财旺财弱都选财
            wealth.decadeLuckList.addAll(find(pan.getDecadeLuckList(), wealth.partialWealthGan, wealth.mainWealthZhi));
        }
        wealth.wealthElements = find(bazi.getFourColumn(), wealth.wealthWuXing);

        wealth.directions.addAll(Direction.getByWuXing(wealth.wealthWuXing.get生()));
        wealth.directions.addAll(Direction.getByWuXing(wealth.wealthWuXing.get同()));
        wealth.directions.forEach(direction -> wealth.regions.addAll(direction.getCities()));

        return wealth;
    }

    public static boolean isWealthGood(八字 bazi) {
        五行 wealthWuXing = bazi.getFate().get耗();
        return bazi.getMonth().getZhi().getWuXing().equals(wealthWuXing) || bazi.getEnergy().getValue(wealthWuXing) > 150;
    }

    private static List<运势> find(List<? extends 运势> yunList, 五行... wuXingList) {
        List<运势> result = new ArrayList<>();
        for (运势 yun : yunList) {
            boolean match = true;
            for (五行 wuXing : wuXingList) {
                match = match && (yun.getGan().getWuXing().equals(wuXing) || yun.getZhi().getWuXing().equals(wuXing));
            }
            if (match) result.add(yun);
        }
        return result;
    }

    private static List<运势> find(List<? extends 运势> yunList, 元素... elementList) {
        List<运势> result = new ArrayList<>();
        for (运势 yun : yunList) {
            boolean match = false;
            for (元素 e : elementList) {
                match = match || (yun.getGan().equals(e) || yun.getZhi().equals(e));
            }
            if (match) result.add(yun);
        }
        return result;
    }

    private static List<元素类别> find(干支 ganZhi, 天干 gan) {
        List<元素类别> result = new ArrayList<>();
        if (gan.equals(ganZhi.getGan())) {
            result.add(元素类别.天干);
        }
        if (gan.equals(ganZhi.getZhi().getTianGan())) {
            result.add(元素类别.地支);
        }
        if (gan.equals(ganZhi.getZhi().get余气()) || gan.equals(ganZhi.getZhi().get合气())) {
            result.add(元素类别.藏干);
        }
        return result;
    }

    private static List<List<List<元素类别>>> find(List<干支> ganZhiList, 五行 wuXing) {
        List<List<List<元素类别>>> result = new ArrayList<>();
        for (干支 ganZhi : ganZhiList) {
            List<List<元素类别>> r = new ArrayList<>();
            r.add(find(ganZhi, 天干.getByYYWX(阴阳.阴, wuXing)));
            r.add(find(ganZhi, 天干.getByYYWX(阴阳.阳, wuXing)));
            result.add(r);
        }
        return result;
    }
}
