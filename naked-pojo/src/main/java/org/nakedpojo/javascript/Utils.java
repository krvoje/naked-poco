package org.nakedpojo.javascript;

class Utils {

    private static final String GET = "get";
    private static final String SET = "set";
    private static final String IS = "is";

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

    static String fieldNameFromGetterOrSetter(String methodName) {
        if(methodName.startsWith(GET)) {
            return lowercaseFirst(methodName.substring(GET.length()));
        } else if(methodName.startsWith(IS)) {
            return lowercaseFirst(methodName.substring(IS.length()));
        } else if(methodName.startsWith(SET)) {
            return lowercaseFirst(methodName.substring(SET.length()));
        } else {
            return methodName;
        }
    }

    static boolean isGetterName(String name) {
        return (name.startsWith(GET) || name.startsWith(IS))
                && !name.equals(GET) && !name.equals(IS);
    }

    static boolean isSetterName(String name) {
        return name.startsWith(SET)
                && !name.equals(SET);
    }
}
