package org.dslplatform.mirror.elements;

import java.util.List;
import static java.util.Arrays.asList;

public class DslEnum extends ModuleElement  {

    public DslEnum(String name, List<DslField> fields) {
        super(name, fields);
    }

    @Override
    public String getKeyword() {
        return "enum";
    }

    @Override
    public DslPlatformElement withName(String name) {
        return new DslEnum(name, fields);
    }

    @Override
    public DslEnum withFields(List<DslField> fields) {
        return new DslEnum(name, fields);
    }
}
