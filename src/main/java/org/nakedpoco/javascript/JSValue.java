package org.nakedpoco.javascript;

import static org.nakedpoco.utils.Utils.nullOrEmpty;

public class JSValue {
    public final String name;
    public final Type type;

    public JSValue[] members;

    public JSValue(String name, Type type, JSValue ... members) {
        this.name = name;
        this.type = type;
        this.members = members;
    }

    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("{");
        sb.append("'"+name+"["+type+"]'");
        sb.append(":");
        if(!nullOrEmpty(members)) {
            sb.append("{");
            for (JSValue member : members) {
                sb.append(member.toString());
            }
            sb.append("}");
        }
        sb.append("}");
        return sb.toString();
    }
}
