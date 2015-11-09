package org.nakedpojo;

import org.nakedpojo.interfaces.Parser;
import org.stringtemplate.v4.ST;
import org.stringtemplate.v4.STGroupFile;

import java.util.HashSet;
import java.util.Set;

public class NakedPojo<SOURCE_TYPE, METADATA_TYPE> {

    // TODO: Use a custom comparator here as well.
    private final Set<SOURCE_TYPE> targets = new HashSet<SOURCE_TYPE>();

    private final Parser<SOURCE_TYPE, METADATA_TYPE> parser;
    private final STGroupFile stGroupFile = new STGroupFile(DEFAULT_TEMPLATE_FILE);

    private static String DEFAULT_TEMPLATE_FILE = "NakedPojoTemplates.stg";

    public NakedPojo(Parser<SOURCE_TYPE, METADATA_TYPE> parser) {
        this.parser = parser;
    }

    public NakedPojo(Parser<SOURCE_TYPE, METADATA_TYPE> parser, SOURCE_TYPE... targets) {
        this(parser);
        for(SOURCE_TYPE target : targets)
            this.targets.add(target);
    }

    public void scan(SOURCE_TYPE clazz) {
        if(!targets.contains(clazz)) targets.add(clazz);
        this.parser.scan(clazz);
    }

    public String render(SOURCE_TYPE clazz) {
        if(!targets.contains(clazz)) targets.add(clazz);
        ST template = stGroupFile.getInstanceOf("JavaScriptObject");
        template.add("jsObj", parser.convert(clazz));
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
