package org.dslplatform.mirror.elements;

import org.dslplatform.mirror.types.DslPrimitive;

public class DslField extends DslPlatformElement {
    public final DslPrimitive type;

    public DslField(String name, DslPrimitive type) {
        super(name);
        this.type = type;
    }

    public DslField withType(DslPrimitive type) {
        return new DslField(name, type);
    }

    public String getKeyword() {
        return this.type.getKeyword();
    }

    public DslField withName(String name) {
        return new DslField(name, type);
    }
}
