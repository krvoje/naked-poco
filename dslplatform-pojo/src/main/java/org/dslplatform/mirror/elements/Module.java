package org.dslplatform.mirror.elements;

import java.util.List;

public class Module extends DslPlatformElement {

    public final List<ModuleElement> children;

    public Module(String name, List<ModuleElement> elements ) {
        super(name);
        this.children = elements;
    }

    @Override
    public String getKeyword() {
        return "module";
    }

    @Override
    public DslPlatformElement withName(String name) {
        return new Module(name, children);
    }

    public Module withChildren(List<ModuleElement> children) {
        return new Module(name, children);
    }
}
