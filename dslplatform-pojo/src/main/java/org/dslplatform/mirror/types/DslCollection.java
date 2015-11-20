package org.dslplatform.mirror.types;

public enum DslCollection implements DslType {

    ARRAY("$TYPE[]")
    , LIST("List<$TYPE>")
    , SET("Set<$TYPE>")
    ;

    private final String keyword;
    private DslType type;

    private DslCollection(String keywordPattern) {
        this.keyword = keywordPattern;
    }

    public String getKeyword() {
        if(this.type == null)
            return keyword.replace("$TYPE", "");
        else
            return keyword.replace("$TYPE", this.type.getKeyword());
    }

    public DslCollection withType(DslType type) {
        this.type=type;
        return this;
    }
}
