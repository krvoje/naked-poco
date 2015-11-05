package org.nakedpojo.javascript;

import java.util.*;

public class JSType {
    public final String typeName;
    public final String fieldName;
    public final Type type;

    public final JSType[] members;

    public JSType(String typeName) {
        this(typeName, typeName, Type.UNDEFINED);
    }

    public JSType(String typeName, Type type) {
        this(typeName, typeName, Type.UNDEFINED);
    }

    public JSType(String typeName, String fieldName) {
        this(typeName, fieldName, Type.UNDEFINED);
    }

    public JSType(String fieldName, Type type, JSType... members) {
        this(fieldName, fieldName, type, members);
    }

    public JSType(String typeName, String fieldName, Type type, JSType... members) {
        this.typeName = typeName;
        this.fieldName = fieldName;
        this.type = type;
        this.members = members;
    }

    public JSType withName(String fieldName) {
        return new JSType(fieldName, type, members);
    }

    public JSType withType(Type type) {
        return new JSType(fieldName, type, members);
    }

    public JSType withMembers(JSType[] members) {
        return new JSType(fieldName, type, members);
    }

    public JSType withMembers(Collection<JSType> members) {
        return new JSType(fieldName, type, members.toArray(new JSType[members.size()]));
    }
}
