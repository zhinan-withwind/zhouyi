package com.zhinan.zhouyi.divine.qimen;

import com.zhinan.zhouyi.base.天干;
import lombok.Getter;

import java.util.List;
import java.util.function.Function;

@Getter
public class 宫位 {
    九宫 palace;
    九星 star;
    八门 door;
    八神 shen;
    天干 sky;
    天干 sky2;
    天干 ground;
    天干 ground2;
    天干 hidden;
    天干 hidden2;

    boolean isEmpty = false;
    boolean isHorse = false;

    public 宫位(九宫 palace) {
        this.palace = palace;
    }

    public static 宫位 find(List<宫位> palaceList, Function<宫位, Object> function, Object value) {
        return (宫位) palaceList.stream().filter(p -> value.equals(function.apply(p))).toArray()[0];
    }
}
