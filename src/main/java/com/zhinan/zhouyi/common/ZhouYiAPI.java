package com.zhinan.zhouyi.common;

import com.zhinan.zhouyi.desc.ZhouYiDescriptor;
import com.zhinan.zhouyi.desc.BaseDescriptor;
import com.zhinan.zhouyi.fate.bazi.model.FatePatternModel;
import com.zhinan.zhouyi.fate.bazi.model.MonthBasedModel;
import com.zhinan.zhouyi.fate.bazi.model.ScoreBasedModel;
import com.zhinan.zhouyi.fate.bazi.八字;
import lombok.Setter;
import lombok.SneakyThrows;

@Setter
public class ZhouYiAPI {
    public enum MODEL_TYPE { MONTH, SCORE }

    private static Class<? extends FatePatternModel> model = ScoreBasedModel.class;

    public  static void init(MODEL_TYPE modelType, String lineSeparator) {
        if (modelType.equals(MODEL_TYPE.MONTH)) {
            model = MonthBasedModel.class;
        } else {
            model = ScoreBasedModel.class;
        }
        ZhouYiDescriptor.init();
        if (lineSeparator != null) BaseDescriptor.lineSeparator = lineSeparator;
    }

    @SneakyThrows
    public static FatePatternModel getModel(八字 bazi) {
        return model.newInstance().of(bazi);
    }
}
