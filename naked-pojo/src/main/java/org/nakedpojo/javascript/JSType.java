package org.nakedpojo.javascript;

import java.util.*;

public class JSType {
    public final String typeName;
    public final String name;
    public final Type type;
    public final Class javaType;

    public final JSType[] members;

    public JSType(Class javaType) {
        this(javaType, javaType.getSimpleName().toString(), Type.UNDEFINED);
    }

    public JSType(Class javaType, String name) {
        this(javaType, name, Type.UNDEFINED);
    }

    public JSType(Class javaType, String name, Type type, JSType... members) {
        this.javaType = javaType;
        this.typeName = javaType.getSimpleName().toString();
        this.name = name;
        this.type = type;
        this.members = members;
    }

    public JSType withName(String fieldName) {
        return new JSType(javaType, fieldName, type, members);
    }

    public JSType withType(Type type) {
        return new JSType(javaType, name, type, members);
    }

    public JSType withMembers(JSType[] members) {
        return new JSType(javaType, name, type, members);
    }

    public JSType withMembers(Collection<JSType> members) {
        return new JSType(javaType, name, type, members.toArray(new JSType[members.size()]));
    }
}
