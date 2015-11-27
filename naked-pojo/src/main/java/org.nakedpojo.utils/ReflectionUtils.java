package org.nakedpojo.utils;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

import static org.nakedpojo.utils.Commons.equalsEither;

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
        return Commons.fieldNameFromGetterOrSetter(name);
    }

    public static boolean isIterable(Class clazz) {
        return clazz.isArray() || isSubclassOf(clazz, Iterable.class);
    }

    public static boolean isPrimitive(Class clazz) {
        return clazz.isPrimitive()
            || equalsEitherCanonicalName(clazz, String.class);
    }

    public static boolean isString(Class clazz) {
        return equalsEitherCanonicalName(clazz,
                String.class,
                Character.class,
                char.class);
    }

    public static boolean isBoolean(Class clazz) {
        return equalsEitherCanonicalName(clazz,
                Boolean.class,
                boolean.class);
    }

    public static boolean isByte(Class clazz) {
        return equalsEitherCanonicalName(clazz,
                Byte.class,
                byte.class);
    }

    public static boolean isNumeric(Class clazz) {
        return equalsEitherCanonicalName(clazz,
                java.lang.Short.class,
                short.class,
                java.lang.Integer.class,
                int.class,
                java.lang.Long.class,
                long.class,
                java.lang.Float.class,
                float.class,
                java.lang.Double.class,
                double.class);
    }

    public static boolean isGetter(Method method) {
        String name = method.getName();
        return Commons.isGetterName(name)
                && !equalsEither(method.getReturnType(), Void.class, void.class);
    }

    public static boolean isSetter(Method method) {
        String name = method.getName();
        return Commons.isSetterName(name)
                && equalsEither(method.getReturnType(), Void.class, void.class)
                && method.getParameterTypes().length == 1;
    }

    public static boolean isSubclassOf(Class clazz, Class superClazz) {
        /// XXX: Incomplete, works just for internal implementation
        if(superClazz.isInterface()) {
            if(Commons.contains(clazz.getInterfaces(), superClazz)) return true;
            for(Class _interface: clazz.getInterfaces()) {
                return isSubclassOf(_interface, superClazz);
            }
        }

        if(clazz.getSuperclass() == null) return false;
        if(clazz.isAssignableFrom(superClazz)) return true;
        return isSubclassOf(clazz.getSuperclass(), superClazz);
    }

    public static boolean equalsEitherCanonicalName(Class clazz, Class ... clazzez) {
        if(Commons.nullOrEmpty(clazzez)) return true;
        for(Class c: clazzez) {
            if(clazz.getCanonicalName().equals(c.getCanonicalName())) {
                return true;
            }
        }
        return false;
    }
}
