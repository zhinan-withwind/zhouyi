package com.zhinan.zhouyi.desc;

import com.zhinan.zhouyi.fate.bazi.命主;

public class 命主描述器 {
    public final static String[] descriptions = {
            "大树之木，仁慈，有领导力，掌控欲强，不喜欢被人管",
            "花草之木，仁爱，八面玲珑",
            "太阳之火，热情，外向，开朗，喜欢帮助别人，重礼节，讲礼貌，有仪式感",
            "炉中之火，热情，坚持，有顽强的生命力，有韧性，不达目的不罢休，有仪式感",
            "城墙之土，讲诚信，守信用",
            "田地之土，讲诚信，擅长整合团队，适合做二把手",
            "大块的金，讲义气，侠义气概，身材健硕",
            "首饰之金，灵性好，第六感强，讲义气",
            "大海之水，聪明，智慧",
            "雨露之水，聪明，第六感强，通神"
    };

    public static String describe(命主 zhu) {
        return descriptions[zhu.getValue()];
    }
}
