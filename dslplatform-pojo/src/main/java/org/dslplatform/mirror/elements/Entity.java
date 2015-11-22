package org.dslplatform.mirror.elements;

import java.util.List;

public class Entity extends ModuleElement  {

    public Entity(String name, List<DslField> members) {
        super(name, members);
    }

    @Override
    public String getKeyword() {
        return "entity";
    }

    @Override
    public Entity withName(String name) {
        return new Entity(name, fields);
    }

    @Override
    public Entity withFields(List<DslField> fields) {
        return new Entity(name, fields);
    }
}
