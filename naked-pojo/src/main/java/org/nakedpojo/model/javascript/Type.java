package org.nakedpojo.model.javascript;

public enum Type {
    BOOLEAN,
    NULL,
    UNDEFINED,
    NUMBER,
    STRING,
    // SYMBOL // unused
    OBJECT,
    ARRAY,

    // Non-JavaScript, added for convenience
    FUNCTION,
    ENUM,
    ENUM_MEMBER
    ;
    
    public Class containingType = null;

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
