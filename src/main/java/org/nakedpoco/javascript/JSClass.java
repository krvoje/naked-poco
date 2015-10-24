package org.nakedpoco.javascript;

public class JSClass {
    public final String typeName;
    public final String fieldName;
    public final Type type;

    public JSClass[] members;

    public JSClass(String typeName, String fieldName, Type type, JSClass... members) {
        this.typeName = typeName;
        this.fieldName = fieldName;
        this.type = type;
        this.members = members;
    }

    public JSClass(JSClass copyMe, String fieldName) {
        this.typeName = copyMe.typeName;
        this.fieldName = fieldName;
        this.type = copyMe.type;
        this.members = copyMe.members;
    }
}
