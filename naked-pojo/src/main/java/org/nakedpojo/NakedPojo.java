package org.nakedpojo;

import org.stringtemplate.v4.ST;
import org.stringtemplate.v4.STGroupFile;

import java.util.HashSet;
import java.util.Set;

public class NakedPojo<T> {

    private final Set<T> targets = new HashSet<T>();

    private final Parser<T> parser;
    private final STGroupFile stGroupFile = new STGroupFile(DEFAULT_TEMPLATE_FILE);

    private static String DEFAULT_TEMPLATE_FILE = "NakedPojoTemplates.stg";

    public NakedPojo(Parser<T> parser) {
        this.parser = parser;
    }

    public NakedPojo(Parser<T> parser, T ... targets) {
        this(parser);
        for(T target : targets)
            this.targets.add(target);
    }

    public String render(T clazz) {
        if(!targets.contains(clazz)) targets.add(clazz);
        ST template = stGroupFile.getInstanceOf("JavaScriptObject");
        template.add("jsObj", parser.convert(clazz));
        return template.render();
    }

    public String renderAll() {
        StringBuilder sb = new StringBuilder();
        for(T clazz: targets) {
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
