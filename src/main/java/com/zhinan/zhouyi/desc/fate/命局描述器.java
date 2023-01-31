package com.zhinan.zhouyi.desc.fate;

import com.zhinan.zhouyi.desc.BaseDescriptor;
import com.zhinan.zhouyi.fate.bazi.FatePattern;

public class 命局描述器 extends BaseDescriptor<FatePattern> {
    static {
        String[] 名称 = {
                "印枭思辨格",
                "劫比强旺格",
                "伤食灵动格",
                "财才实践格",
                "官杀自律格",
        };

        String[] 优点 = {
                "思考多、计划强、 逻辑强、愿景大。",
                "能担当、精力旺，爱付出讲义气，爱交友。",
                "灵气才华、创造力、情绪敏感、共情力高。",
                "务实、结果导向、目标强、经营敏感度强。",
                "严道、规则感强、风险意识强、自律周全。",
        };

        String[] 缺点 = {
                "要面子、思虑过度、行动弱、苛刻。",
                "自我、固执、易冲动。",
                "感性、易情绪化、思维发散、自律性差。",
                "想的少、过度重视性价比、目的性强现实。",
                "顾虑多、疑心重、风险过度担忧，压力大。",
        };

        String[] 交往 = {
                "重视梦想和影响力、注意名在利先。",
                "提出需求、价值确认、正向反馈。",
                "重视主观感受、给予空间感。",
                "强调实用、功利性、性价比。",
                "给与安全感、权威感、放心感。",
        };

        register(FatePattern.class, "名称", 名称);
        register(FatePattern.class, "优点", 优点);
        register(FatePattern.class, "缺点", 缺点);
        register(FatePattern.class, "交往", 交往);
    }

    public String describe(Object o) {
        StringBuilder sb = new StringBuilder();
        String name = getName(o);
        sb.append(getName(o))
                .append("的人的优点有：").append(describe(o, "优点")).append(lineSeparator)
                .append("需要注意的地方有：").append(describe(o, "缺点")).append(lineSeparator)
                .append("和").append(name).append("的人的相处方法是：")
                .append(describe(o, "交往")).append(lineSeparator);
        return sb.toString();
    }
}
