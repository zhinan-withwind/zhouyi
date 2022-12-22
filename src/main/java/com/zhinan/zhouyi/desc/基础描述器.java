package com.zhinan.zhouyi.desc;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.zhinan.zhouyi.common.Descriptor;
import com.zhinan.zhouyi.common.Source;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.ParameterizedType;
import java.util.HashMap;
import java.util.Map;

@Slf4j
@Data
public class 基础描述器<T> implements IDescriptor<T> {

    public final static String NAME = "名称";
    public static String lineSeparator = System.lineSeparator();

    Source source;

    private final static Map<String, HashMap<String, HashMap<String, String[]>>> registry = new HashMap<>();

    public static void register(Source source, Class<?> dClass, String key, String[] descriptions) {
        registry.computeIfAbsent(source.getName(), s -> new HashMap<>())
                .computeIfAbsent(dClass.getSimpleName(), d -> new HashMap<>())
                .put(key, descriptions);
    }

    public static Map<String, String[]> getDescriptions(Class<?> dClass) {
        return registry
                .computeIfAbsent(Source.getCurrent().getName(), s -> new HashMap<>())
                .computeIfAbsent(dClass.getSimpleName(), d -> new HashMap<>());
    }

    public static String getName(Class<?> dClass, int index) {
        String name = null;
        if (getDescriptions(dClass) != null && getDescriptions(dClass).get(NAME) != null) {
            name = getDescriptions(dClass).get(NAME)[index];
        }
        return name;
    }

    public static Class<?> getTypeClass(Object o) {
        return o.getClass();
    }

    public Class<?> getTypeClass() {
        ParameterizedType type = (ParameterizedType)this.getClass().getGenericSuperclass();
        return (Class<?>) type.getActualTypeArguments()[0];
    }

    public void register() {
        boolean isDefault = false;
        if (this.getClass().isAnnotationPresent(Descriptor.class)) {
            Descriptor descriptor = this.getClass().getAnnotation(Descriptor.class);
            this.setSource(descriptor.source());
            isDefault = descriptor.isDefault();
        }
        周易描述器.register(getSource(), getTypeClass(), this, isDefault);
        log.info(this.getSource().getName() + this.getClass().getSimpleName() + "已初始化!");
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
        String name = getName(getTypeClass(o), getValue(o));
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

    public static String describe(Object o, String type) {
        String result = "";
        if (getDescriptions(getTypeClass(o)) != null && getDescriptions(getTypeClass(o)).get(type) != null) {
            result = getDescriptions(getTypeClass(o)).get(type)[getValue(o)];
        }
        return result;
    }

    @Override
    public String describe(Object o) {
        return JSON.toJSONString(描述(o), true);
    }

    @Override
    public JSONObject 描述(Object o) {
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
