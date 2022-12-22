package com.zhinan.zhouyi.desc.mingyi;

import com.alibaba.fastjson.JSONObject;
import com.zhinan.zhouyi.common.Source;
import com.zhinan.zhouyi.common.Descriptor;
import com.zhinan.zhouyi.desc.基础描述器;
import com.zhinan.zhouyi.fate.bazi.命局;

@Descriptor(source = Source.明易)
public class 命局描述器 extends 基础描述器<命局> {
    static {
        final String[] 名称 = new String[] {
                "爱思考的印重局", "自信的命旺局", "聪明的伤官局", "有目标的财旺局", "谨慎的煞重局"
        };

        final String[] 优点 = new String[] {
                "善于思考，想法多，标准多，标准高，做事方法多，清高，爱讲道理，有任性，有执行力，在乎结果，相信权威，但是更相信自己，精力旺盛，爱学习，爱看书。",
                "很自信，内在非常有力量，有强大的拯救情结，好胜，做事喜欢体验过程，不太在乎结果，一心多用，热情，胆大，有领导气质，能操心，不服输，喜欢占山为王，热爱体育运动，能折腾，竞争心强，有使命感。",
                "才气，秀气，灵气，傲气。有才艺，情商高，口才好，悟性高，善解人意，开拓精神，潇洒，有梦想，有长远规划，善于表达，有行动力。",
                "有目标，眼里到处都是商机，对数字有感觉，目标感强，点子多，适合做销售，有行动力，欲望多，旺财的女人大都是女强人，爱操心，旺财的男人异性缘都很好，疼老婆。",
                "非常谨慎，一生热爱学习，有一技之长，有专业技能。",
        };

        final String[] 缺点 = new String[] {
                "有点固执，有时候一根筋，财迷，爱生小闷气，有洁癖，有时候小心眼。特别有标准，爱用自己的标准框别人，财迷，有洁癖（生活上及精神上），想完了就当干完了。放不下过去。",
                "自以为是，自负，有赌性，好斗，固执，精力旺盛，面子上有好多朋友，骨子里其实并不太喜欢朋友，喜欢独处，学习精神不集中，易走神，被认可的时候忠诚度最高。",
                "喜欢鲜花掌声，任性，脾气大，情绪化，易冲动，有点杠精，不服气，狂妄，玻璃心，最快，不过脑，伤领导，伤老公，容易瘦。",
                "目标不专一，财大伤身担不住，财来财去一场空，富屋穷命，比较现实。",
                "敏感，焦虑，纠结，选择困难症，收到压力多，容易自卑，追求完美，旺夫，适合当领导，容易遇小人，活出霸气的时候很有责任心，爱讨好，爱逃避，表面平静，内心波澜，容易记仇，怕失去。",
        };

        final String[] 优势 = new String[] {
                "喜欢学习、读书、标准高，有思想高度，自我价值感高，认可权威，讲原理、讲道理，记忆力好、逻辑思维强，点子多、想法多、仁慈、传统、爱思考、有梦想、有洁癖、做事有逻辑、有步骤、易指挥别人，有创意、喜欢易学、玄学，悟性高、喜欢刨根问底，喜欢独处。",
                "对待任何事情都喜欢享受过程，注重感受，能吃苦耐劳，喜欢亲力亲为，心怀天下，特别有拯救情节，朋友多，有一心多用的能力。精力旺盛，睡眠少，能折腾，喜欢占山为王。有毅力，被认可时忠诚度超高，竞争心强，不服输，超级自信。",
                "有才气、长的秀气、有灵气、有点傲气，聪明、激情、浪漫、表演、悟性高，善解人意、洞察力强、情商高，有开拓精神、行动力强，有创新性、有长远规划、有梦想，兴趣广泛、善于表达、热情、口才好，喜欢被表扬、喜欢鲜花、掌声，有上进心，有技能、有才艺、高雅、善辨、幽默。",
                "善于把握商机、发现商业价值，有投机性、目标感强、兴趣广泛、易多领域发展，异性缘好。为人大方、喜欢交朋友，有理财天赋，创意多，点子多，多情善感，执行力强。男生对老婆孩子好，女生偏女强人。",
                "易有一技之长，专业能力强，喜欢学习，善解人意，顾全大局，能屈能伸，正统、正直，责任心强，有担当，有气魄，进取心强，有一定的管理能力。天生敏感，有演员天赋，模仿能力强。女性懂事听话易旺夫。",
        };

        final String[] 不足 = new String[] {
                "喜欢挑毛病、喜欢给人建议、凡事喜欢自己琢磨研究、要求多、顾虑多、爱生闷气、想得多做得少、易头痛、从骨子里喜欢钱或欲望很多、固执、一根筋、有些人显得愚笨。",
                "冲动鲁莽，胆子大易坐不住，容易走神儿。自大、孤傲、孤僻、赌性强、固执、自以为是、易破财。个别人也有不够自信。",
                "任性、玻璃心、善变、夸大、反叛、多疑、嘴比脑子快、牢骚、小气、懒惰。急性子，有攀比心，易情绪化，易冲动，说话直接爱怼人，男性说话或行为伤领导，女性说话行为伤丈夫和领导。",
                "学习力不易集中，坐不住，易有用人靠前，不用人靠后特点，目的性太强，容易财来财去，目标多不专一，不深入，能折腾。",
                "焦虑，害怕失去，胆小懦弱，爱逃避，容易不好意思拒绝别人，过于谨慎、顾虑多、有选择困难症，特别爱面子，拖延症，不够自信、也易暴躁、霸道、野心、固执。",
        };

        register(Source.明易, 命局.class, "名称", 名称);
        register(Source.明易, 命局.class, "优点", 优点);
        register(Source.明易, 命局.class, "缺点", 缺点);
        register(Source.明易, 命局.class, "优势", 优势);
        register(Source.明易, 命局.class, "不足", 不足);
    }

    public String summary(Object o) {
        return getName(o)
//                + "的人的优点有：" + describe(o, "优点") + lineSeparator
//                + "需要注意的地方有：" + describe(o, "缺点") + lineSeparator
                ;
    }

    @Override
    public String describe(Object o) {
        return getName(o)
                + "的正面心性有：" + describe(o, "优势") + lineSeparator
                + "负面心性有：" + describe(o, "不足") + lineSeparator;
    }


    @Override
    public JSONObject 描述(Object o) {
        return super.描述(o)
                .fluentPut("简述", summary(o))
                .fluentPut("详述", describe(o));
    }

}
