package org.dslplatform.mirror.elements;

public class AggregateRoot extends ModuleElement  {

    public AggregateRoot(String name, DslField ... fields) {
        super(name);
    }

    @Override
    public String getKeyword() {
        return "root";
    }

    @Override
    public DslPlatformElement withName(String name) {
        return new AggregateRoot(name, fields);
    }

    @Override
    public AggregateRoot withFields(DslField[] fields) {
        return new AggregateRoot(name, fields);
    }
}
