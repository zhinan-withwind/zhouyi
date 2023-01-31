package com.zhinan.zhouyi.desc.divine;

import com.alibaba.fastjson.JSONObject;
import com.zhinan.zhouyi.desc.BaseDescriptor;
import com.zhinan.zhouyi.divine.other.称骨;

public class 称骨描述器 extends BaseDescriptor<称骨> {

    @Override
    public JSONObject fullDescribe(Object o) {
        称骨 weight = (称骨) o;
        return new JSONObject()
                .fluentPut("生日", new JSONObject()
                    .fluentPut("年", weight.getGanZhiDateTime().getGanZhiYear() .getName())
                    .fluentPut("月", weight.getGanZhiDateTime().getGanZhiMonth().getName())
                    .fluentPut("日", weight.getGanZhiDateTime().getGanZhiDay()  .getName())
                    .fluentPut("时", weight.getGanZhiDateTime().getGanZhiHour() .getName())
                )
                .fluentPut("重量", new JSONObject()
                    .fluentPut("年重", describeWeight(weight.getYear()))
                    .fluentPut("月重", describeWeight(weight.getMonth()))
                    .fluentPut("日重", describeWeight(weight.getDay()))
                    .fluentPut("时重", describeWeight(weight.getTime()))
                )
                .fluentPut("骨重", describeWeight(weight.getWeight()))
                .fluentPut("歌诀", 称骨歌[weight.getSex()][weight.getWeight() - 21]);
    }

    private String describeWeight(int weight) {
        return (weight / 10 > 0 ? weight / 10 + "两" : "") + (weight % 10 > 0 ? weight % 10 + "钱" : "");
    }

