package com.zhinan.zhouyi.desc.xinghe;

import com.zhinan.zhouyi.base.十神;
import com.zhinan.zhouyi.common.Descriptor;
import com.zhinan.zhouyi.common.Source;
import com.zhinan.zhouyi.desc.基础描述器;

import java.util.HashMap;
import java.util.Map;

@Descriptor(source = Source.星鹤)
public class 十神描述器 extends 基础描述器<十神> {
    static {
        final String[] 基本象义 = {
                "逻辑性强、思维强、好读书、有学识、有修养、有愿景、内含不漏、有容忍性、思想保守传统、学识、文化、思维、聪明、智慧、尊严、忍耐力、承受力、少病、庇荫、保护意识、爱心、呆板不活跃。",
                "领悟力强、机智、孤独、自我、劳碌、有心计、博学多才、聪明、少言、妄想、偏门学问和学识、设计、独创、损福、损寿、疾病、失权、破财、瘦弱、挑食、隐疚、孤独、别离、福薄、养子、疾灾。",
                "固执己见、自傲性急、爱争斗、投机心强、欠思虑、爱吹牛、胆大妄为、不讲理、爱惹是非、有侵犯性、口舌、易招是非、有小人、欺骗、破财、高估自己、惰性强、易有分歧、好面子。",
                "刚健、果断、自我心强、冷静、我行我素、固执、有傲慢心、持久、爱交朋友、人脉、有体力、自尊心、好胜心、爱面子、心比天高、比较独立、劳苦、有恒心、朋友、同辈人脉。",
                "度量宽宏、温厚乐观、易交人缘、口腹之欲、爱美食、生财之道、福气、会表达、有才华、快乐、运动、多福多寿、旅游、爱运动、展示、流动、变化、艺术表现力。",
                "情绪敏感度佳、智慧才能发挥、创造力、展示力、不受约束管辖、情绪控制力弱、有才华、有表现力、著作才能、跳舞、才华、排泄、诽谤、诉讼、生财之道、多利欲妄为、不拘礼法。",
                "现实主义、务实、重视利益、落地执行能力强、做事小心谨慎、勤俭、吝啬、专一、资产、金 钱、财产、福禄、管理、中介、金融、享受、占有欲、欲望、薪水、工资、理解能力。",
                "副业兼职之财、意夕投机之财、流动性、随意之财、灰色收入之财、特殊技艺之财、财源多、技艺技巧、金融，中介、花钱大方、横财、欲望、朝刷益、做事细致、落地能力强。",
                "传统意识和理念、做事按部就班、有责任心、讲规则、自我管理能力强、有权利欲望、学历、名气、官位、官职、争议、正直、守法、自我约束力强、疾病、灾祸、法律、诽谤、非议。",
                "专制性、排他性、活跃、暴力、果断、好打抱不 平、暴力倾向、压制权利和管理欲望、灾难、祸患、病痛、权威、权利、地位、名望、霸道蛮横、不讲理、是非口舌、酒色、威胁、意外、外界压迫、欺诈。",
        };

        final String[] 正向心性 = {
                "聪颖仁慈、淡泊名利、逆来顺受",
                "精明干练、聪明机敏、多才多艺",
                "爽朗坦直、坚韧志旺、奋斗不屈",
                "稳健刚毅、勇敢冒险、积极进取",
                "聪明活跃、才华横溢、逞强好胜",
                "温文随和、待人宽厚、善良体贴",
                "正直负责、端庄严肃、循规蹈矩",
                "豪爽侠义、积极进取、威严机敏",
                "勤劳节约、踏实保守、任劳任怨",
                "慷慨重情、聪明机灵、乐观开朗",
        };

        final String[] 负向心性 = {
                "流于庸碌、缺乏进取、迟钝消极",
                "流于孤独、缺乏人情、自私冷漠",
                "流于盲目、缺乏理智、蛮横冲动",
                "流于孤僻、缺乏合群、孤立寡合",
                "流于任性、缺乏约束、桀警不驯",
                "流于虚伪、缺乏是非、迂腐懦弱",
                "流于刻板、墨守成规、意志不坚",
                "流于偏激、叛逆霸道、堕落极端",
                "流于苟且、缺乏进取、懦弱无能",
                "流于虚浮、缺乏节制、浮华风流",
        };

        register(Source.星鹤, 十神.class, "基本象义", 基本象义);
        register(Source.星鹤, 十神.class, "正向心性", 正向心性);
        register(Source.星鹤, 十神.class, "负向心性", 负向心性);

    }
}
