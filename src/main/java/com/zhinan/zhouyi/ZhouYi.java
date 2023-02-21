package com.zhinan.zhouyi;

import com.zhinan.zhouyi.common.ZhouYiAPI;
import com.zhinan.zhouyi.desc.ZhouYiDescriptor;
import org.springframework.boot.SpringApplication;

public class ZhouYi {
    public static void main(String[] args) {
        ZhouYiAPI.init(ZhouYiAPI.MODEL_TYPE.SCORE, System.lineSeparator());
        ZhouYiDescriptor.autoInit();

        SpringApplication.run(ZhouYi.class, args);
    }
}
