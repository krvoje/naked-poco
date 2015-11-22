package org.dslplatform.mirror.elements;

import java.util.List;

import static java.util.Arrays.asList;

public class AggregateRoot extends ModuleElement  {

    public AggregateRoot(String name, List<DslField> fields) {
        super(name, fields);
    }

    @Override
    public AggregateRoot withFields(List<DslField> fields) {
        return new AggregateRoot(name, fields);
    }

    public AggregateRoot(String name, DslField ... fields) {
        this(name, asList(fields));
    }

    @Override
    public String getKeyword() {
        return "root";
    }

    @Override
    public DslPlatformElement withName(String name) {
        return new AggregateRoot(name, fields);
    }
}
