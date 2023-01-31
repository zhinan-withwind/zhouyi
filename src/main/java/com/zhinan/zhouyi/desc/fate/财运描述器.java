package com.zhinan.zhouyi.desc.fate;

import com.alibaba.fastjson.JSONObject;
import com.zhinan.zhouyi.common.ColorSeries;
import com.zhinan.zhouyi.desc.BaseDescriptor;
import com.zhinan.zhouyi.effect.作用元素;
import com.zhinan.zhouyi.effect.元素类别;
import com.zhinan.zhouyi.fate.luck.运势;
import com.zhinan.zhouyi.fate.util.财运;
import com.zhinan.zhouyi.fate.util.风水物;

import java.util.List;

/*  1. 财富所属
    2. 大财机会
    3. 发财指南
    4. 地域推荐
 */
public class 财运描述器 extends BaseDescriptor<财运> {
    private final String lineSeparator = System.lineSeparator();

    public JSONObject fullDescribe(Object o) {
        财运 wealth = (财运) o;
        return new JSONObject()
                .fluentPut("财运五行", wealth.getWealthWuXing())
                .fluentPut("担财能力", describeAffordability(wealth))
                .fluentPut("招财穿衣", describeCloth(wealth))
                .fluentPut("旺财风水", describeLuckObject(wealth))
                .fluentPut("财富所属", describeWealth(wealth).split(lineSeparator))
                .fluentPut("大财机会", describeLuck  (wealth).split(lineSeparator))
                .fluentPut("增财指南", describeGuide (wealth).split(lineSeparator))
                .fluentPut("地域推荐", describeRegion(wealth).split(lineSeparator));
    }

    //1. 财富所属
    //您是癸水命，您的财的五行是火，丙火和巳火是您的正财，丁火和午火是您的偏财。
    //正财是指工作上挣到的钱财，是比较规律性的收入。
    //偏财是指投资理财挣的钱财，是比较不规律的收入，通常偏财有可能是大财。
    //您在年柱天干有一个正财，说明您祖上家资殷实，有一定的家底。
    //您在月柱藏干有一个正财，说明您年轻时候有潜在的贵人，助您财运。
    //您在日柱地支有一个正财，说明您自身的工作不错，收入颇丰。
    //您在时柱地支有一个偏财，说明您晚年财运不错，有投资收入。
    public String describeWealth(财运 wealth) {
        StringBuilder result = new StringBuilder("您是" + wealth.getFate().getName() + "命，"
                + "您的财的五行是" + wealth.getWealthWuXing() + "，"
                + wealth.getMainWealthGan().getFullName() + "和" + wealth.getMainWealthZhi().getFullName() + "是您的正财，"
                + wealth.getPartialWealthGan().getFullName() + "和" + wealth.getPartialWealthZhi().getFullName()
                + "是您的偏财。" + lineSeparator
                + "正财是指工作上挣到的钱财，是比较规律性的收入，通常正财是意料之内的。" + lineSeparator
                + "偏财是指投资理财挣的钱财，是比较不规律的收入，通常偏财有可能是大财。" + lineSeparator);
        List<List<List<元素类别>>> elements = wealth.getWealthElements();
        for (int i = 0; i < elements.size(); i++) {
            List<List<元素类别>> w = elements.get(i);
            for (元素类别 e : w.get(wealth.getMainWealthGan().getYinYang().getValue())) {
                result.append("您有一个正财在").append(作用元素.位置.getByValue(i)).append(e.getName()).append("，")
                      .append(describeWealthElement(作用元素.位置.getByValue(i), 0)).append(lineSeparator);
            }
            for (元素类别 e : w.get(wealth.getPartialWealthGan().getYinYang().getValue())) {
                result.append("您有一个偏财在").append(作用元素.位置.getByValue(i)).append(e.getName()).append("，")
                        .append(describeWealthElement(作用元素.位置.getByValue(i), 1)).append(lineSeparator);
            }
        }
        return result.toString();
    }

