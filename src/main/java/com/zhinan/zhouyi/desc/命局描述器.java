package com.zhinan.zhouyi.desc;

import com.zhinan.zhouyi.fate.bazi.命局;

import java.util.HashMap;
import java.util.Map;

public class 命局描述器 {
    public enum 描述类型 {
        优点, 缺点
    }

    public final static String[] 优点 = new String[] {
            "很自信，内在非常有力量，有强大的拯救情结，好胜，做事喜欢体验过程，不太在乎结果，一心多用，热情，胆大，有领导气质，能操心，不服输，喜欢占山为王，热爱体育运动，能折腾，竞争心强，有使命感",
            "才气，秀气，灵气，傲气。有才艺，情商高，口才好，悟性高，善解人意，开拓精神，潇洒，有梦想，有长远规划，善于表达，有行动力",
            "有目标，眼里到处都是商机，对数字有感觉，目标感强，点子多，适合做销售，有行动力，欲望多，旺财的女人大都是女强人，爱操心，旺财的男人异性缘都很好，疼老婆。",
            "非常谨慎，一生热爱学习，有一技之长，有专业技能。",
            "善于思考，想法多，标准多，标准高，做事方法多，清高，爱讲道理，有任性，有执行力，在乎结果，相信权威，但是更相信自己，精力旺盛，爱学习，爱看书。",
    };

    public final static String[] 缺点 = new String[] {
            "自以为是，自负，有赌性，好斗，固执，精力旺盛，面子上有好多朋友，骨子里其实并不太喜欢朋友，喜欢独处，学习精神不集中，易走神，被认可的时候忠诚度最高",
            "喜欢鲜花掌声，任性，脾气大，情绪化，易冲动，有点杠精，不服气，狂妄，玻璃心，最快，不过脑，伤领导，伤老公，容易瘦。",
            "目标不专一，财大伤身担不住，财来财去一场空，富屋穷命，比较现实",
            "敏感，焦虑，纠结，选择困难症，收到压力多，容易自卑，追求完美，旺夫，适合当领导，容易遇小人，活出霸气的时候很有责任心，爱讨好，爱逃避，表面平静，内心波澜，容易记仇，怕失去",
            "有点固执，有时候一根筋，财迷，爱生小闷气，有洁癖，有时候小心眼。特别有标准，爱用自己的标准框别人，财迷，有洁癖（生活上及精神上），想完了就当干完了。放不下过去。",
    };

    public final static Map<描述类型, String[]> descriptions = new HashMap<描述类型, String[]>() {
        { put(描述类型.优点, 优点); put(描述类型.缺点, 缺点); }
    };

    public static String describe(命局 pattern) {
        return 描述类型.优点.name() + ": " + describe(pattern, 描述类型.优点) + System.lineSeparator()
             + 描述类型.缺点.name() + ": " + describe(pattern, 描述类型.缺点);
    }

    public static String describe(命局 pattern, 描述类型 type) {
        return descriptions.get(type)[pattern.getValue()];
    }
}
