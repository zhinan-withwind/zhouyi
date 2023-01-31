package com.zhinan.zhouyi.desc;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import lombok.Data;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.ParameterizedType;
import java.util.HashMap;
import java.util.Map;

@Slf4j
@Data
public class BaseDescriptor<T> implements IDescriptor<T> {
    public final static String NAME = "名称";
    public static String lineSeparator = System.lineSeparator();

    private final static Map<String, HashMap<String, String[]>> registry = new HashMap<>();

    public static void register(Class<?> tClass, String key, String[] descriptions) {
        registry.computeIfAbsent(tClass.getSimpleName(), d -> new HashMap<>()).put(key, descriptions);
    }

    public static Map<String, String[]> getDescriptions(Class<?> tClass) {
        return registry
                .computeIfAbsent(tClass.getSimpleName(), t -> new HashMap<>());
    }

    public static Class<?> getTypeClass(Object o) {
        return o.getClass();
    }

    public Class<?> getTypeClass() {
        ParameterizedType type = (ParameterizedType)this.getClass().getGenericSuperclass();
        return (Class<?>) type.getActualTypeArguments()[0];
    }

    public void register() {
        ZhouYiDescriptor.register(getTypeClass(), this);
        log.info(this.getClass().getSimpleName() + "已初始化!");
    }

    public static int getValue(Object o) {
        int value = 0;
        try {
            Method method = o.getClass().getMethod("getValue");
            value = (Integer) method.invoke(o);
        } catch (NoSuchMethodException | InvocationTargetException | IllegalAccessException e) {
            log.error(e.getLocalizedMessage());
        }
        return value;
    }

    public String getName(Object o) {
        String name = null;
        Class<?> tClass = getTypeClass(o);
        if (getDescriptions(tClass) != null && getDescriptions(tClass).get(NAME) != null) {
            name = getDescriptions(tClass).get(NAME)[getValue(o)];
        }
        if (name == null) {
            try {
                Method method = o.getClass().getMethod("getName");
                name = (String) method.invoke(o);
            } catch (NoSuchMethodException | InvocationTargetException | IllegalAccessException e) {
                log.error(e.getLocalizedMessage());
            }
        }
        return name;
    }

    public String describe(Object o, String type) {
        String result = "";
        if (getDescriptions(getTypeClass(o)) != null && getDescriptions(getTypeClass(o)).get(type) != null) {
            result = getDescriptions(getTypeClass(o)).get(type)[getValue(o)];
        }
        return result;
    }

    @Override
    public String describe(Object o) {
        return JSON.toJSONString(fullDescribe(o), true);
    }

    @Override
    public JSONObject fullDescribe(Object o) {
        JSONObject result = new JSONObject().fluentPut(NAME, getName(o));
        Map<String, String[]> descriptions = getDescriptions(getTypeClass());
        for (String type : descriptions.keySet()) {
            if (!type.equals(NAME)) {
                result.put(type, descriptions.get(type)[getValue(o)]);
            }
        }
        return result;
    }

}
