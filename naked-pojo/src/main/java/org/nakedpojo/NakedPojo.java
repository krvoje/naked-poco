package org.nakedpojo;

import org.nakedpojo.interfaces.Parser;
import org.stringtemplate.v4.ST;
import org.stringtemplate.v4.STGroupFile;

import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class NakedPojo<SOURCE_TYPE, METADATA_TYPE> {

    public enum TemplateType {
        DEFAULT("KnockoutObservableTemplates.stg"),
        KNOCKOUT_JS("KnockoutObservableTemplates.stg"),
        PLAIN_JAVASCRIPT("PlainJavaScriptTemplates.stg"),
        CUSTOM("");

        private String templateFilename;
        private TemplateType(String templateFilename) {
            this.templateFilename = templateFilename;
        }

        TemplateType  withFilename(String templateFilename) {
            this.templateFilename = templateFilename;
            return this;
        }
    }

    private static final String JS_TEMPLATE = "JavaScriptObject";
    private static final String JS_INSTANCE_TEMPLATE = "jsObj";

    // TODO: Use a custom comparator here as well.
    private final Set<SOURCE_TYPE> targets = new HashSet<SOURCE_TYPE>();

    private final Parser<SOURCE_TYPE, METADATA_TYPE> parser;
    private final TemplateType templateType;
    private final STGroupFile stGroupFile;

    public NakedPojo(Parser<SOURCE_TYPE, METADATA_TYPE> parser, TemplateType templateType, SOURCE_TYPE... targets) {
        this.parser = parser;
        this.templateType = templateType;
        this.stGroupFile = new STGroupFile(this.templateType.templateFilename);

        if(targets != null)
        for(SOURCE_TYPE target : targets)
            this.targets.add(target);
    }

    public NakedPojo(Parser<SOURCE_TYPE, METADATA_TYPE> parser, SOURCE_TYPE... targets) {
        this(parser, TemplateType.DEFAULT, targets);
    }

    public NakedPojo(Parser<SOURCE_TYPE, METADATA_TYPE> parser, String customTemplateFilename, SOURCE_TYPE... targets) {
        this.parser = parser;
        this.templateType = TemplateType.CUSTOM.withFilename(customTemplateFilename);
        this.stGroupFile = new STGroupFile(customTemplateFilename);

        if(targets != null)
            for(SOURCE_TYPE target : targets)
                scan(target);

        for(Map.Entry<SOURCE_TYPE, METADATA_TYPE> e: parser.prototypes().entrySet()) {
            SOURCE_TYPE key = e.getKey();
            if(!this.targets.contains(key)) this.targets.add(key);
        }
    }

    public void scan(SOURCE_TYPE clazz) {
        if(!targets.contains(clazz)) targets.add(clazz);
        this.parser.scan(clazz);
    }

    public String render(SOURCE_TYPE clazz) {
        if(!targets.contains(clazz)) targets.add(clazz);
        ST template = stGroupFile.getInstanceOf(JS_TEMPLATE);
        template.add(JS_INSTANCE_TEMPLATE, parser.convert(clazz));
        return template.render();
    }

    public String renderAll() {
        StringBuilder sb = new StringBuilder();
        for(SOURCE_TYPE clazz: targets) {
            sb.append(this.render(clazz));
        }
        return sb.toString();
    }

    private static String[] packageNames(Package ... packages) {
        String[] names = new String[packages.length];
        for(int i=0; i<packages.length; i++) {
            names[i] = packages[i].getName();
        }
        return names;
    }
}
