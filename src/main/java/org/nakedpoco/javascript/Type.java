package org.nakedpoco.javascript;

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
    ENUM("function(){}")
    ;

    public final String defaultValue;
    public Class containingType = null;

    Type(String defaultValue) {
        this.defaultValue = defaultValue;
    }

    public void containingType(Class clazz) {
        this.containingType = containingType;
    }

    public boolean isArray() {
        return this.equals(ARRAY);
    }

    public boolean isEnum() {
        return this.equals(ENUM);
    }
}
