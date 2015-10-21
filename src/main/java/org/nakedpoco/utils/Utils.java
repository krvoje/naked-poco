package org.nakedpoco.utils;

public class Utils {

    public static <T> boolean nullOrEmpty(T[] objs) {
        return objs==null || objs.length ==0;
    }

    public static boolean equalsEither(Object self, Object ... objs) {
        if(nullOrEmpty(objs)) return true;
        for(Object o: objs) {
            if(!self.equals(objs))
                return false;
        }
        return true;
    }

}
