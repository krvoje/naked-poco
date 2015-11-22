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
    public DslReference withType(DslPrimitive type) {
        return new DslReference(name, type, referenced);
    }

    @Override
    public DslReference withName(String name) {
        return new DslReference(name, type, referenced);
    }

    public DslReference withReferenced(ModuleElement referenced) {
        return new DslReference(name, type, referenced);
    }
}
