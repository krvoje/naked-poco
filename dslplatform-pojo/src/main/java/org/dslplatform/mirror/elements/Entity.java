package org.dslplatform.mirror.elements;

public class Entity extends ModuleElement  {

    public Entity(String name, DslField... members) {
        super(name);
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
    public Entity withFields(DslField[] fields) {
        return new Entity(name, fields);
    }
}
