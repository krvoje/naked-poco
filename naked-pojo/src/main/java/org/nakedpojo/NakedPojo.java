package org.nakedpojo;

import org.nakedpojo.annotations.TemplateType;
import org.nakedpojo.interfaces.Parser;
import org.nakedpojo.model.javascript.JSType;
import org.stringtemplate.v4.ST;
import org.stringtemplate.v4.STGroupFile;

import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class NakedPojo<SOURCE_TYPE> {

    private static final String JS_TEMPLATE = "JavaScriptObject";
    private static final String JS_INSTANCE_TEMPLATE = "jsObj";

    // TODO: Use a custom comparator here as well.
    private final Set<SOURCE_TYPE> targets = new HashSet<SOURCE_TYPE>();

    private final Parser<SOURCE_TYPE, JSType> parser;

    // TODO: A constructor taking a @Naked annotation

    public NakedPojo(Parser<SOURCE_TYPE, JSType> parser, SOURCE_TYPE... targets) {
        this.parser = parser;

        if(targets != null)
            for(SOURCE_TYPE target : targets)
                scan(target);

        for(Map.Entry<SOURCE_TYPE, JSType> e: parser.prototypes().entrySet()) {
            SOURCE_TYPE key = e.getKey();
            if(!this.targets.contains(key)) this.targets.add(key);
        }
    }

    public void scan(SOURCE_TYPE clazz) {
        if(!targets.contains(clazz)) targets.add(clazz);
        this.parser.scan(clazz);
    }

    public String renderDefault(SOURCE_TYPE clazz) {
        return render(clazz, TemplateType.DEFAULT.templateFilename);
    }

    public String render(SOURCE_TYPE clazz, String templateFilename) {
        if(!targets.contains(clazz)) targets.add(clazz);
        System.out.println(templateFilename);
        STGroupFile stGroupFile = new STGroupFile(templateFilename);
        ST template = stGroupFile.getInstanceOf(JS_TEMPLATE);
        template.add(JS_INSTANCE_TEMPLATE, parser.convert(clazz));
        return template.render();
    }

    public String renderAll() {
        StringBuilder sb = new StringBuilder();
        for(SOURCE_TYPE clazz: targets) {
            // TODO: this is wrong
            sb.append(this.renderDefault(clazz));
        }
        return sb.toString();
    }

    public void setTargetTypeName(SOURCE_TYPE tp, String name) {
        JSType prototype = parser.prototypes().get(tp);
        parser.prototypes().put(tp, prototype.withTypeName(name));
    }
}
