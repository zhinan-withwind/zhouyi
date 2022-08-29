package com.zhinan.zhouyi.divine.liuyao;

import com.zhinan.zhouyi.base.五行;
import com.zhinan.zhouyi.base.地支;
import com.zhinan.zhouyi.base.天干;
import com.zhinan.zhouyi.base.阴阳;
import com.zhinan.zhouyi.divine.common.世名;
import com.zhinan.zhouyi.divine.common.京房八宫;
import com.zhinan.zhouyi.divine.common.六十四卦;
import lombok.Getter;
import lombok.experimental.Accessors;

import java.util.*;

@Getter
@Accessors(chain = true)
public class 卦象 {
    List<卦爻>         yaoList   = new ArrayList<>();
    Map<Integer, 卦爻> hiddenMap = new HashMap<>();

    阴阳 yinYang;
    五行 wuXing;
    天干 day;

    String palaceName;
    世名 generation;

    int shi;
    int ying;

    boolean allGood = false;
    boolean allBad  = false;

    六十四卦 gua;

    public final static int[] shiYao  = {6, 1, 2, 3, 4, 5, 4, 3};
    public final static int[] yingYao = {3, 4, 5, 6, 1, 2, 1, 6};
    public final static 地支[][][] diZhis =
            {{
                    {地支.子, 地支.寅, 地支.辰},
                    {地支.巳, 地支.卯, 地支.丑},
                    {地支.卯, 地支.丑, 地支.亥},
                    {地支.子, 地支.寅, 地支.辰},
                    {地支.丑, 地支.亥, 地支.酉},
                    {地支.寅, 地支.辰, 地支.午},
                    {地支.辰, 地支.午, 地支.申},
                    {地支.未, 地支.巳, 地支.卯}
            },{
                    {地支.午, 地支.申, 地支.戌},
                    {地支.亥, 地支.酉, 地支.未},
                    {地支.酉, 地支.未, 地支.巳},
                    {地支.午, 地支.申, 地支.戌},
                    {地支.未, 地支.巳, 地支.卯},
                    {地支.申, 地支.戌, 地支.子},
                    {地支.戌, 地支.子, 地支.寅},
                    {地支.丑, 地支.亥, 地支.酉}
            }};

    private 卦象() {}

    public static 卦象 of(int[] values, 天干 day) {
        卦象 divine = new 卦象();

        divine.day = day;
        int[] data = new int[6];
        for (int i = 0; i < 6; i++) {
            data[i] = values[i] % 2;
        }
        divine.gua = 六十四卦.of(data);

        京房八宫 palace = divine.gua.getPalace();
        assert palace != null;
        divine.palaceName = palace.getName();

        六十四卦 zhu = palace.get主卦();
        Integer index = divine.gua.getGeneration();
        assert index != null;
        divine.generation = 世名.getByValue(index);

        divine.wuXing  = zhu.get上卦().getWuXing();
        divine.yinYang = zhu.get上卦().getYinYang();
        divine.shi     = shiYao [index];
        divine.ying    = yingYao[index];
        divine.allGood = 六合.is(divine.gua);
        divine.allBad  = 六冲.is(divine.gua);

        List<五行> wuXingList = new ArrayList<>(Arrays.asList(五行.木, 五行.火, 五行.土, 五行.金, 五行.水));
        for (int i = 0; i < values.length; i++) {
            地支 zhi = diZhis[i / 3][(i < 3 ? divine.gua.get下卦() : divine.gua.get上卦()).getInitValue() - 1][i % 3];
            wuXingList.remove(zhi.getWuXing());
            卦爻 yao = new 卦爻(zhi,
                    阴阳.getByValue(values[i] % 2),
                    zhi.getWuXing(),
                    六亲.getByValue(divine.getWuXing().compare(zhi.getWuXing()).getValue()),
                    六神.getByValue((i + (day.getValue() < 5 ? day.getValue() / 2 : (day.getValue() + 2) / 2)) % 6),
                    位置.getByValue(i),
                    divine.shi   == i + 1,
                    divine.ying == i + 1,
                    values[i] > 2,
                    false
            );
            divine.yaoList.add(yao);
        }
        for (五行 wuXing : wuXingList) {
            for (int i = 0; i < 6; i++) {
                地支 zhi = diZhis[i / 3][zhu.get上卦().getInitValue() - 1][i % 3];
                if (zhi.getWuXing().equals(wuXing)) {
                    卦爻 fly = divine.yaoList.get(i);
                    卦爻 yao = new 卦爻(zhi, fly.yinYang, zhi.getWuXing(),
                            六亲.getByValue(divine.getWuXing().compare(zhi.getWuXing()).getValue()),
                            fly.animal, fly.position, false, false, false, false
                    );
                    fly.setFly(true);
                    divine.hiddenMap.put(fly.getPosition().getValue(), yao);
                }
            }
        }
        return divine;
    }

    public static 卦象 change(卦象 original) {
        int[] values = new int[6];
        for (int i = 0; i < 6; i++) {
            卦爻 yao = original.yaoList.get(i);
            values[i] = yao.change ? 3 - yao.yinYang.getValue() : yao.yinYang.getValue();
        }
        卦象 gua = 卦象.of(values, original.day);
        for (卦爻 yao : gua.yaoList) {
            yao.setRelation(六亲.getByValue(original.wuXing.compare(yao.wuXing).getValue()));
        }
        return gua;
    }
}
