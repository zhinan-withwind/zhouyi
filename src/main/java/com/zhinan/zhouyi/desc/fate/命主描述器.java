package com.zhinan.zhouyi.desc.fate;

import com.alibaba.fastjson.JSONObject;
import com.zhinan.zhouyi.common.Source;
import com.zhinan.zhouyi.desc.基础描述器;
import com.zhinan.zhouyi.fate.bazi.命主;

import java.util.Map;

public class 命主描述器 extends 基础描述器 {
    public final static String[] 描述 = {
            "大树之木，仁慈，有领导力，掌控欲强，不喜欢被人管",
            "花草之木，仁爱，八面玲珑",
            "太阳之火，热情，外向，开朗，喜欢帮助别人，重礼节，讲礼貌，有仪式感",
            "炉中之火，热情，坚持，有顽强的生命力，有韧性，不达目的不罢休，有仪式感",
            "城墙之土，讲诚信，守信用",
            "田地之土，讲诚信，擅长整合团队，适合做二把手",
            "大块的金，讲义气，侠义气概，身材健硕",
            "首饰之金，灵性好，第六感强，讲义气",
            "大海之水，聪明，智慧",
            "雨露之水，聪明，第六感强，通神"
    };

    public final static String[] 意向 =  {
            "高乔木", "藤蔓木", "太阳火", "灯烛火", "城墙土",
            "庄稼土", "剑锋金", "钗钏金", "江河水", "露泉水",
    };

    public final static String[] 五常 =  {
            "仁，性直、情和。有慈悲恻隐之心，性子虽直但最终不会吵架，底层平和",
            "仁，性直、情和。有慈悲恻隐之心，性子虽直但最终不会吵架，底层平和",
            "礼，性急情恭、有礼貌、火热、热情乐观、急躁、自尊心强",
            "礼，性急情恭、有礼貌、火热、热情乐观、急躁、自尊心强",
            "信，性重，情原，真诚踏实，为人教原守信，靠谱，言出必行",
            "信，性重，情原，真诚踏实，为人教原守信，靠谱，言出必行",
            "义，性刚，情烈。刚直不阿，不卑不亢 ，干脆刚毅，有义气有担当",
            "义，性刚，情烈。刚直不阿，不卑不亢 ，干脆刚毅，有义气有担当",
            "智，性聪，情善，灵活变通，聪明，有谋略，信息敏感度强",
            "智，性聪，情善，灵活变通，聪明，有谋略，信息敏感度强",
    };

    public final static String[] 优势 =  {
            "性格仁慈、宽原随和、大方仁义、威武坚强",
            "外柔内刚，温顺坚忍，情感丰富，多情多义",
            "精力充沛，迅速威猛，开朗热情，大方耿直",
            "内在热情，奉献精神，内敛文雅，恩虑缜密",
            "胸怀宽大，稳重诚实，严蓮踏实，落地能力强",
            "性情温原，原道坦诚，包容含蓄，适应力强",
            "坚强豪爽，讲义气，刚毅果决，公正侠义",
            "柔软细腻，亲切细致 ，助人为乐，有表现力",
            "足智多淇，有勇有谋，处理灵活，有冲劲",
            "稳定宁静，有耐心，恩想细威，润物无声",
    };

    public final static String[] 提醒 =  {
            "容易自负，不合群，过于直率",
            "人性固执，钻牛角尖，不够自信，依赖性强",
            "暴躁性极，威猛冲动，性情不稳定",
            "易有疑心，耍小脾气，有小心机",
            "恩维传统，自答心强、蛋名誉",
            "易茫然 ，易妥协，主见不足",
            "好胜心强，粗心，易情绪化",
            "自導心强，好面子，好虚菜，易华而不实",
            "易狡诈，不够踏实，旯咸性",
            "多幻想，感情易脆弱，魄力不足，易悲观",
    };

    public final static String[] 相处 =  {
            "示弱", "示弱", "表扬", "表扬", "诚信", "诚信", "负责", "负责", "聪明", "聪明",
    };


   static  {
       register(Source.明易.getName(), 命主描述器.class.getSimpleName(), "描述", 描述);

       register(Source.星鹤.getName(), 命主描述器.class.getSimpleName(), "意向", 意向);
       register(Source.星鹤.getName(), 命主描述器.class.getSimpleName(), "五常", 五常);
       register(Source.星鹤.getName(), 命主描述器.class.getSimpleName(), "优势", 优势);
       register(Source.星鹤.getName(), 命主描述器.class.getSimpleName(), "提醒", 提醒);
       register(Source.星鹤.getName(), 命主描述器.class.getSimpleName(), "相处", 相处);
    }


    public static String describe(命主 zhu) {
        JSONObject descriptions = 描述(zhu);
        StringBuilder result = new StringBuilder();
        for (String key : descriptions.keySet()) {
            result.append(key).append(": ").append(descriptions.get(key)).append(System.lineSeparator());
        }
        return result.toString();
    }

    public static JSONObject 描述(命主 zhu) {
        JSONObject result = new JSONObject();
        result.put("名称", zhu.getName());
        Map<String, String[]> descriptions = getDescriptions(命主描述器.class.getSimpleName());
        for (String type : descriptions.keySet()) {
            result.put(type, descriptions.get(type)[zhu.getValue()]);
        }
        return result;
    }
}
