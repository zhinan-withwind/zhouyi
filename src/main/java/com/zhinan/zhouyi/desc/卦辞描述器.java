package com.zhinan.zhouyi.desc;

import com.zhinan.zhouyi.base.十神;
import com.zhinan.zhouyi.divine.六十四卦;

import java.util.HashMap;
import java.util.Map;

public class 卦辞描述器 {
    public enum 描述类型 {
        基本含义, 卦辞, 象传, 彖传
    }

    public final static String[] 基本含义 = {
            "天，君主，健，刚健，引领",
            "地，臣民，顺，柔和，包容",
            "屯积，积累，萌芽，初创艰难",
            "启蒙，启发，教育",
            "需求，等待，期许，饮食，供养",
            "争斗，争讼，官非",
            "军队，众人",
            "相近，亲附",

            "小的积累，小的成就",
            "履行，践行，礼貌、礼节",
            "亨通，安泰，通达",
            "不通，阻隔，闭塞",
            "同行，同我一起，集结一起",
            "收获很大，收益较多",
            "谦虚，谦逊",
            "喜悦，安乐",

            "跟随，跟从",
            "蛊惑，腐败，混乱",
            "靠近，临近",
            "观看，展示，展览",
            "咬住，咬合，刑罚，刑狱",
            "装饰，修饰，文饰",
            "下落，剥落，衰落",
            "恢复，归复，回归",

            "不要妄想，不要妄念",
            "大的积累，大的积蓄",
            "颐养，休养，食物，安乐",
            "大的过分，过度，过错，不一般的行动",
            "陷落，坑坎，危险",
            "明丽，美丽，依附",
            "咸即感，感应，感情，夫妇之道",
            "恒久，恒常，长久",

            "遁走，潜藏，退走，消退",
            "极为强壮，力量强大",
            "晋为进，前进，晋升",
            "受伤，韬光养晦",
            "家庭伦理，家庭关系",
            "睽违，离开，争吵",
            "跛脚，困难，艰难险阻",
            "解脱，解开，化解",

            "损失，减少，受损",
            "受益，增多",
            "决断，决策，奇怪",
            "邂逅，相遇，遇见",
            "汇聚，聚集，提炼",
            "上升，发达，升起",
            "穷困，困境，困难",
            "水井，贤德，井田制",

            "变革，改变",
            "饮食，蓄养贤人，烹制，制衡",
            "震动，威惧，震慑",
            "停止，终止",
            "渐进，逐步",
            "婚嫁，出嫁",
            "丰盛，盛大，丰富",
            "旅行，不安定，路途",

            "进入，风吹，谦逊",
            "喜悦，取悦，口舌",
            "涣散，离散，分散",
            "节制，控制，把控，节约",
            "诚信，朴实，踏实",
            "小的过错或过度，稍有越过法度",
            "已经完成",
            "尚未完成，还未成功",
    };

    public final static Map<卦辞描述器.描述类型, String[]> descriptions = new HashMap<卦辞描述器.描述类型, String[]>() {
        {
            put(卦辞描述器.描述类型.基本含义, 基本含义);

        }
    };


    public static String describe(六十四卦 gua) {
        StringBuilder description = new StringBuilder();
        for (卦辞描述器.描述类型 type : 卦辞描述器.描述类型.values()) {
            description.append(type.name()).append(": ").append(describe(gua, type)).append(System.lineSeparator());
        }
        return description.toString();
    }

    public static String describe(六十四卦 gua, 卦辞描述器.描述类型 type) {
        return descriptions.get(type)[gua.getValue()];
    }
}
