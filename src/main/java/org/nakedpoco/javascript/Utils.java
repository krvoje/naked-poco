package org.nakedpoco.javascript;

class Utils {

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
