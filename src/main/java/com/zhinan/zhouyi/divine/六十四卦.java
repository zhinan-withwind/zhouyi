package com.zhinan.zhouyi.divine;

import com.zhinan.zhouyi.base.五行;
import com.zhinan.zhouyi.base.阴阳;
import lombok.Getter;

import java.util.Objects;

@Getter
public enum 六十四卦 {
    乾为天, 坤为地, 水雷屯, 山水蒙, 水天需, 天水讼, 地水师, 水地比, 风天小蓄, 天泽履, 地天泰, 天地否, 天火同人, 火天大有, 地山谦, 雷地豫,
    泽雷随, 山风蛊, 地泽临, 风地观, 火雷噬嗑, 山火贲, 山地剥, 地雷复, 天雷无妄, 山天大蓄, 山雷颐, 泽风大过, 坎为水, 离为火, 泽山咸, 雷风恒,
    天山遁, 雷天大壮, 火地晋, 地火明夷, 风火家人, 火泽睽, 水山蹇, 雷水解, 山泽损, 风雷益, 泽天夬, 天风姤, 泽地萃, 地风升, 泽水困, 水风井,
    泽火革, 火风鼎, 震为雷, 艮为山, 风山渐, 雷泽归妹, 雷火丰, 火山旅, 巽为风, 兑为泽, 风水涣, 水泽节, 风泽中孚, 雷山小过, 水火既济, 火水未济;

    八卦 上卦;
    八卦 下卦;
    String code;

    六十四卦() {
        String upperName = name().charAt(1) == '为' ? String.valueOf(name().charAt(2)) : String.valueOf(name().charAt(0));
        String downName  = name().charAt(1) == '为' ? String.valueOf(name().charAt(2)) : String.valueOf(name().charAt(1));
        this.上卦 = 八卦.getBySymbolize(upperName);
        this.下卦 = 八卦.getBySymbolize(downName );
        this.code = this.下卦.code + this.上卦.code;
    }

    public static 六十四卦 getByCode(String code) {
        六十四卦 result = null;
        for (六十四卦 value : values()) {
            if (value.code.equals(code)) {
                result = value;
                break;
            }
        }
        return result;
    }

    public static 六十四卦 of(int[] sixYao) {
        StringBuilder code = new StringBuilder();
        for (int i : sixYao) {
            code.append(i);
        }
        return getByCode(code.toString());
    }

    public static 六十四卦 getByUpAndDown(Integer upper, Integer down) {
        return 六十四卦.getByCode(八卦.getByInitValue(down).getCode() + 八卦.getByInitValue(upper).getCode());
    }

    public int getValue() { return ordinal() + 1; }

    public String getName() { return name(); }

    public 阴阳 getYao(int index) {
        return code.charAt(index - 1) == '1' ? 阴阳.阳 : 阴阳.阴;
    }

    public 六十四卦 change(int i) {
        StringBuilder sb = new StringBuilder(this.code);
        sb.setCharAt(i - 1, this.code.charAt(i - 1) == '0' ? '1' : '0');
        return getByCode(sb.toString());
    }

    /**
     * 问宫 —— 找出本卦属于京房八宫的哪一宫
     * @return 当前卦所属的京房八宫
     */
    public 京房八宫 getPalace() {
        for (京房八宫 palace : 京房八宫.values()) {
            for (六十四卦 gua : palace.generations) {
                if (gua.equals(this)) {
                    return palace;
                }
            }
        }
        return null;
    }

    /**
     * 寻世 —— 找出本卦在宫中的第几世
     * @return 当前卦的世值
     */
    public Integer getGeneration() {
        for (京房八宫 palace : 京房八宫.values()) {
            for (int i = 0; i < palace.generations.length; i++) {
                if (palace.generations[i].equals(this)) {
                    return i;
                }
            }
        }
        return null;
    }

    public 五行 getWuXing() {
        return Objects.requireNonNull(getPalace()).get主卦().get上卦().getWuXing();
    }

    public 六十四卦 to互卦() {
        return getByCode(""
                + getYao(2).getValue() + getYao(3).getValue() + getYao(4).getValue()
                + getYao(3).getValue() + getYao(4).getValue() + getYao(5).getValue()

        );
    }
}
