package org.nakedpojo.annotations;

public enum TemplateType {
    DEFAULT("KnockoutObservableTemplates.stg"),
    KNOCKOUT_JS("KnockoutObservableTemplates.stg"),
    PLAIN_JAVASCRIPT("PlainJavaScriptTemplates.stg"),
    CUSTOM("");

    private String templateFilename;
    private TemplateType(String templateFilename) {
        this.templateFilename = templateFilename;
    }

    public TemplateType withFilename(String templateFilename) {
        this.templateFilename = templateFilename;
        return this;
    }

    public String getTemplateFilename() {
        return this.templateFilename;
    }
}