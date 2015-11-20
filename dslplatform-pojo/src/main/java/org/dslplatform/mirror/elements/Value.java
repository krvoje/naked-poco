package org.dslplatform.mirror.elements;

public class Value extends ModuleElement  {

    public Value(String name, DslField... members) {
        super(name);
    }

    @Override
    public String getKeyword() {
        return "value";
    }

    @Override
    public Value withName(String name) {
        return new Value(name, fields);
    }

    public Value withFields(DslField[] fields) {
        return new Value(name, fields);
    }
}
