package org.dslplatform.mirror.types;

import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.UUID;

public enum DslPrimitive implements DslType {

    // TODO: Arrayevi, liste, parametrizirani tipovi

    STRING("string", String.class, Character.class, char.class)
    , INTEGER("int", Integer.class, int.class)
    , LONG("long", Long.class, long.class)
    , FLOAT("float", Float.class, float.class)
    , DOUBLE("double", Double.class, double.class)
    , BOOLEAN("bool", Boolean.class, boolean.class)
    , MAP("map", Map.class)
    , BINARY("binary", byte[].class, Byte[].class)
    , DATE("date", java.util.Date.class) // TODO: joda as well
    , MONEY("money") // TODO:
    , GUID("guid", UUID.class) // TODO:
    , XML("xml")
    , LOCATION("location")
    , POINT("location")
    ;

    private final String keyword;

    // Fully qualified names of Classes that map to this DslPrimitive
    private Set<String> domain = new HashSet<String>();

    private DslPrimitive(String keyword, Class... javaClasses) {
        this.keyword = keyword;
        for(Class clazz: javaClasses) {
            this.domain.add(clazz.getCanonicalName());
        }
    }

    public String getKeyword() {
        return keyword;
    }
}
