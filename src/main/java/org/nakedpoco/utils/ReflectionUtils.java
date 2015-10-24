package org.nakedpoco.utils;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

import static org.nakedpoco.utils.Utils.lowercaseFirst;

public class ReflectionUtils {

    private static final String GET = "get";
    private static final String SET = "set";
    private static final String IS = "is";

    public static Field[] publicFields(Class clazz) {
        Field[] fields = clazz.getFields();
        List<Field> fs = new ArrayList<Field>();
        for(Field field: fields) {
            fs.add(field);
        }
        return fs.toArray(new Field[fs.size()]);
    }

    public static Method[] getters(Class clazz) {
        List<Method> getters = new ArrayList<Method>();
        for(Method method: clazz.getDeclaredMethods()) {
            if(isGetter(method))
                getters.add(method);
        }
        return getters.toArray(new Method[getters.size()]);
    }

    public static Method[] setters(Class clazz) {
        List<Method> setters = new ArrayList<Method>();
        for(Method method: clazz.getDeclaredMethods()) {
            if(isSetter(method))
                setters.add(method);
        }
        return setters.toArray(new Method[setters.size()]);
    }

    public static String fieldName(Method getterOrSetter) {
        String name = getterOrSetter.getName();
        if(name.startsWith(GET)) {
            return lowercaseFirst(name.substring(GET.length()));
        } else if(name.startsWith(IS)) {
            return lowercaseFirst(name.substring(IS.length()));
        } else if(name.startsWith(SET)) {
            return lowercaseFirst(name.substring(SET.length()));
        } else {
            return name;
        }
    }

    public static boolean isGetter(Method method) {
        String name = method.getName();
        return (name.startsWith(GET) || name.startsWith(IS))
                && !method.getReturnType().equals(Void.class)
                && !name.equals(GET) && !name.equals(IS);
    }

    public static boolean isSetter(Method method) {
        String name = method.getName();
        return name.startsWith(SET)
                && method.getReturnType().equals(Void.class)
                && !name.equals(SET);
    }
}
