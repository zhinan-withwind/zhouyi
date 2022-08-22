package com.zhinan.zhouyi.test;

import com.zhinan.zhouyi.divine.qimen.九宫;
import com.zhinan.zhouyi.divine.qimen.八门;
import com.zhinan.zhouyi.divine.qimen.奇门遁甲;
import com.zhinan.zhouyi.divine.qimen.宫位;
import com.zhinan.zhouyi.out.QiMenOutputter;
import org.junit.jupiter.api.Test;
import org.testng.Assert;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class DoorTest {

    void testFindPalace() {
        List<宫位> palaceList = new ArrayList<>();
        for (九宫 p : 九宫.values()) {
            palaceList.add(new 宫位(p));
        }

        Assert.assertEquals(宫位.find(palaceList, 宫位::getPalace, 九宫.兑七), palaceList.get(6));
    }

    void testPaipan() {
        奇门遁甲 pan = 奇门遁甲.init("", LocalDateTime.of(1987, 2, 20, 3, 40));
        System.out.println(pan.getPattern().getName() + System.lineSeparator());
        宫位 palace = pan.getPalaceList().get(0);
        for (int i = 0; i < 8; i++) {
            System.out.println("=========================");
            System.out.println(palace.getPalace().getName());
            System.out.println(palace.getShen().getName());
            System.out.println(palace.getStar().getName());
            System.out.println(palace.getDoor().getFullName());
            System.out.println("天：" + palace.getSky().getName() + (palace.getSky2() == null ? "" : palace.getSky2().getName()));
            System.out.println("地：" + palace.getGround().getName() + (palace.getGround2() == null ? "" : palace.getGround2().getName()));
            System.out.println("暗：" + palace.getHidden().getName());
            if (palace.isEmpty()) System.out.println("空");
            if (palace.isHorse()) System.out.println("马");
            System.out.println("=========================");
            palace = pan.getPalace(palace.getPalace().getPalaceAfter());
        }
    }

    @Test
    void testOutput() {
        奇门遁甲 pan = 奇门遁甲.init("", LocalDateTime.of(1987, 2, 20, 3, 40));

        System.out.println(System.lineSeparator() + QiMenOutputter.outputPan(pan).build().output());
    }
}
