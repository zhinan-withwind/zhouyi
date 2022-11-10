package com.zhinan.zhouyi.desc.fate;

import com.alibaba.fastjson.JSONObject;
import com.zhinan.zhouyi.common.Source;
import com.zhinan.zhouyi.desc.基础描述器;
import com.zhinan.zhouyi.fate.bazi.命局;

import java.util.Map;

public class 命局描述器 extends 基础描述器 {
    public enum 描述类型 {
        名称, 优点, 缺点, 交往
    }

    public final static String[] 明易_名称 = new String[] {
            "爱思考的印重局", "自信的命旺局", "聪明的伤官局", "有目标的财旺局", "谨慎的煞重局"
    };

    public final static String[] 明易_优点 = new String[] {
            "善于思考，想法多，标准多，标准高，做事方法多，清高，爱讲道理，有任性，有执行力，在乎结果，相信权威，但是更相信自己，精力旺盛，爱学习，爱看书。",
            "很自信，内在非常有力量，有强大的拯救情结，好胜，做事喜欢体验过程，不太在乎结果，一心多用，热情，胆大，有领导气质，能操心，不服输，喜欢占山为王，热爱体育运动，能折腾，竞争心强，有使命感。",
            "才气，秀气，灵气，傲气。有才艺，情商高，口才好，悟性高，善解人意，开拓精神，潇洒，有梦想，有长远规划，善于表达，有行动力。",
            "有目标，眼里到处都是商机，对数字有感觉，目标感强，点子多，适合做销售，有行动力，欲望多，旺财的女人大都是女强人，爱操心，旺财的男人异性缘都很好，疼老婆。",
            "非常谨慎，一生热爱学习，有一技之长，有专业技能。",
    };

    public final static String[] 明易_缺点 = new String[] {
            "有点固执，有时候一根筋，财迷，爱生小闷气，有洁癖，有时候小心眼。特别有标准，爱用自己的标准框别人，财迷，有洁癖（生活上及精神上），想完了就当干完了。放不下过去。",
            "自以为是，自负，有赌性，好斗，固执，精力旺盛，面子上有好多朋友，骨子里其实并不太喜欢朋友，喜欢独处，学习精神不集中，易走神，被认可的时候忠诚度最高。",
            "喜欢鲜花掌声，任性，脾气大，情绪化，易冲动，有点杠精，不服气，狂妄，玻璃心，最快，不过脑，伤领导，伤老公，容易瘦。",
            "目标不专一，财大伤身担不住，财来财去一场空，富屋穷命，比较现实。",
            "敏感，焦虑，纠结，选择困难症，收到压力多，容易自卑，追求完美，旺夫，适合当领导，容易遇小人，活出霸气的时候很有责任心，爱讨好，爱逃避，表面平静，内心波澜，容易记仇，怕失去。",
    };

    public final static String[] 星鹤_名称 = {
            "印枭思辨格",
            "劫比强旺格",
            "伤食灵动格",
            "财才实践格",
            "官杀自律格",
    };

    public final static String[] 星鹤_优点 = {
            "思考多、计划强、 逻辑强、愿景大。",
            "能担当、精力旺，爱付出讲义气，爱交友。",
            "灵气才华、创造力、情绪敏感、共情力高。",
            "务实、结果导向、目标强、经营敏感度强。",
            "严道、规则感强、风险意识强、自律周全。",
    };

    public final static String[] 星鹤_缺点 = {
            "要面子、思虑过度、行动弱、苛刻。",
            "自我、固执、易冲动。",
            "感性、易情绪化、思维发散、自律性差。",
            "想的少、过度重视性价比、目的性强现实。",
            "顾虑多、疑心重、风险过度担忧，压力大。",
    };

    public final static String[] 星鹤_交往 = {
            "重视梦想和影响力、注意名在利先。",
            "提出需求、价值确认、正向反馈。",
            "重视主观感受、给予空间感。",
            "强调实用、功利性、性价比。",
            "给与安全感、权威感、放心感。",
    };

    static {
        register(Source.明易.getName(), 命局描述器.class.getSimpleName(), "名称", 明易_名称);
        register(Source.明易.getName(), 命局描述器.class.getSimpleName(), "优点", 明易_优点);
        register(Source.明易.getName(), 命局描述器.class.getSimpleName(), "缺点", 明易_缺点);

        register(Source.星鹤.getName(), 命局描述器.class.getSimpleName(), "名称", 星鹤_名称);
        register(Source.星鹤.getName(), 命局描述器.class.getSimpleName(), "优点", 星鹤_优点);
        register(Source.星鹤.getName(), 命局描述器.class.getSimpleName(), "缺点", 星鹤_缺点);
        register(Source.星鹤.getName(), 命局描述器.class.getSimpleName(), "交往", 星鹤_交往);
    }

    public static JSONObject 描述(命局 pattern) {
        JSONObject result = new JSONObject();
        Map<String, String[]> descriptions = getDescriptions(命局描述器.class.getSimpleName());
        for (String key : descriptions.keySet()) {
            result.put(key, descriptions.get(key)[pattern.getValue()]);
        }
        return result;
    }

    public static String describe(命局 pattern) {
        StringBuilder sb = new StringBuilder();
        Map<String, String[]> descriptions = getDescriptions(命局描述器.class.getSimpleName());

        String name = describe(pattern, 描述类型.名称);
        sb.append(name)
                .append("的人的优点有：").append(describe(pattern, 描述类型.优点)).append(System.lineSeparator())
                .append("需要注意的地方有：").append(describe(pattern, 描述类型.缺点)).append(System.lineSeparator());
        String handle = describe(pattern, 描述类型.交往);
        if (handle != null) {
            sb.append("和").append(name).append("的人的相处方法是：").append(handle).append(System.lineSeparator());
        }
        return sb.toString();
    }

    public static String describe(命局 pattern, 描述类型 type) {
        Map<String, String[]> descriptions = getDescriptions(命局描述器.class.getSimpleName());
        return descriptions.get(type.name()) == null ? null : descriptions.get(type.name())[pattern.getValue()];
    }

}
