package org.dslplatform.mirror.elements;

public abstract class ModuleElement extends DslPlatformElement  {
    public final DslField[] fields;
    public ModuleElement(String name, DslField ... fields) {
        super(name);
        this.fields = fields;
    }

    public abstract <T extends ModuleElement> T withFields(DslField[] fields);
}
