package com.zhinan.zhouyi.desc.mingyi;

import com.zhinan.zhouyi.common.Descriptor;
import com.zhinan.zhouyi.common.Source;
import com.zhinan.zhouyi.date.DateFormatType;
import com.zhinan.zhouyi.date.DateTimeFormatter;
import com.zhinan.zhouyi.date.DateType;
import com.zhinan.zhouyi.date.SolarDateTime;
import com.zhinan.zhouyi.desc.基础描述器;
import com.zhinan.zhouyi.fate.luck.日运;
import com.zhinan.zhouyi.fate.luck.运势;

@Descriptor(source = Source.明易, isDefault = true)
public class 运势描述器 extends 基础描述器<运势> {
    public final static String[] 结束语 = {
            "美好的日子能带来快乐，痛苦的日子能带来经历，平淡的日子能带来安静.....",
            "不管怎么过，开心的日子就是值得纪念的日子.....",
            "我在日复一日的日子中开始懂得，所有普通的，看似无聊的日子，都是我要经历的日子.....",
            "有些人数着日子相逢，有些人数着日子相爱，还有些人，数着日子成长.....",
            "不论是拼命忙碌，奔波，都是为了过上好日子.....",
            "美好的日子，痛苦的日子总是相伴而来的，很多年后，这些日子都会成为我无法忘怀，如数家珍的日子.....",
            "你混日子，日子也混你，你好好对待日子，日子也不会辜负你.....",
            "特殊的日子平常过，平常的日子用心过.....",
            "辛辛苦苦，过舒服的日子，舒舒服服，过辛苦的日子.....",
            "这世上最浪费的日子，是那些没有笑声的日子.....",
            "这就是我的日子，不会太甜，也不会太苦.....",
            "有风的日子，云会跳舞，有雨的日子，花会唱歌，平平淡淡的日子，我的小小的幸福.....",
            "简简单单的过好现在的日子，别却想那些已经过了的日子.....",
            "每个起早贪黑的日子，都是有梦想的日子.....",
            "日子还是这样的日子，而我还是我.....",
            "休息的日子往往很短，上班的日子往往很长.....",
            "如果有一个经营日子的神，他会不会说：这里每个日子都是好日子，从来没有坏日子.....",
            "用春夏秋冬连成一串珠子，用悲欢离合粉饰那些日子，带着珠子，掐着日子，不知不觉，就是一辈子.....",
            "慢慢的，我学会了，在白衣飘飘的日子，别留遗憾，在炊烟袅袅的日子，笑看人间.....",
            "其实，生活并不是活了多少日子，而是记住了多少日子.....",
            "最快乐的日子，就是那些不忌口的日子.....",
            "喜欢充实的日子，也喜欢休闲的日子，还有那些忙的飞起来的日子.....",
            "我想过平平淡淡的日子，却也不想过平平淡淡的日子.....",
    };

    private String getEndWord() {
        return 结束语[new Double(Math.random() * 结束语.length).intValue()];
    }

    @Override
    public String describe(Object o) {
        String result = super.describe(o);
        if (o instanceof 日运) {
            StringBuilder sb = new StringBuilder();
            日运 yun = (日运) o;
            String s = ""; //lineSeparator;

            sb.append("今天是").append(DateTimeFormatter.getInstance(SolarDateTime.of(yun.getStartTime()))
                    .format(DateFormatType.CHINESE_NUMBER, DateType.SHORT_DATE))
                    .append("，").append(yun).append("日，是个")
                    .append(yun.getScore() > 60 ? "特别美好" : yun.getScore() < 40 ? "需要小心" : "平平淡淡")
                    .append("的日子。").append(s);

            sb.append("我").append(yun.getScore() > 60 ? "享受" : "接纳").append("老天为我注入的")
                    .append(describe(yun.getGanGod(), "基本含义"));
            if (!yun.getGanGod().equals(yun.getZhiGod()))
                sb.append("和").append(describe(yun.getZhiGod(), "基本含义")).append("，").append(s);

            sb.append("今天的我").append(yun.isGanGodGood() ? "特别" : "有点")
                    .append(describe(yun.getGanGod(), yun.isGanGodGood() ? "正向情绪" : "负向情绪")).append("，");
            if (!yun.getGanGod().equals(yun.getZhiGod())) {
                sb.append(yun.isGanGodGood() == yun.isZhiGodGood() ? "并且" : "但是")
                        .append(yun.isZhiGodGood() ? "特别" : "有点")
                        .append(describe(yun.getZhiGod(),
                                yun.isZhiGodGood() ? "正向情绪" : "负向情绪")).append("。").append(s);
            }
            if (yun.isGanGodGood() == yun.isZhiGodGood()) {
                if (yun.isGanGodGood()) {
                    sb.append("适合").append(describe(yun.getGanGod(), "正向行为"));
                    if (!yun.getGanGod().equals(yun.getZhiGod()))
                        sb.append("和").append(describe(yun.getGanGod(), "正向行为"));
                } else {
                    sb.append("实在是").append(describe(yun.getGanGod(), "负向行为"));
                    if (!yun.getGanGod().equals(yun.getZhiGod()))
                        sb.append("而又").append(describe(yun.getZhiGod(), "负向行为"));
                }
            } else {
                sb.append("适合")
                        .append(yun.isGanGodGood() ?
                                describe(yun.getGanGod(), "正向行为") :
                                describe(yun.getZhiGod(), "正向行为"))
                        .append("，不过会")
                        .append(yun.isGanGodGood() ?
                                describe(yun.getZhiGod(), "负向行为") :
                                describe(yun.getGanGod(), "负向行为"));
            }
            sb.append("。").append(s).append(getEndWord());

            result = sb.toString();
        }
        return result;
    }
}
