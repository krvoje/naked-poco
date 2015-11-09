package org.nakedpojo.utils;

import static org.nakedpojo.utils.Commons.*;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

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
        return fieldNameFromGetterOrSetter(name);
    }

    public static boolean isNumeric(Class class) {

    }

    public static boolean isGetter(Method method) {
        String name = method.getName();
        return isGetterName(name)
                && !method.getReturnType().equals(Void.class);
    }

    public static boolean isSetter(Method method) {
        String name = method.getName();
        return isSetterName(name)
                && method.getReturnType().equals(Void.class);
    }

    public static boolean isSubclassOf(Class clazz, Class superClazz) {
        /// XXX: Incomplete, works just for internal implementation
        if(superClazz.isInterface()) {
            if(contains(clazz.getInterfaces(), superClazz)) return true;
            for(Class _interface: clazz.getInterfaces()) {
                return isSubclassOf(_interface, superClazz);
            }
        }

        if(clazz.getSuperclass() == null) return false;
        if(clazz.isAssignableFrom(superClazz)) return true;
        return isSubclassOf(clazz.getSuperclass(), superClazz);
    }
}
