package com.zhinan.zhouyi.divine.meihua;

import com.zhinan.zhouyi.base.生克;
import com.zhinan.zhouyi.divine.六十四卦;
import com.zhinan.zhouyi.util.ChineseUtil;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class 梅花易数 {
    private final static int[] effects = new int[] {5, 4, 1, -2, -5};

    String question;
    LocalDateTime divineTime;

    Integer number1;
    Integer number2;
    Integer change;

    Boolean upperIsSelf;

    六十四卦 original;
    六十四卦 process;
    六十四卦 result;

    生克 originalStatus;
    生克 processStatus;
    生克 resultStatus;

    String description;

    private 梅花易数() {}

    public static 梅花易数 init(String question, Integer number1, Integer number2) {
        梅花易数 divination      = new 梅花易数();
        divination.divineTime   = LocalDateTime.now();

        divination.question     = question != null ? question : "你心中想问的问题";
        divination.number1      = number1  != null ?
                (ChineseUtil.isChineseCharacter((char) number1.intValue()) ? ChineseUtil.calculateNumberFromChineseCharacter((char) number1.intValue()) : number1)  :
                divination.divineTime.getYear() + divination.divineTime.getMonthValue() + divination.divineTime.getDayOfMonth();
        divination.number2      = number2  != null ?
                (ChineseUtil.isChineseCharacter((char) number2.intValue()) ? ChineseUtil.calculateNumberFromChineseCharacter((char) number2.intValue()) : number2)  :
                divination.divineTime.getYear() + divination.divineTime.getMonthValue() + divination.divineTime.getDayOfMonth() + divination.divineTime.getHour();
        divination.change       = (divination.number1 + divination.number2 + divination.divineTime.getSecond() + 5) % 6 + 1;

        divination.upperIsSelf  = divination.change < 4;

        divination.original     = 六十四卦.getByUpAndDown((divination.number1 + 7) % 8 + 1, (divination.number2 + 7) % 8 + 1);
        divination.process      = divination.original.to互卦();
        divination.result       = divination.original.change(divination.change);

        divination.originalStatus = getStatus(divination.original, divination.upperIsSelf);
        divination.processStatus  = getStatus(divination.process , divination.upperIsSelf);
        divination.resultStatus   = getStatus(divination.result  , divination.upperIsSelf);

        divination.description    = "初步判断，" +
                (effects[divination.originalStatus.getValue()]
                        + effects[divination.processStatus.getValue()]
                        + effects[divination.resultStatus.getValue()] > 0 ?
                        "这件事，应该能够如你所愿！✌️" : "这件事，你可能要失望了‍......")
                + " 【* 注意：初步判断结果只是系统根据卦象对问题的自动判断，只作为参考之用，不代表本卦实际的含义。"
                + "如需要获得更加准确的卦意解释，可以申请免费解卦，由专业解卦师为您解卦。】";


        return divination;
    }

    private static 生克 getStatus(六十四卦 gua, Boolean upperIsSelf) {
        return upperIsSelf ?
                gua.get上卦().getWuXing().compare(gua.get下卦().getWuXing()) :
                gua.get下卦().getWuXing().compare(gua.get上卦().getWuXing());
    }
}
