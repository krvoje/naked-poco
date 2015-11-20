package org.dslplatform.mirror.elements;

public abstract class DslPlatformElement {
    public final String name;

    public DslPlatformElement(String name) {
        this.name = name;
    }

    public String getName() {
        return this.name;
    }

    public abstract String getKeyword();
    public abstract <T extends DslPlatformElement> T withName(String name);

}
