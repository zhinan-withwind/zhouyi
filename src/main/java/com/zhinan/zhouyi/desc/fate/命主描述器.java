package com.zhinan.zhouyi.desc.fate;

import com.zhinan.zhouyi.desc.BaseDescriptor;
import com.zhinan.zhouyi.fate.bazi.命主;
import org.springframework.stereotype.Component;

@Component
public class 命主描述器 extends BaseDescriptor<命主> {
    static  {
        final String[] 指代 =  {
                "高乔木", "藤蔓木", "太阳火", "灯烛火", "城墙土",
                "庄稼土", "剑锋金", "钗钏金", "江河水", "露泉水",
        };

        final String[] 五常 =  {
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

        final String[] 优势 =  {
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

        final String[] 提醒 =  {
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

        final String[] 相处 =  {
                "示弱", "示弱", "表扬", "表扬", "诚信", "诚信", "负责", "负责", "聪明", "聪明",
        };

        register(命主.class, "指代", 指代);
        register(命主.class, "五常", 五常);
        register(命主.class, "优势", 优势);
        register(命主.class, "提醒", 提醒);
        register(命主.class, "相处", 相处);
    }

    @Override
    public String describe(Object o) {
        命主 zhu = (命主) o;
        return "您的日主是"  + getName(zhu) + "，" + getName(zhu) + "是"
                + describe(zhu, "指代") + "，主"
                + describe(zhu, "五常") + "。"
                + lineSeparator
                + getName(zhu) + "日主的人会比较" + describe(zhu, "优势") + "。"
                + lineSeparator
                + "同时也需要注意"+ describe(zhu, "提醒") + "的问题。"
                + lineSeparator
                + "和" + getName(zhu) + "日主的人相处的方法是：展现你的" + describe(zhu, "相处") + "。"
                + lineSeparator;
    }
}
