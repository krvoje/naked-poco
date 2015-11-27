package org.nakedpojo.utils;

public class Commons {

    public static final String GET_PREFIX = "get";
    public static final String SET_PREFIX = "set";
    public static final String IS_PREFIX = "is";

    public static <T> boolean nullOrEmpty(T[] objs) {
        return objs==null || objs.length ==0;
    }
    public static boolean nullOrEmpty(String string) {
        return string == null || string.isEmpty();
    }

    public static boolean equalsEither(Object self, Object ... objs) {
        if(nullOrEmpty(objs)) return true;
        for(Object o: objs) {
            if(self.equals(o))
                return true;
        }
        return false;
    }

    public static boolean equalsAll(Object self, Object ... objs) {
        if(nullOrEmpty(objs)) return true;
        for(Object o: objs) {
            if(!self.equals(o))
                return false;
        }
        return true;
    }

    public static boolean either(Boolean ... exprs) {
        for(Boolean expr: exprs) {
            if(expr) return true;
        }
        return false;
    }

    public static String lowercaseFirst(String string) {
        // TODO: locale
        if(nullOrEmpty(string) || string.length() == 1) return string;
        return string.toLowerCase().charAt(0) + string.substring(1);
    }

    public static <T> boolean contains(T[] coll, T a) {
        if(nullOrEmpty(coll)) return false;
        for(T t: coll) {
            if(t.equals(a)) return true;
        }
        return false;
    }

    public static String fieldNameFromGetterOrSetter(String methodName) {
        if(methodName.startsWith(GET_PREFIX)) {
            return lowercaseFirst(methodName.substring(GET_PREFIX.length()));
        } else if(methodName.startsWith(IS_PREFIX)) {
            return lowercaseFirst(methodName.substring(IS_PREFIX.length()));
        } else if(methodName.startsWith(SET_PREFIX)) {
            return lowercaseFirst(methodName.substring(SET_PREFIX.length()));
        } else {
            return methodName;
        }
    }

    public static boolean isGetterName(String name) {
        return (name.startsWith(GET_PREFIX) || name.startsWith(IS_PREFIX))
                && !name.equals(GET_PREFIX) && !name.equals(IS_PREFIX);
    }

    public static boolean isSetterName(String name) {
        return name.startsWith(SET_PREFIX)
                && !name.equals(SET_PREFIX);
    }

    public static <T> T last(T[] ts) {
        if(nullOrEmpty(ts)) return null;
        else return ts[ts.length-1];
    }
}
