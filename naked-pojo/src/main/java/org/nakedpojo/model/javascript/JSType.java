package org.nakedpojo.model.javascript;

import java.util.*;

import static java.util.Arrays.asList;

public class JSType {
    private final String typeName;
    private final String fieldName;
    private final Type type;
    private final Set<JSType> members = new TreeSet<>(new Comparator<JSType>() {
        @Override
        public int compare(JSType t1, JSType t2) {
            return t1.fieldName.compareTo(t2.fieldName);
        }
    });

    public JSType(String typeName, String fieldName, Type type, JSType ... members) {
        this.typeName = typeName;
        this.fieldName = fieldName;
        this.type = type;
        this.members.addAll(asList(members));
    }

    public JSType(String typeName, String fieldName, Type type, Collection<JSType> members) {
        this(typeName, fieldName, type, members.toArray(new JSType[members.size()]));
    }

    public JSType withTypeName(String typeName) {
        return new JSType(typeName, fieldName, type, members);
    }

    public JSType withFieldName(String fieldName) {
        return new JSType(typeName, fieldName, type, members);
    }

    public JSType withType(Type type) {
        return new JSType(typeName, fieldName, type, members);
    }

    public JSType withMembers(JSType[] members) {
        return new JSType(typeName, fieldName, type, asList(members));
    }

    public JSType withMembers(Collection<JSType> members) {
        return new JSType(typeName, fieldName, type, members);
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

    public Set<JSType> getMembers() {
        return this.members;
    }
}
