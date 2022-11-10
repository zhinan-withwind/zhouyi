package com.zhinan.zhouyi.desc.fate;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.zhinan.zhouyi.base.五行;
import com.zhinan.zhouyi.base.元素;
import com.zhinan.zhouyi.effect.作用类别;
import com.zhinan.zhouyi.effect.合化冲;
import com.zhinan.zhouyi.energy.能量;
import com.zhinan.zhouyi.fate.bazi.八字;
import com.zhinan.zhouyi.fate.bazi.格局;
import com.zhinan.zhouyi.fate.bazi.纳音;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * 合婚
 *
 * 1. 男命纳音克或者同女命纳音
 * 2. 女命纳音生或者同男命纳音
 * 3. 男命属相与女命属相相合则吉，相克，相冲，相刑，相害，相破凶
 * 4. 男命月令与女命月令相合则吉，相克，相冲，相刑，相害，相破凶
 * 5. 男命日支与女命日支相合则吉，相克，相冲，相刑，相害，相破凶
 * 6. 男命日干与女命日干相生，相合吉
 * 7. 一方的主要能量是另一方的药，或者相互为药
 * 8. 以毒攻毒（如果两方的婚姻都不好，反而比较相配）
 */
public class 合婚描述器 {

    public static String describe(八字 mBazi, 八字 wBazi) {
        JSONObject result = 描述(mBazi, wBazi);
        return describe("总体状况", result)
                + describe("纳音关系", result.getJSONObject("纳音关系"))
                + describe("属相关系", result.getJSONObject("属相关系"))
                + describe("月令关系", result.getJSONObject("月令关系"))
                + describe("日支关系", result.getJSONObject("日支关系"))
                + describe("日主关系", result.getJSONObject("日主关系"))
                + describe("男命能量", result.getJSONObject("男命能量"))
                + describe("女命能量", result.getJSONObject("女命能量"))
                + describe("婚运关系", result.getJSONObject("婚运关系"))
                ;
    }

    private static String describe(String key, JSONObject description) {
        return  "从" + key + "上分析，" +
                "男命是：" + description.get("男命") + "，" +
                "女命是：" + description.get("女命") + "，" +
                "两者之间的关系是：" + description.get("关系") + "，" +
                "你们的配对得分是：" + description.get("分数") + "分，" +
                "这部分的匹配度是：" + description.get("吉凶") + "，" +
                System.lineSeparator() + System.lineSeparator();
    }

