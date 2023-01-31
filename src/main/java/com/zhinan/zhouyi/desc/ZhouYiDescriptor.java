package com.zhinan.zhouyi.desc;

import com.alibaba.fastjson.JSONObject;
import com.zhinan.zhouyi.desc.divine.卦辞描述器;
import com.zhinan.zhouyi.desc.divine.称骨描述器;
import com.zhinan.zhouyi.desc.fate.*;
import com.zhinan.zhouyi.util.ClassUtil;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Slf4j
public class ZhouYiDescriptor {
    private final static Map<String, IDescriptor<?>> registry = new HashMap<>();

    public static void register(Class<?> tClass, IDescriptor<?> descriptor) {
        registry.put(tClass.getSimpleName(), descriptor);
    }

    public static IDescriptor<?> getDescriptor(Class<?> tClass) {
        IDescriptor<?> descriptor = registry.get(tClass.getSimpleName());
        if (descriptor == null && !tClass.getSuperclass().equals(Object.class)) {
            descriptor = getDescriptor(tClass.getSuperclass());
        }
        return descriptor;
    }

    @SneakyThrows
    public static void autoInit() {
        List<Class<?>> classes = ClassUtil.listClassesByPackage(ZhouYiDescriptor.class.getPackage().getName());
        for (Class<?> aClass : classes) {
            if (IDescriptor.class.isAssignableFrom(aClass) && !aClass.equals(BaseDescriptor.class) && !aClass.isInterface()) {
                ((IDescriptor<?>) aClass.newInstance()).register();
            }
        }
    }

    public static void init() {
        new 命主描述器().register();
        new 命局描述器().register();
        new 运势描述器().register();
        new 十神描述器().register();
        new 八字描述器().register();

        new 能量描述器().register();
        new 合婚描述器().register();
        new 财运描述器().register();
        new 健康描述器().register();

        new 卦辞描述器().register();
        new 称骨描述器().register();
    }

    public static String getName(Object o) {
        return getDescriptor(o.getClass()).getName(o);
    }

    public static String describe(Object o) {
        return getDescriptor(o.getClass()).describe(o);
    }

    public static String describe(Object o, String type) {
        return getDescriptor(o.getClass()).describe(o, type);
    }

    public static JSONObject fullDescribe(Object o) {
        return getDescriptor(o.getClass()).fullDescribe(o);
    }
}