    public String describeLuck(财运 wealth) {
        StringBuilder result = new StringBuilder();
        if (wealth.getDecadeLuckList().size() > 0) {
            result.append("在您的大运中：").append(lineSeparator);
            for (运势 yun : wealth.getDecadeLuckList()) {
                result.append(yun.getName()).append("(").append(yun.getStartTime().getYear())
                        .append(" - ").append(yun.getEndTime().getYear()).append(")，").append(lineSeparator);
            }
            result.append("是您财运比较好的大运。").append(lineSeparator);
        }
        if (wealth.getYearLuckList().size() > 0) {
            result.append("在您当前大运的十年流年中：").append(lineSeparator);
            for (运势 yun : wealth.getYearLuckList()) {
                result.append(yun.getName()).append("(").append(yun.getStartTime().getYear()).append(")，")
                        .append(lineSeparator);
            }
            result.append("是您财运比较好的年份。").append(lineSeparator);
        }
        if (wealth.isHasBank()) {
            result.append("您的命局中有财库，在这些大运中：").append(lineSeparator);
            wealth.getBankOpenList().forEach(ganZhi -> result.append(ganZhi.getName())
                    .append("(").append(ganZhi.getStartTime().getYear())
                    .append(" - ").append(ganZhi.getEndTime().getYear()).append(")，").append(lineSeparator));
            result.append("您的财库会被打开，会有发大财的机会！").append(lineSeparator);
        }
        return result.toString();
    }

    public String describeAffordability(财运 wealth) {
        boolean isWeak = !wealth.getPattern().isStrong();
        StringBuilder result = new StringBuilder("您的命局是" + wealth.getPattern().getName() + "，是个" + (wealth.getPattern().isStrong() ? "身强" : "身弱") + "的命局。" + lineSeparator);
        result.append("命强与命弱决定了您的担财能力。假如您的生命里有一个宝库，所谓担财能力，是指您能从这个宝库中搬运出财的能力。" + "所以您的担财能力相对").append(isWeak ? "较弱" : "较强").append("。");
        if (isWeak) {
            result.append("加强您的命主五行，可以帮助您提升担财能力，从而获取更多的财富。").append(lineSeparator);
        } else {
            result.append("加强您的财的五行，可以帮助您催旺您的财运，从而获得更多的财富。").append(lineSeparator);
        }
        return result.toString();
    }

    public String describeCloth(财运 wealth) {
        return !wealth.getPattern().isStrong() ?
                "穿着" + ColorSeries.getByWuXing(wealth.getFate().get生()) + "颜色的衣服和"
                        + ColorSeries.getByWuXing(wealth.getFate().get同())
                        + "颜色的衣服可以补充您的命主力量，加强您的担财能力，从而获得更多财富。" + lineSeparator :
                "穿着" + ColorSeries.getByWuXing(wealth.getWealthWuXing().get生()) + "颜色的衣服和"
                        + ColorSeries.getByWuXing(wealth.getWealthWuXing().get同())
                        + "颜色的衣服可以增加您的财运力量，加强您的财富数量，从而获得更多财富。" + lineSeparator;
    }

    public String describeLuckObject(财运 wealth) {
        StringBuilder result = new StringBuilder("可以摆放一个");
        if (!wealth.getPattern().isStrong()) {
            风水物.getByWuXing(wealth.getFate().getGan().getWuXing()).forEach(o -> result.append(o.getName()).append("，"));
        } else {
            风水物.getByWuXing(wealth.getWealthWuXing()).forEach(o -> result.append(o.getName()).append("，"));
        }
        result.append("来催旺你的财富运势。");
        return result.toString();
    }

    public String describeGuide(财运 wealth) {
        return describeAffordability(wealth)
                + describeCloth(wealth)
                + describeLuckObject(wealth);
    }

    public String describeRegion(财运 wealth) {
        StringBuilder result = new StringBuilder("您是" + wealth.getFate().getName() + "命，"
                + "您的财的五行是" + wealth.getWealthWuXing() + "。").append(lineSeparator);
        result.append("所以去往：");
        wealth.getDirections().forEach(direction -> result.append(direction.getName()).append("方，"));
        result.append("会催旺您的财运！").append(lineSeparator);

        result.append("可以考虑的城市有：");
        wealth.getRegions().forEach(region -> result.append(region.getName()).append("，"));
        result.append("等等。");

        return result.toString();
    }

    public String describeWealthElement(作用元素.位置 p, int type) {
        return 财星说明[p.getValue() * 2 + type];
    }

    private final static String[] 财星说明 = {
            "说明您少年时，祖上家底丰厚，家资殷实。",
            "说明您少年时，祖上遗产丰厚，家境富贵。",
            "说明您青年时，朋友中遇贵人，助您财运。",
            "说明您青年时，和朋友做生意，回报不菲。",
            "说明您中年时，自身工作不错，收入颇丰。",
            "说明您中年是，自己经营投资，坐收渔利。",
            "说明您晚年时，孩子很有出息，挣钱孝敬。",
            "说明您晚年时，孩子能赚大钱，衣食无忧。",
    };
}