    public static JSONObject 描述(八字 mBazi, 八字 wBazi) {
        JSONObject result = new JSONObject();
        int total = 0;
        result.put("男命", mBazi);
        result.put("女命", wBazi);

        // 1. 男女命纳音八字的关系
        纳音 mSound = mBazi.getYear().getSound();
        纳音 wSound = wBazi.getYear().getSound();
        String relation = "";
        String good     = "";
        int    score    = 0;
        switch (mSound.getWuXing().compare(wSound.getWuXing())) {
            case 耗:
                relation = "男命克女命";
                good     = "吉";
                score    = 100;
                break;
            case 同:
                relation = "男命同女命";
                good     = "吉";
                score    = 80;
                break;
            case 生:
                relation = "女命生男命";
                good     = "吉";
                score    = 60;
                break;
            case 泄:
                relation = "男命生女命";
                good     = "不吉";
                score    = 40;
                break;
            case 克:
                relation = "女命克男命";
                good     = "凶";
                score    = 20;
                break;
        }
        result.put("纳音关系", new JSONObject()
                .fluentPut("男命", mSound)
                .fluentPut("女命", wSound)
                .fluentPut("关系", relation)
                .fluentPut("吉凶", good)
                .fluentPut("分数", score)
        );
        total += result.getJSONObject("纳音关系").getInteger("分数") * 1.0;

        // 3. 男命属相与女命属相相合则吉，相克，相冲，相刑，相害，相破凶
        result.put("属相关系",  getElementScore(mBazi.getYear().getZhi(), wBazi.getYear().getZhi()));
        total += result.getJSONObject("属相关系").getInteger("分数") * 1.0;

        // 4. 男命月令与女命月令相合则吉，相克，相冲，相刑，相害，相破凶
        result.put("月令关系", getElementScore(mBazi.getMonth().getZhi(), wBazi.getMonth().getZhi()));
        total += result.getJSONObject("月令关系").getInteger("分数") * 0.8;

        // 5. 男命日支与女命日支相合则吉，相克，相冲，相刑，相害，相破凶
        result.put("日支关系", getElementScore(mBazi.getDay().getZhi(), wBazi.getDay().getZhi()));
        total += result.getJSONObject("日支关系").getInteger("分数") * 0.7;

        // 6. 男命日干与女命日干相合则吉，相克，相冲，相刑，相害，相破凶
        result.put("日主关系", getElementScore(mBazi.getDay().getGan(), wBazi.getDay().getGan()));
        total += result.getJSONObject("日主关系").getInteger("分数") * 0.5;

        // 7. 一方的主要能量是另一方的药，或者相互为药
        result.put("男命能量", getEnergyScore(mBazi.getEnergy(), 1, wBazi.getGridPattern()));
        total += result.getJSONObject("男命能量").getInteger("分数") * 0.5;

        // 7. 一方的主要能量是另一方的药，或者相互为药
        result.put("女命能量", getEnergyScore(wBazi.getEnergy(), 0, mBazi.getGridPattern()));
        total += result.getJSONObject("女命能量").getInteger("分数") * 0.5;

        // 8. 以毒攻毒（如果两方的婚姻都不好，反而比较相配）
        boolean mBad = 合化冲.is刑冲破害(Arrays.asList(mBazi.getMonth().getZhi(), mBazi.getDay().getZhi()));
        boolean wBad = 合化冲.is刑冲破害(Arrays.asList(wBazi.getMonth().getZhi(), wBazi.getDay().getZhi()));
        if (mBad && wBad) {
            relation = "男命与女命婚姻运势相配";
            score = 100;
            good  = "吉";
        } else if (!mBad && !wBad) {
            relation = "男命与女命婚姻运势无关";
            score = 50;
            good  = "中平";
        } else {
            relation = "男命与女命婚姻运势相反";
            score = 0;
            good  = "凶";
        }
        作用类别 mRelation = 合化冲.getRelation(mBazi.getMonth().getZhi(), mBazi.getDay().getZhi());
        作用类别 wRelation = 合化冲.getRelation(wBazi.getMonth().getZhi(), wBazi.getDay().getZhi());
        result.put("婚运关系", new JSONObject()
                .fluentPut("男命", "婚姻宫与配偶宫" + mRelation.getName())
                .fluentPut("女命", "婚姻宫与配偶宫" + wRelation.getName())
                .fluentPut("关系", relation)
                .fluentPut("吉凶", good)
                .fluentPut("分数", score)
        );
        total += result.getJSONObject("婚运关系").getInteger("分数") * 1.0;

        result.put("分数", total);
        result.put("吉凶", total > 360 ? "吉" : total <= 240 ? "凶" : "中平");
        if (total > 480) {
            relation = "天作之合，五百次回眸遇见你";
        } else if (total > 360) {
            relation = "红尘作伴，和和睦睦相守到底";
        } else if (total > 240) {
            relation = "欢喜冤家，分分合合不离不弃";
        } else if (total > 120) {
            relation = "恋人未满，一辈子都做好朋友";
        } else {
            relation = "今生无缘，等来生还要在一起";
        }
        result.put("关系", relation);

        return result;
    }

    public static JSONObject getElementScore(元素 mZhi, 元素 wZhi) {
        JSONObject result = new JSONObject();
        String relation;
        String good;
        int score;
        List<元素> elements = new ArrayList<>(Arrays.asList(mZhi, wZhi));
        if (合化冲.is合(elements)) {
            relation = "男命与女命相合";
            good = "吉";
            score = 100;
        } else if (合化冲.is冲(elements)) {
            relation = "男命与女命相冲";
            good = "凶";
            score = 40;
        } else if (合化冲.is刑(elements)) {
            relation = "男命与女命相刑";
            good = "凶";
            score = 30;
        } else if (合化冲.is破(elements)) {
            relation = "男命与女命相破";
            good = "凶";
            score = 20;
        } else if (合化冲.is害(elements)) {
            relation = "男命与女命相害";
            good = "凶";
            score = 10;
        } else {
            relation = "男命与女命无作用";
            good = "中平";
            score = 50;
        }
        result.put("男命", mZhi.toString());
        result.put("女命", wZhi.toString());
        result.put("关系", relation);
        result.put("吉凶", good);
        result.put("分数", score);

        return result;
    }

    public static JSONObject getEnergyScore(能量 energy, int sex, 格局 gridPattern) {
        JSONObject result = new JSONObject();
        五行 wx = null;
        for (五行 wuXing : 五行.values()) {
            if (energy.get(wuXing) > energy.getTotal() / 2 && gridPattern.isGood(wuXing)) {
                wx = wuXing;
                break;
            }
        }
        result.put("男命", sex == 1 ? wx == null ? energy.getMax() : wx : gridPattern.getName());
        result.put("女命", sex == 0 ? wx == null ? energy.getMax() : wx : gridPattern.getName());
        result.put("关系", sex == 1 ?
                "男命的能量对女命" + (wx == null ? "无" : "起") + "辅助作用" :
                "女命的能量对男命" + (wx == null ? "无" : "起") + "辅助作用");
        result.put("吉凶", wx == null ? "凶" : "吉");
        result.put("分数", wx == null ? 0 : 100);
        return result;
    }
}
