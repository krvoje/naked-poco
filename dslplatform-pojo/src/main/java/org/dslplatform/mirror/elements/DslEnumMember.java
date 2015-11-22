package org.dslplatform.mirror.elements;

import org.dslplatform.mirror.types.DslPrimitive;

public class DslEnumMember extends DslField {

    public DslEnumMember(String name) {
        super(name, DslPrimitive.ENUM_MEMBER);
    }

    public String getKeyword() {
        return this.name;
    }

    public DslEnumMember withName(String name) {
        return new DslEnumMember(name);
    }
}
