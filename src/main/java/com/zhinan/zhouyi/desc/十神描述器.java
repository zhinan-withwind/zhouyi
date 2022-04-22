package com.zhinan.zhouyi.desc;

import com.zhinan.zhouyi.base.十神;
import com.zhinan.zhouyi.fate.命局;

import java.util.HashMap;
import java.util.Map;

public class 十神描述器 {
    public enum 描述类型 {
        基本含义, 男性指代, 女性指代, 正向情绪, 负向情绪, 正向行为, 负向行为
    }

    public final static String[] 基本含义 = {
            "学习能力", "创作能力", "生命动力", "承受能力", "行动能力", "沟通能力", "管理能力", "执行能力", "赚钱能力", "改变能力"
    };

    public final static String[] 男性指代 = {
            "母亲", "祖父", "姐妹", "兄弟", "孙女", "孙子", "女儿", "儿子", "妻子", "情人"
    };

    public final static String[] 女性指代 = {
            "祖父", "母亲", "兄弟", "姐妹", "儿子", "女儿", "丈夫", "外婆", "父亲", "婆婆"
    };

    public final static String[] 正向情绪 = {
            "积极仁爱", "干练机敏", "热诚直爽", "承受能力", "思维活跃", "温柔随和", "正直负责", "豪爽义气", "任劳任怨", "乐观开朗"
    };

    public final static String[] 负向情绪 = {
            "消极迟钝", "缺乏人情", "冲动蛮横", "傲气孤僻", "傲气伤人", "沟通能力", "迂腐刻板", "偏激霸道", "随遇而安", "虚浮风流"
    };

    public final static String[] 正向行为 = {
            "去学习", "去创作", "去聚会", "找朋友", "谈生意", "去享受", "守规矩", "交朋友", "去挣钱", "去投资"
    };

    public final static String[] 负向行为 = {
            "木讷发呆", "说话呛人", "无理取闹", "居高临下", "放纵任性", "心不在焉", "翻脸无情", "想要吵架", "不想干活", "放飞自我"
    };

    public final static Map<描述类型, String[]> descriptions = new HashMap<描述类型, String[]>() {
        {
            put(描述类型.基本含义, 基本含义);
            put(描述类型.男性指代, 男性指代);
            put(描述类型.女性指代, 女性指代);
            put(描述类型.正向情绪, 正向情绪);
            put(描述类型.负向情绪, 负向情绪);
            put(描述类型.正向行为, 正向行为);
            put(描述类型.负向行为, 负向行为);
        }
    };

    public static String describe(十神 shen) {
        StringBuilder description = new StringBuilder();
        for (描述类型 type : 描述类型.values()) {
            description.append(type.name()).append(": ").append(describe(shen, type)).append(System.lineSeparator());
        }
        return description.toString();
    }

    public static String describe(十神 shen, 描述类型 type) {
        return descriptions.get(type)[shen.getValue()];
    }
}
