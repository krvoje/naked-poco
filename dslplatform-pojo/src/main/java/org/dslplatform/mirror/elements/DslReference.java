package org.dslplatform.mirror.elements;

import org.dslplatform.mirror.types.DslPrimitive;

public class DslReference extends DslField {

    private final ModuleElement referenced;

    public DslReference(
            String name,
            DslPrimitive type,
            ModuleElement referenced) {
        super(name, type);
        this.referenced = referenced;
    }

    public ModuleElement getReferenced() {
        return referenced;
    }

    @Override
    public String getKeyword() {
        return referenced.getKeyword();
    }

    @Override
    public <T extends DslField> T withType(DslPrimitive type) {
        return null;
    }

    @Override
    public <T extends DslPlatformElement> T withName(String name) {
        return null;
    }
}
