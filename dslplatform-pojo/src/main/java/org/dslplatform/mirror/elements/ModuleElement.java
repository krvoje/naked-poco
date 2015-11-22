package org.dslplatform.mirror.elements;

import java.util.List;

public abstract class ModuleElement extends DslPlatformElement  {
    public final List<DslField> fields;

    public ModuleElement(String name, List<DslField> fields) {
        super(name);
        this.fields = fields;
    }

    public abstract ModuleElement withFields(List<DslField> fields);
}
