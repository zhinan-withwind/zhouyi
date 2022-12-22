package com.zhinan.zhouyi.desc.mingyi;

import com.zhinan.zhouyi.base.十神;
import com.zhinan.zhouyi.common.Descriptor;
import com.zhinan.zhouyi.common.Source;
import com.zhinan.zhouyi.desc.IDescriptor;
import com.zhinan.zhouyi.desc.基础描述器;

@Descriptor(source = Source.明易, isDefault = true)
public class 十神描述器 extends 基础描述器<十神> implements IDescriptor<十神> {
    static {
        final String[] 基本含义 = {
                "学习能力", "创作能力", "生命动力", "承受能力", "行动能力", "沟通能力", "赚钱能力", "改变能力", "管理能力", "执行能力"
        };

        final String[] 男性指代 = {
                "母亲", "祖父", "姐妹", "兄弟", "孙女", "孙子", "妻子", "情人", "女儿", "儿子"
        };

        final String[] 女性指代 = {
                "祖父", "母亲", "兄弟", "姐妹", "儿子", "女儿", "父亲", "婆婆", "丈夫", "外婆"
        };

        final String[] 正向情绪 = {
                "积极仁爱", "干练机敏", "热诚直爽", "承受能力", "思维活跃", "温柔随和", "任劳任怨", "乐观开朗", "正直负责", "豪爽义气"
        };

        final String[] 负向情绪 = {
                "消极迟钝", "缺乏人情", "冲动蛮横", "傲气孤僻", "傲气伤人", "沟通能力", "随遇而安", "虚浮风流", "迂腐刻板", "偏激霸道"
        };

        final String[] 正向行为 = {
                "去学习", "去创作", "去聚会", "找朋友", "谈生意", "去享受", "去挣钱", "去投资", "守规矩", "交朋友"
        };

        final String[] 负向行为 = {
                "木讷发呆", "说话呛人", "无理取闹", "居高临下", "放纵任性", "心不在焉", "不想干活", "放飞自我", "翻脸无情", "想要吵架"
        };

        register(Source.明易, 十神.class, "基本含义", 基本含义);
        register(Source.明易, 十神.class, "男性指代", 男性指代);
        register(Source.明易, 十神.class, "女性指代", 女性指代);
        register(Source.明易, 十神.class, "正向情绪", 正向情绪);
        register(Source.明易, 十神.class, "负向情绪", 负向情绪);
        register(Source.明易, 十神.class, "正向行为", 正向行为);
        register(Source.明易, 十神.class, "负向行为", 负向行为);
    }

    @Override
    public String describe(Object o) {
        StringBuilder sb = new StringBuilder();
        String name = getName(o);
        sb.append(name).append("是").append(((十神) o).getShengKe().getName()).append("你的能量，它主要代表的是你的")
                .append(describe(o, "基本含义")).append("。").append(lineSeparator)
                .append("如果你是男生，那么").append(name).append("代表你的").append(describe(o, "男性指代")).append("，")
                .append("如果你是女生，那么").append(name).append("代表你的").append(describe(o, "女性指代")).append("。").append(lineSeparator)
                .append("当").append(name).append("在积极的状态下，它会带给你").append(describe(o, "正向情绪")).append("的情绪，")
                .append("这时候的你很可能会").append(describe(o, "正向行为")).append("。").append(lineSeparator)
                .append("当").append(name).append("有点消极的时候，它会带给你").append(describe(o, "负向情绪")).append("的情绪，")
                .append("这状态的你大概率会").append(describe(o, "负向行为")).append("。").append(lineSeparator)
        ;
        return sb.toString();
    }

}
