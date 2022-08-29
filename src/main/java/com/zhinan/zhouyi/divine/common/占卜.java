package com.zhinan.zhouyi.divine.common;

import lombok.Data;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

@Data
public class 占卜 {
    String question;
    LocalDateTime divineTime;
    Map<String, String> initInfo = new HashMap<>();

    public void init(String question, LocalDateTime divineTime, Map<String, String> initInfo) {
        this.question   = question   == null ? "你心中想问的问题"     : question;
        this.divineTime = divineTime == null ? LocalDateTime.now() : divineTime;
        this.initInfo   = initInfo;
    }
}
