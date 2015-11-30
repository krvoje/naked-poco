package org.nakedpojo.annotations;

public enum TemplateType {
    DEFAULT("KnockoutObservableTemplates.stg"),
    KNOCKOUT_JS("KnockoutObservableTemplates.stg"),
    PLAIN_JAVASCRIPT("PlainJavaScriptTemplates.stg")
    ;

    public final String templateFilename;
    TemplateType(String templateFilename) {
        this.templateFilename = templateFilename;
    }

    public String getTemplateFilename() {
        return this.templateFilename;
    }
}