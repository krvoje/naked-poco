package org.nakedpojo.model.javascript;

import java.util.*;

public class JSType {
    private final String typeName;
    private final String fieldName;
    private final Type type;
    private final JSType[] members;

    public JSType(String typeName, String fieldName, Type type, JSType... members) {
        this.typeName = typeName;
        this.fieldName = fieldName;
        this.type = type;
        this.members = members;
    }

    public JSType(String typeName) {
        this(typeName, typeName, Type.UNDEFINED);
    }

    public JSType(String typeName, String fieldName) {
        this(typeName, fieldName, Type.UNDEFINED);
    }

    public JSType(String typeName, Type type) {
        this(typeName, typeName, type);
    }

    public JSType(String fieldName, Type type, JSType... members) {
        this(fieldName, fieldName, type, members);
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

    public String getTypeName() {
        return this.typeName;
    }

    public String getFieldName() {
        return this.fieldName;
    }

    public Type getType() {
        return this.type;
    }

    public JSType[] getMembers() {
        return this.members;
    }
}
