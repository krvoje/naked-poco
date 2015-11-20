package org.dslplatform.mirror.elements;

public class Module extends DslPlatformElement {

    public final ModuleElement[] children;

    public Module(String name, ModuleElement ... elements) {
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

    public Module withChildren(ModuleElement[] children) {
        return new Module(name, children);
    }
}
