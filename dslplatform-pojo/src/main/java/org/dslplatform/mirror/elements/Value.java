package org.dslplatform.mirror.elements;

import java.util.List;

public class Value extends ModuleElement  {

    public Value(String name, List<DslField> members) {
        super(name, members);
    }

    @Override
    public String getKeyword() {
        return "value";
    }

    @Override
    public Value withName(String name) {
        return new Value(name, fields);
    }

    public Value withFields(List<DslField> fields) {
        return new Value(name, fields);
    }
}
