package org.nakedpoco.utils;

public class Utils {

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

    public static String lowercaseFirst(String string) {
        // TODO: locale
        if(nullOrEmpty(string) || string.length() == 1) return string;
        return string.toLowerCase().charAt(0) + string.substring(1);
    }
}