    public final static String[][] 称骨歌 = {
            {
                    "二两一钱：生身此命运不通，乌云盖月黑朦胧，莫向故园载花木，可来幽地种青松。",
                    "二两二钱：女命孤冷独凄身，此身推来路乞人，操心烦恼难度日，一生痛苦度光阴。",
                    "二两三钱：女命生来轻薄人，营谋事作难称心，六亲骨肉亦无靠，奔走劳碌困苦门。",
                    "二两四钱：女命推来福禄无，治家艰难辛苦多，丈夫儿女不亲爱，奔走他乡作游姑。",
                    "二钱五两：此命一身八字低，家庭艰辛多苦妻，娘家亲友冷如炭，一生勤劳多忧眉。",
                    "二钱六两：平生依禄但苦求，两次配夫带忧愁，咸酸苦辣他偿过，晚年衣食本无忧。",
                    "二钱七两：此格做事单独强，难告夫君作主张，心问口来口问心，晚景衣禄宜自生。",
                    "二钱八两：女命生来八字轻，为善作事也无因，你把别人当亲生，别人对你假殷情。",
                    "二钱九两：花支艳来硬性身，自奔自力不求人，若问求财方可止，在苦有甜度光阴。",
                    "三两：女命推来比郎强，婚姻大事碍无障，中年走过坎坷地，末年渐经行前强。",
                    "三钱一两：早年行运在忙头，劳碌奔波苦勤求，费力劳心把家立，后来晚景名忧愁。",
                    "三两二钱：时逢运来带吉神，从有凶星转灰尘，真变假来假成真，结拜弟妹当亲生。",
                    "三两三钱：初限命中有变化，中年可比树落花，勤俭持家难度日，晚年成业享荣华。",
                    "三两四钱：矮巴勾枣难捞枝，看破红尘最相宜，谋望求财空费力，婚姻三娶两次离。",
                    "三两五钱：女子走冰怕冰薄，出行交易受残霜，婚姻周郎休此意，官司口舌须相加。",
                    "三两六钱：忧悉常锁两眉间，家业挂心不等闲，从今以后防口角，任意行移不相关。",
                    "三两七钱：此命推来费运多，若作摧群受折磨，山路崎岖吊下耳，左插右安心难留。",
                    "三两八钱：凤鸣岐山四方扬，女命逢此大吉昌，走失夫君音信有，晚年衣禄财盈箱。",
                    "三两九钱：女命推来运未能，劳碌奔波一场空，好似俊鸟在笼锁，中年未限凄秋风。",
                    "四两：目前月令运不良，千辛万苦受煎熬，女身受得多苦难，晚年福禄比密甜。",
                    "四两一钱：此命推来一般艰，女子为人很非凡，中年逍遥多自在，晚年更比中年超。",
                    "四两二钱：杜井破废已多年，今有泉水出来鲜，资生济竭人称美，中运来转喜安然。",
                    "四两三钱：推车靠涯道路通，女名求财也无穷，婚姻出配无阻碍，疾病口舌离身躬。",
                    "四两四钱：夜梦金银醒来空，立志谋业运不通，婚姻难成交易散，夫君趟失未见踪。",
                    "四两五钱：女命终身驳杂多，六亲骨肉不相助，命中男女都难养，劳碌辛苦还奔波。",
                    "四两六钱：孤舟行水离沙滩，离乡出外早过家，是非口舌皆无碍，婚姻合配紫微房。",
                    "四两七钱：时来运转喜开颜，多年枯木逢春花，枝叶重生多茂盛，凡人见得都赞夸。",
                    "四两八钱：一朵鲜花镜中开，看着极好取不来，劝你休把镜花想，此命推来主可癫。",
                    "四两九钱：一生为人福宏名，心兹随君显门庭，容貌美丽惹人爱，银钱富足万事成。",
                    "五两：马氏太公不相和，好命逢此忧凝多，恩人无义反为仇，是非平地起风波。",
                    "五两一钱：肥羊一群入出场，防虎逢之把口张，适口充饥心欢喜，女命八字大吉昌。",
                    "五两二钱：顺风行舟扯起帆，上天又助一顺风，不用费力逍遥去，任意顺行大享通。",
                    "五两三钱：此命相貌眉目清，文武双全功名成，一生衣禄皆无缺，可算世上积福人。",
                    "五两四钱：运开满腹好文章，谋事求财大吉祥，出行交易多得稳，到处享通姓名扬。",
                    "五两五钱：发政旅仁志量高，女命求财任他乡，交舍婚姻多有意，无君出外有音耗。",
                    "五两六钱：明珠辉吐离埃来，女有口有清散开，走失郎君当两归，交易有成永无灾。",
                    "五两七钱：游鱼戏水被网惊，踊身变化入龙门，三根杨柳垂金钱，万朵桃花显价能。",
                    "五两八钱：此命推来转悠悠，时运未来莫强求，幸得今日重反点，自有好运在后头。",
                    "五两九钱：雨雪载途活泥泞，交易不安难出生，疾病还拉婚姻慢，谋望求财事难寻。",
                    "六两：女命八字喜气和，谋事求财吉庆多，口舌渐消疾病少，夫君走别归老窠。",
                    "六两一钱：缘木求鱼事多难，虽不得鱼无害反，若是行险弄巧地，事不遂心枉安凡。",
                    "六两二钱：指日高升气象新，走失待人有信音，好命遇事遂心好，伺病口舌皆除根。",
                    "六两三钱：五官脱运难抬头，妇命须当把财求，交易少行有人助，一生衣禄不须愁。",
                    "六两四钱：俊鸟曾得出胧中，脱离为难显威风，一朝得意福力至，东南西北任意通。",
                    "六两五钱：女命推来福非轻，兹善为事受人敬，天降文王开基业，八百年来富贵门。",
                    "六两六钱：时来运转闺阁楼，贤德淑女君子求，击鼓乐之大吉庆，女命逢此喜悠悠。",
                    "六两七钱：乱丝无头定有头，碰得亲事暂折磨，交易出行无好处，谋事求财心不遂。",
                    "六两八钱：水底明月不可捞，女命早限运未高，交易出行难获利，未运终得渐见好。",
                    "六两九钱：太公封祖不非凡，女子求财稳如山，交易合伙多吉庆，疾病口角遗天涯。",
                    "七两：本命推断喜气新，恰遇郎君金遂心，坤身来交正当运，富贵衣禄乐平生。",
                    "七两一钱：此命推来宏运交，不须再愁苦劳难，一生身有衣禄福，安享荣华胜班超。"
            }, {
                    "二两一钱：短命非业谓大空，平生灾难事重重，凶祸频临陷逆境，终世困苦事不成。",
                    "二两二钱：身寒骨冷苦伶仃，此命推来行乞人，劳劳碌碌无度日，终年打拱过平生。",
                    "二两三钱：此命推来骨格轻，求谋作事事难成，妻儿兄弟应难许，别处他乡作散人。",
                    "二两四钱：此命推来福禄无，门庭困苦总难荣，六亲骨肉皆无靠，流浪他乡作老翁。",
                    "二钱五两：此命推来祖业微，门庭营度似稀奇，六亲骨肉如冰炭，一世勤劳自把持。",
                    "二钱六两：平生衣禄苦中求，独自营谋事不休，离祖出门宜早计，晚来衣禄自无休。",
                    "二钱七两：一生作事少商量，难靠祖宗作主张，独马单枪空做去，早年晚岁总无长。",
                    "二钱八两：一生行事似飘蓬，祖宗产业在梦中，若不过房改名姓，也当移徒二三通。",
                    "二钱九两：初年运限未曾亨，纵有功名在后成，须过四旬才可立，移居改姓始为良。",
                    "三两：劳劳碌碌苦中求，东奔西走何日休，若使终身勤与俭，老来稍可免忧愁。",
                    "三钱一两：忙忙碌碌苦中求，何日云开见日头，难得祖基家可立，中年衣食渐无忧。",
                    "三两二钱：初年运蹇事难谋，渐有财源如水流，到得中年衣食旺，那时名利一齐收。",
                    "三两三钱：早年做事事难成，百年勤劳枉费心，半世自如流水去，后来运到始得金。",
                    "三两四钱：此命福气果如何，僧道门中衣禄多，离祖出家方为妙，朝晚拜佛念弥陀。",
                    "三两五钱：生平福量不周全，祖业根基觉少传，营事生涯宜守旧，时来衣食胜从前。",
                    "三两六钱：不须劳碌过平生，独自成家福不轻，早有福星常照命，任君行去百般成。",
                    "三两七钱：此命般般事不成、弟兄少力自孤行，虽然祖业须微有，来得明时去不明。",
                    "三两八钱：一身骨肉最清高，早入簧门姓氏标，待到年将三十六，蓝衫脱去换红袍。",
                    "三两九钱：此命终身运不通，劳劳作事尽皆空，苦心竭力成家计，到得那时在梦中。",
                    "四两：平生衣禄是绵长，件件心中自主张，前面风霜多受过，后来必定享安康。",
                    "四两一钱：此命推来自不同，为人能干异凡庸，中年还有逍遥福：不比前时运来通。",
                    "四两二钱：得宽怀处且宽怀，何用双眉皱不开，若使中年命运济，那时名利一起来。",
                    "四两三钱：为人心性最聪明，作事轩昂近贵人，衣禄一生天注定，不须劳碌是丰亨。",
                    "四两四钱：万事由天莫苦求，须知福碌赖人修，当年财帛难如意，晚景欣然便不优。",
                    "四两五钱：名利推求竟若何？前番辛苦后奔波，命中难养男和女，骨肉扶持也不多。",
                    "四两六钱：东西南北尽皆通，出姓移居更觉隆，衣禄无穷无数定，中年晚景一般同。",
                    "四两七钱：此命推求旺末年，妻荣子贵自怡然，平生原有滔滔福，可卜财源若水泉。",
                    "四两八钱：初年运道未曾通，几许蹉跎命亦穷，兄弟六亲无依靠，一生事业晚来整。",
                    "四两九钱：此命推来福不轻，自成自立显门庭，从来富贵人钦敬，使婢差奴过一生。",
                    "五两：为利为名终日劳，中年福禄也多遭，老来自有财星照，不比前番目下高。",
                    "五两一钱：一世荣华事事通，不须劳碌自亨通，兄弟叔侄皆如意，家业成时福禄宏。",
                    "五两二钱：一世亨通事事能，不须劳苦自然宁，宗族有光欣喜甚，家产丰盈自称心。",
                    "五两三钱：此格推来福泽宏，兴家立业在其中，一生衣食安排定，却是人间一福翁。",
                    "五两四钱：此格详采福泽宏，诗书满腹看功成，丰衣足食多安稳，正是人间有福人。",
                    "五两五钱：策马扬鞭争名利，少年作事费筹论，一朝福禄源源至，富贵荣华显六亲。",
                    "五两六钱：此格推来礼义通，一身福禄用无穷，甜酸苦辣皆尝过，滚滚财源盈而丰。",
                    "五两七钱：福禄丰盈万事全，一身荣耀乐天年，名扬威震人争羡，此世逍遥宛似仙。",
                    "五两八钱：平生衣食自然来，名利双全富贵偕，金榜题名登甲第，紫袍玉带走金阶。",
                    "五两九钱：细推此格秀而清，必定才高学业成，甲第之中应有分，扬鞭走马显威荣。",
                    "六两：一朝金榜快题名，显祖荣宗大器成，衣禄定然无欠缺，田园财帛更丰盈。",
                    "六两一钱：不作朝中金榜客，定为世上大财翁，聪明天付经书熟，名显高褂自是荣。",
                    "六两二钱：此命生来福不穷，读书必定显亲宗，紫衣玉带为卿相，富贵荣华孰与同。",
                    "六两三钱：命主为官福禄长，得来富贵实非常，名题雁塔传金榜，大显门庭天下扬。",
                    "六两四钱：此格威权不可当，紫袍金带尘高堂，荣华富贵谁能及，万古留名姓氏扬。",
                    "六两五钱：细推此命福非轻，富贵荣华孰与争，定国安邦人极品，威声显赫震寰瀛。",
                    "六两六钱：此格人间一福人，堆金积玉满堂春，从来富贵有天定，金榜题名更显亲。",
                    "六两七钱：此命生来福自宏，田园家业最高隆，平生衣禄盈丰足，一路荣华万事通。",
                    "六两八钱：富贵由天莫苦求，万事家计不须谋，十年不比前番事，祖业根基千古留。",
                    "六两九钱：君是人间福禄星，一生富贵众人钦，总然衣禄由天定，安享荣华过一生。",
                    "七两：此命推来福不轻，何须愁虑苦劳心，荣华富贵已天定，正笏垂绅拜紫宸。",
                    "七两一钱：此命生成大不同，公侯卿相在其中，一生自有逍遥福，富贵荣华极品隆。",
                    "七两二钱：此命推来天下隆，必定人间一主公，富贵荣华数不尽，定为乾坤一蛟龙。",
                    "七两三钱：此命推来天下隆，必定人间一主公，富贵荣华数不尽，定为乾坤一蛟龙。"
            }
    };
}
