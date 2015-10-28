package org.nakedpojo.javascript;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

class Utils {

    private static final String GET = "get";
    private static final String SET = "set";
    private static final String IS = "is";

    static Field[] publicFields(Class clazz) {
        Field[] fields = clazz.getFields();
        List<Field> fs = new ArrayList<Field>();
        for(Field field: fields) {
            fs.add(field);
        }
        return fs.toArray(new Field[fs.size()]);
    }

    static Method[] getters(Class clazz) {
        List<Method> getters = new ArrayList<Method>();
        for(Method method: clazz.getDeclaredMethods()) {
            if(isGetter(method))
                getters.add(method);
        }
        return getters.toArray(new Method[getters.size()]);
    }

    static Method[] setters(Class clazz) {
        List<Method> setters = new ArrayList<Method>();
        for(Method method: clazz.getDeclaredMethods()) {
            if(isSetter(method))
                setters.add(method);
        }
        return setters.toArray(new Method[setters.size()]);
    }

    static String fieldName(Method getterOrSetter) {
        String name = getterOrSetter.getName();
        if(name.startsWith(GET)) {
            return Utils.lowercaseFirst(name.substring(GET.length()));
        } else if(name.startsWith(IS)) {
            return Utils.lowercaseFirst(name.substring(IS.length()));
        } else if(name.startsWith(SET)) {
            return Utils.lowercaseFirst(name.substring(SET.length()));
        } else {
            return name;
        }
    }

    static boolean isGetter(Method method) {
        String name = method.getName();
        return (name.startsWith(GET) || name.startsWith(IS))
                && !method.getReturnType().equals(Void.class)
                && !name.equals(GET) && !name.equals(IS);
    }

    static boolean isSetter(Method method) {
        String name = method.getName();
        return name.startsWith(SET)
                && method.getReturnType().equals(Void.class)
                && !name.equals(SET);
    }

    static boolean isSubclassOf(Class clazz, Class superClazz) {
        /// XXX: Incomplete, works just for internal implementation
        if(superClazz.isInterface()) {
            if(Utils.contains(clazz.getInterfaces(), superClazz)) return true;
            for(Class _interface: clazz.getInterfaces()) {
                return isSubclassOf(_interface, superClazz);
            }
        }

        if(clazz.getSuperclass() == null) return false;
        if(clazz.isAssignableFrom(superClazz)) return true;
        return isSubclassOf(clazz.getSuperclass(), superClazz);
    }

    static <T> boolean nullOrEmpty(T[] objs) {
        return objs==null || objs.length ==0;
    }
    static boolean nullOrEmpty(String string) {
        return string == null || string.isEmpty();
    }

    static boolean equalsEither(Object self, Object ... objs) {
        if(nullOrEmpty(objs)) return true;
        for(Object o: objs) {
            if(self.equals(o))
                return true;
        }
        return false;
    }

    static boolean equalsAll(Object self, Object ... objs) {
        if(nullOrEmpty(objs)) return true;
        for(Object o: objs) {
            if(!self.equals(o))
                return false;
        }
        return true;
    }

    static boolean either(Boolean ... exprs) {
        for(Boolean expr: exprs) {
            if(expr) return true;
        }
        return false;
    }

    static String lowercaseFirst(String string) {
        // TODO: locale
        if(nullOrEmpty(string) || string.length() == 1) return string;
        return string.toLowerCase().charAt(0) + string.substring(1);
    }

    static <T> boolean contains(T[] coll, T a) {
        if(nullOrEmpty(coll)) return false;
        for(T t: coll) {
            if(t.equals(a)) return true;
        }
        return false;
    }
}
