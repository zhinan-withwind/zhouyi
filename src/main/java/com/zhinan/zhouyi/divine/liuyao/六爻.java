package com.zhinan.zhouyi.divine.liuyao;

import com.zhinan.zhouyi.base.地支;
import com.zhinan.zhouyi.base.干支;
import com.zhinan.zhouyi.base.阴阳;
import com.zhinan.zhouyi.util.DateUtil;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.LocalDateTime;
import java.util.List;

@Getter
public class 六爻 {
    @AllArgsConstructor
    public enum QUESTION_TYPE {
        财运(六亲.妻财.getName()), 学业(六亲.父母.getName()), 事业(六亲.官鬼.getName()),
        长辈(六亲.父母.getName()), 子女(六亲.子孙.getName()), 人际(六亲.兄弟.getName()),
        婚姻("配偶"), 恋爱("配偶"), 情感("配偶"),
        健康("世爻"), 其他("应爻");

        String operateGod;

        public static QUESTION_TYPE getByValue(int value) {
            return values()[value];
        }
    }

    int[] data;

    String        question;
    QUESTION_TYPE questionType;
    LocalDateTime divineTime;
    String date;
    String empty;

    地支 month;
    地支 day;

    卦象 originalGua;
    卦象 resultGua;

    /**
     * 六爻排盘
     * @param question  要问的问题
     * @param dateTime  起卦的时间
     * @param data      摇卦的数据
     *                  1 - 一个背两个字阳静爻
     *                  2 - 两个背一个字阴静爻
     *                  3 - 三个背     阳动爻
     *                  4 - 三个字     阴动爻
     */
    private 六爻(String question, int questionType, LocalDateTime dateTime, int[] data) {
        this.question     = question;
        this.questionType = QUESTION_TYPE.getByValue(questionType);
        this.divineTime   = dateTime;
        this.data         = data;
        List<干支> ganzhi  = DateUtil.toGanZhi(dateTime);
        month = ganzhi.get(1).getZhi();
        day   = ganzhi.get(2).getZhi();
        date  = ganzhi.get(0).getName() + "年 " + ganzhi.get(1).getName() + "月 " + ganzhi.get(2).getName() + "日";

        originalGua = 卦象.of(data, ganzhi.get(2).getGan());
        resultGua   = 卦象.change(originalGua);
    }

    public static 六爻 init(String question, int questionType, LocalDateTime dateTime, String data) {
        int[] result =  new int[6];
        for (int i = 0; i < 6; i++) {
            result[i] = Integer.parseInt(String.valueOf(data.charAt(i)));
        }
        return new 六爻(question, questionType, dateTime, result);
    }

    public static 六爻 init(String question, int questionType) {
        int[] data = new int[6];
        for (int i = 0; i < 6; i++) {
            data[i] = (int) Math.floor(Math.random() * 4 + 1);
        }
        return new 六爻(question, questionType, LocalDateTime.now(), data);
    }

    public static String generateResult(六爻 divination, QUESTION_TYPE questionType, 阴阳 sex) {
        String operateGodName = questionType.operateGod.equals("配偶") ?
                (sex.equals(阴阳.阳) ? 六亲.妻财.getName() : 六亲.官鬼.getName()) : questionType.operateGod;
        int operateGodIndex = operateGodName.equals("世爻") ? divination.originalGua.shi :
                operateGodName.equals("应爻") ? divination.originalGua.ying : -1;
        if (operateGodIndex == -1) {
            for (卦爻 yao : divination.originalGua.yaoList) {
                if (yao.getRelation().getName().equals(operateGodName)) {
                    operateGodIndex = yao.position.getValue();
                    break;
                }
            }
        }
        if (operateGodIndex == -1) {
            operateGodIndex = divination.originalGua.ying;
        }

        卦爻 operateYao = divination.originalGua.getYaoList().get(operateGodIndex);

        return "";
    }
}
