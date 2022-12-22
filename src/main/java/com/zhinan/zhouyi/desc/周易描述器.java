package com.zhinan.zhouyi.desc;

import com.alibaba.fastjson.JSONObject;
import com.zhinan.zhouyi.common.Source;
import com.zhinan.zhouyi.util.ClassUtil;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Slf4j
public class 周易描述器 {
    private final static Map<String, IDescriptor<?>> registry = new HashMap<>();

    public static void register(Source source, Class<?> clazz, IDescriptor<?> descriptor, boolean isDefault) {
        registry.put(buildKey(source, clazz), descriptor);
        if (isDefault) {
            registry.put(buildKey(Source.通用, clazz), descriptor);
        }
    }

    public static IDescriptor<?> getDescriptor(Source source, Class<?> clazz) {
        IDescriptor<?> descriptor = registry.get(buildKey(source, clazz));
        if (descriptor == null && !clazz.getSuperclass().equals(Object.class)) {
            descriptor = getDescriptor(source, clazz.getSuperclass());
        }
        if (descriptor == null) {
            descriptor = getDescriptor(Source.通用, clazz);
        }
        return descriptor;
    }

    private static String buildKey(Source source, Class<?> tClass) {
        return source.getName() + "_" + tClass.getSimpleName();
    }

    @SneakyThrows
    public static void autoInit() {
        List<Class<?>> classes = ClassUtil.listClassesByPackage(周易描述器.class.getPackage().getName());
        for (Class<?> aClass : classes) {
            if (IDescriptor.class.isAssignableFrom(aClass) && !aClass.equals(基础描述器.class) && !aClass.isInterface()) {
                ((IDescriptor<?>) aClass.newInstance()).register();
            }
        }
    }

    public static void init() {
        new com.zhinan.zhouyi.desc.mingyi.命主描述器().register();
        new com.zhinan.zhouyi.desc.mingyi.命局描述器().register();
        new com.zhinan.zhouyi.desc.mingyi.运势描述器().register();
        new com.zhinan.zhouyi.desc.mingyi.十神描述器().register();
        new com.zhinan.zhouyi.desc.mingyi.八字描述器().register();

        new com.zhinan.zhouyi.desc.xinghe.命主描述器().register();
        new com.zhinan.zhouyi.desc.xinghe.命局描述器().register();
        new com.zhinan.zhouyi.desc.xinghe.运势描述器().register();
        new com.zhinan.zhouyi.desc.xinghe.十神描述器().register();

        new com.zhinan.zhouyi.desc.fate.能量描述器().register();
        new com.zhinan.zhouyi.desc.fate.合婚描述器().register();
        new com.zhinan.zhouyi.desc.fate.财运描述器().register();
        new com.zhinan.zhouyi.desc.fate.健康描述器().register();

        new com.zhinan.zhouyi.desc.divine.称骨描述器().register();
    }

    public static String getName(Object o) {
        return getDescriptor(Source.getCurrent(), o.getClass()).getName(o);
    }

    public static String describe(Object o) {
        return getDescriptor(Source.getCurrent(), o.getClass()).describe(o);
    }

    public static JSONObject 描述(Object o) {
        return getDescriptor(Source.getCurrent(), o.getClass()).描述(o);
    }
}
