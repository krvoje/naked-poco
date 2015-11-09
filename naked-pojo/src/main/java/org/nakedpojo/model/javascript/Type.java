package org.nakedpojo.model.javascript;

public enum Type {
    BOOLEAN("false"),
    NULL("null"),
    UNDEFINED("undefined"),
    NUMBER("0"),
    STRING("\"\""),
    // SYMBOL // unused
    OBJECT("{}"),
    ARRAY("[]"),

    // Non-JavaScript, added for convenience
    FUNCTION("function(){}"),
    ENUM("{}"),
    ENUM_MEMBER("{}")
    ;

    public final String defaultValue;
    public Class containingType = null;

    Type(String defaultValue) {
        this.defaultValue = defaultValue;
    }

    public boolean isBoolean() {
        return this.equals(BOOLEAN);
    }

    public boolean isNull() {
        return this.equals(NULL);
    }

    public boolean isUndefined() {
        return this.equals(UNDEFINED);
    }

    public boolean isNumber() {
        return this.equals(NUMBER);
    }

    public boolean isString() {
        return this.equals(STRING);
    }

    public boolean isObject() {
        return this.equals(OBJECT);
    }

    public boolean isArray() {
        return this.equals(ARRAY);
    }

    public boolean isFunction() {
        return this.equals(FUNCTION);
    }

    public boolean isEnum() {
        return this.equals(ENUM);
    }

    public boolean isEnumMember() {
        return this.equals(ENUM_MEMBER);
    }

}
