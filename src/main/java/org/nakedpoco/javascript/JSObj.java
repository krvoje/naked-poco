package org.nakedpoco.javascript;

import static org.nakedpoco.utils.Utils.nullOrEmpty;

public class JSObj {
    public final String name;
    public final Type type;

    public JSObj[] members;

    public JSObj(String name, Type type, JSObj... members) {
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
            for (JSObj member : members) {
                sb.append(member.toString());
            }
            sb.append("}");
        }
        sb.append("}");
        return sb.toString();
    }
}
