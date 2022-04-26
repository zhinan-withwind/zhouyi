package com.zhinan.zhouyi.effect;

import com.zhinan.zhouyi.base.元素;
import com.zhinan.zhouyi.base.地支;
import com.zhinan.zhouyi.base.天干;
import com.zhinan.zhouyi.base.干支;

import java.util.*;
import java.util.function.Function;

/**
 * @author withwind
 *
 * 合化冲的逻辑类
 */
public class 合化冲 {
    private static List<可作用> values;

    public static List<可作用> getValues() {
        if (values == null) {
            values = new ArrayList<>();
            values.addAll(Arrays.asList(天干五合.values()));
            values.addAll(Arrays.asList(地支三会.values()));
            values.addAll(Arrays.asList(地支三合.values()));
            values.addAll(Arrays.asList(地支六合.values()));
            values.addAll(Arrays.asList(地支半合.values()));
            values.addAll(Arrays.asList(地支暗合.values()));
            values.addAll(Arrays.asList(天干四冲.values()));
            values.addAll(Arrays.asList(地支六穿.values()));
            values.addAll(Arrays.asList(地支相刑.values()));
            values.addAll(Arrays.asList(地支相破.values()));
        }
        return values;
    }

    /**
     * 当前的作用是否被满足
     * @param effect    作用类型
     * @param elements  参与作用的元素
     * @return  如果满足作用，返回作用关系，如果不满足，返回null
     */
    public static 作用关系 match(可作用 effect, List<作用元素> elements) {
        作用关系 relation = null;
        boolean result = true;
        List<元素> matchList = new ArrayList<>();
        for (作用元素 element : elements) {
            boolean match = effect.getElements().contains(element.getElement());
            result = result && match;
            if (match && !matchList.contains(element.getElement())) {
                matchList.add(element.getElement());
            }
        }
        if (result && matchList.size() == effect.getElements().size()) {
            relation = new 作用关系(effect, elements);

        }
        return relation;
    }

    public static List<作用关系> apply(可作用 effect, List<? extends 元素> elements) {
        List<作用关系> relations = new ArrayList<>();
        int num = effect.getElements().size();
        for (int i = 0; i < elements.size(); i++) {
            for (int j = i + 1; j < elements.size(); j++) {
                if (num > 2) {
                    for (int k = j + 1; k < elements.size(); k++) {
                        List<作用元素> eList = new ArrayList<>();
                        eList.add(new 作用元素(elements.get(i), 作用元素.位置.getByValue(i)));
                        eList.add(new 作用元素(elements.get(j), 作用元素.位置.getByValue(j)));
                        eList.add(new 作用元素(elements.get(k), 作用元素.位置.getByValue(k)));
                        作用关系 relation = match(effect, eList);
                        if (relation != null) relations.add(relation);
                    }
                } else {
                    List<作用元素> eList = new ArrayList<>();
                    eList.add(new 作用元素(elements.get(i), 作用元素.位置.getByValue(i)));
                    eList.add(new 作用元素(elements.get(j), 作用元素.位置.getByValue(j)));
                    作用关系 relation = match(effect, eList);
                    if (relation != null) {
                        relations.add(relation);
                    }
                }
            }
        }
        return relations;
    }

    public static List<作用关系> apply(List<? extends 可作用> effects, List<? extends 元素> elements) {
        List<作用关系> result = new ArrayList<>();
        for (可作用 effect : effects) {
            List<作用关系> relations = apply(effect, elements);
            for (作用关系 relation : relations) {
                boolean contains = false;
                for (作用关系 r : result) {
                    contains = contains || r.contains(relation);
                }
                if (!contains) result.add(relation);
            }
        }
        return result;
    }

    public static List<作用关系> apply(List<干支> ganzhiList) {
        List<作用关系> relations = new ArrayList<>();
        List<天干> ganList = new ArrayList<>();
        List<地支> zhiList = new ArrayList<>();
        for (干支 ganzhi : ganzhiList) {
            ganList.add(ganzhi.getGan());
            zhiList.add(ganzhi.getZhi());
        }
        relations.addAll(apply(getValues(), ganList));
        relations.addAll(apply(getValues(), zhiList));
        return relations;
    }

    public static Map<String, List<可作用>> getEffects(List<干支> ganzhiList) {
        Map<String, List<可作用>> result = new HashMap<>();
        List<作用关系> relations = apply(ganzhiList);
        for (作用关系 relation : relations) {
            List<可作用> effects = result.computeIfAbsent(
                    relation.getEffect().getElementType().name()
                            + (relation.isOriginal() ? 作用范围.本命.getName() : 作用范围.运势.getName()),
                    key -> new ArrayList<>());
            if (!effects.contains(relation.getEffect())) effects.add(relation.getEffect());
        }
        return result;
    }
}
