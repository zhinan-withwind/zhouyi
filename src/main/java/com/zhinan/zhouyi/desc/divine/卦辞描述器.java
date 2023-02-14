package com.zhinan.zhouyi.desc.divine;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.zhinan.zhouyi.desc.BaseDescriptor;
import com.zhinan.zhouyi.divine.common.六十四卦;
import com.zhinan.zhouyi.util.FileUtil;

import java.util.HashMap;
import java.util.Map;

public class 卦辞描述器 extends BaseDescriptor<六十四卦> {
    public enum 描述类型 {
        含义, 卦辞, // 象传, 彖传
    }

    public final static String[] 含义 = {
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

    public final static String[] 卦辞 = {
            "乾：元，亨，利，贞。",
            "坤：元亨，利牝马之贞。君子有攸往，先迷后得主。利西南得朋，东北丧朋。安贞吉。",
            "屯：元亨利贞。勿用有攸往，利建侯。",
            "蒙：亨。匪我求童蒙，童蒙求我。初噬告，再三渎，渎则不告。利贞。",
            "需：有孚，光亨，贞吉。利涉大川。",
            "讼：有孚，窒惕，中吉。终凶。利见大人，不利涉大川。",
            "师：贞，丈人吉，无咎。",
            "比：吉。原筮，元永贞，无咎。不宁方来，后夫凶。",
            "小畜：亨。密云不雨，自我西郊。",
            "履：履虎尾，不咥人，亨。",
            "泰：小往大来，吉亨。",
            "否：否之匪人，不利君子贞，大往小来。",
            "同人：同人于野，亨。利涉大川，利君子贞。",
            "大有：元亨。",
            "谦：亨，君子有终。",
            "豫：利建侯行师。",
            "随：元亨利贞，无咎。",
            "蛊：元亨，利涉大川。先甲三日，后甲三日。",
            "临：元，亨，利，贞。至于八月有凶。",
            "观：盥而不荐，有孚颙若。",
            "噬嗑：亨。利用狱。",
            "贲：亨。小利有所往。",
            "剥：不利有攸往。",
            "复：亨。出入无疾，朋来无咎。反复其道，七日来复，利有攸往。",
            "无妄：元亨利贞。其匪正有眚，不利有攸往。",
            "大畜：利贞，不家食吉，利涉大川。",
            "颐：贞吉。观颐，自求口实。",
            "大过：栋桡，利有攸往，亨。",
            "坎：习坎，有孚。维心亨。行有尚。",
            "离：利贞，亨。畜牝牛，吉。",
            "咸：亨，利贞。取女吉。",
            "恒：亨，无咎，利贞。利有攸往。",
            "遁：亨。小利贞。",
            "大壮：利贞。",
            "晋：康侯用锡马蕃庶，昼日三接。",
            "明夷：利艰贞。",
            "家人：利女贞。",
            "睽：小事吉。",
            "蹇：利西南，不利东北。利见大人，贞吉。",
            "解：利西南，无所往，其来复吉。有攸往，夙吉。",
            "损：有孚，元吉，无咎，可贞。利有攸往。曷之用？二簋可用享。",
            "益：利有攸往，利涉大川。",
            "夬：扬于王庭。孚号有厉。告自邑，不利即戎，利有攸往。",
            "姤：女壮，勿用取女。",
            "萃：亨。王假有庙。利见大人，亨，利贞。用大牲吉，利有攸往。",
            "升：元亨，用见大人，勿恤，南征吉。",
            "困：亨，贞，大人吉，无咎。有言不信。",
            "井：改邑不改井，无丧无得。往来井井。汔至亦未繘井，羸其瓶，凶。",
            "革：己日乃孚。元亨利贞。悔亡。",
            "鼎：元吉，亨。",
            "震：亨。震来虩虩，笑言哑哑。震惊百里，不丧匕鬯。",
            "艮：艮其背，不获其身，行其庭，不见其人，无咎。",
            "渐：女归吉，利贞。",
            "归妹：征凶，无攸利。",
            "丰：亨。王假之，勿忧，宜日中。",
            "旅：小亨，旅贞吉。",
            "巽：小亨。利有攸往，利见大人。",
            "兑：亨，利贞。",
            "涣：亨。王假有庙。利涉大川，利贞。",
            "节：亨。苦节不可贞。",
            "中孚：豚鱼，吉，利涉大川，利贞。",
            "小过：亨，利贞。可小事，不可大事。飞鸟遗之音。不宜上，宜下，大吉。",
            "既济：亨小，利贞。初吉终乱。",
            "未济：亨。小狐汔济，濡其尾，无攸利。"
    };

    private static final JSONArray description64 = JSON.parseArray(FileUtil.loadResource("data/description64.json"));

    public final static Map<卦辞描述器.描述类型, String[]> descriptions = new HashMap<卦辞描述器.描述类型, String[]>() {
        {
            put(描述类型.含义, 含义);
            put(描述类型.卦辞, 卦辞);
        }
    };

    public static String describe(六十四卦 gua) {
        StringBuilder description = new StringBuilder();
        for (卦辞描述器.描述类型 type : 卦辞描述器.描述类型.values()) {
            description.append(type.name()).append(": ").append(describe(gua, type)).append(lineSeparator);
        }
        return description.toString();
    }

    public static String describe(六十四卦 gua, 卦辞描述器.描述类型 type) {
        return descriptions.get(type)[gua.getValue() - 1];
    }

    public static JSONObject describe(六十四卦 gua, int yao) {
        return (JSONObject) ((JSONObject) description64.get(gua.getValue() - 1)).getJSONArray("卦爻").get(yao);
    }

    public static JSONObject fullDescribe(六十四卦 gua) {
        JSONObject result = new JSONObject();
        for (卦辞描述器.描述类型 type : 卦辞描述器.描述类型.values()) {
            result.fluentPut(type.name(), describe(gua, type));
        }
        return result;
    }
}
