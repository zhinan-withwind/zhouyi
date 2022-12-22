package com.zhinan.zhouyi.desc.fate;

import com.alibaba.fastjson.JSONObject;
import com.zhinan.zhouyi.base.五行;
import com.zhinan.zhouyi.common.Descriptor;
import com.zhinan.zhouyi.common.Source;
import com.zhinan.zhouyi.common.Weather;
import com.zhinan.zhouyi.desc.基础描述器;
import com.zhinan.zhouyi.fate.util.健康;

@Descriptor(source = Source.明易, isDefault = true)
public class 健康描述器 extends 基础描述器<健康> {
    final static String[] 指代 = {
            "肝脏和胆囊",
            "心脏和小肠",
            "脾脏和胃部",
            "肺部和大肠",
            "肾脏和膀胱",
    };

    final static String[] 季节 = {
            "春季，大约每年阴历正月和二月",
            "夏季，大约每年阴历四月和五月",
            "四季，大约每年阴历三月和六月、九月和腊月",
            "秋季，大约每年阴历七月和八月",
            "冬季，大约每年阴历十月和冬月",
    };

    final static String[] 味道 = {
            "酸味", "苦味", "甜味", "辣味", "咸味",
    };

    final static String[] 颜色 = {
            "绿色", "红色", "黄色", "白色", "黑色",
    };

    final static String[] 过旺 = {
            "眼睛干涩，影响视力，指甲易会有竖纹，同时情绪比较急躁，影响睡眠。并且以腺体节结性问题。",
            "脸易发红，严重者易有红血丝，舌苔发红，易发生胸闷，血稠血热，手心褪皮，睡眠质量差等症状。",
            "胃燥脾湿，胃部不时食欲不振，消化不良、过胖或过瘦牙龈出血等症状，严重者会影响抵抗力和造血功能失衡紊乱。",
            "便秘、毛孔粗大、呼吸不畅，手心脚心易出汗，脾气急躁，烦躁等问题。",
            "生殖器官受影响，易引起水肿、尿频，尿酸值不稳，眼袋，湿寒风湿性疾病等问题。",
    };

    final static String[] 过弱 = {
            "恶心，消化不良，指甲有竖纹，睡眠质量差，月经不调，脂肪囤积，视力下降等问题。",
            "消瘦、乏力心慌，头晕，头痛，记忆力下降，睡眠质量差，面色发白，供血不足等问题。",
            "脾虚忧思，胃酸积食，肌肉乏力松弛，食欲不振，贫血，月经量少，过胖过瘦等问题。",
            "会使皮毛容易过敏，毛孔粗大皮肤干燥，同时会影响便秘或者拉稀，呼吸不畅、头晕、头痛、记忆力下降等问题。",
            "耳鸣耳痒，头发干枯，骨质疏松，肩颈酸痛，腰酸背痛，月经不调，眼袋、黑眼圈，睡眠质量差脱发、尿频等等问题。",
    };

    final static String[] 被克 = {
            "金克木，金过旺则肺火过旺，会影响肝胆功能运化受阻，则会产生记忆力下降、视力下降睡眠质量差，月经不调，情绪不稳定，易抑郁等问题。",
            "水克火，水过旺则会出现消瘦、乏力，心慌、胸闷、头晕、记忆力下降、睡眠差的问题。",
            "木克土，木过旺会克制脾胃的消化吸收，同时会引发肌肉无力，运化薄弱，食欲不振，贫血等问题，女性则易月经量少。",
            "火克金，火过旺会影响肺与大肠的运化和排泄。肺主皮毛最明显，心火过旺会使皮毛容易过敏，毛孔粗大皮肤干燥，同时会引发便秘，呼吸不畅、头晕、头痛、记忆力下降等问题。",
            "土克水，土过旺即脾胃过燥运化失调，会影响肾和膀胱的代谢功能，会产生耳鸣耳痒，头发干枯，骨质疏松，肩颈酸痛，腰酸背痛，月经不调，眼袋、黑眼圈，睡眠质量差脱发、尿频等等问题。",
    };

    public String describeUpAndDown(五行 wuXing) {
        return "您生在" + wuXing + "月，" + wuXing + "属于"
                + Weather.getByWuXing(wuXing) + "季，" + "其五行旺衰分别为："
                + wuXing.get泄() + "旺，"
                + wuXing.get同() + "相，"
                + wuXing.get生() + "休，"
                + wuXing.get克() + "囚，"
                + wuXing.get耗() + "死。";
    }

    public String describeTooMuch(五行 wuXing) {
        return "您的八字中，最多的五行元素是" + wuXing + "，" + wuXing + "代表" + 指代[wuXing.getValue()] + "，"
                + wuXing + "过旺会导致" + 指代[wuXing.getValue()] + "方面的问题，例如："
                + 过旺[wuXing.getValue()] + lineSeparator
                + 被克[wuXing.get耗().getValue()] + lineSeparator
                + "在" + wuXing + "气旺的季节（每年的" + 季节[wuXing.getValue()] + "）会加重这方面的影响。";
    }

    public String describeTooLess(五行 wuXing) {
        return "您的八字中，最少的五行元素是" + wuXing + "，" + wuXing + "代表" + 指代[wuXing.getValue()] + "，"
                + wuXing + "过弱会导致" + 指代[wuXing.getValue()] + "方面的问题，例如："
                + 过弱[wuXing.getValue()] + lineSeparator
                + wuXing + "生" + wuXing.get泄() + "，" + wuXing + "过弱，导致生" + wuXing.get泄() + "能力不足，会出现："
                + 过弱[wuXing.get泄().getValue()] + lineSeparator
                + "在" + wuXing.get克() + "气旺的季节（每年的" + 季节[wuXing.get克().getValue()] + "）会加重这方面的影响。";
    }

    public String describeSuggestion(五行 max, 五行 min) {
        return "建议多吃" + 味道[max.getValue()] + (min.get对宫().equals(max) ? "" : "和" + 味道[min.get对宫().getValue()]) + "的食物，"
                + (max.get对宫().equals(max) && max.get对宫().equals(min) ? "" : "少吃" + (max.get对宫().equals(max) ? "" : 味道[max.get对宫().getValue()])
                + (max.get对宫().equals(min) ? "" : (max.get对宫().equals(max) ? "" : "和") + 味道[min.getValue()]) + "的食物，")
                + "并且多吃" + 颜色[min.get生().getValue()] + (max.get泄().equals(min.get生()) ? "" : "和" + 颜色[max.get泄().getValue()]) +  "的食物。";
    }

    @Override
    public JSONObject 描述(Object o) {
        健康 health = (健康) o;
        return new JSONObject()
                .fluentPut("旺衰", describeUpAndDown(health.getMonth()) + lineSeparator)
                .fluentPut("最强", describeTooMuch(health.getMax()) + lineSeparator)
                .fluentPut("最弱", describeTooLess(health.getMin()) + lineSeparator)
                .fluentPut("建议", describeSuggestion(health.getMax(), health.getMin()) + lineSeparator);
    }
}
