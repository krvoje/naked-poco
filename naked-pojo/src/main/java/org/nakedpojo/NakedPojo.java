package org.nakedpojo;

import org.nakedpojo.interfaces.Parser;
import org.nakedpojo.model.javascript.JSType;
import org.stringtemplate.v4.ST;
import org.stringtemplate.v4.STGroupFile;

import java.util.*;

import static java.util.Arrays.asList;

public class NakedPojo<SOURCE_TYPE> {

    private static final String JS_TEMPLATE = "JavaScriptObject";
    private static final String JS_INSTANCE_TEMPLATE = "jsObj";

    private final Set<SOURCE_TYPE> targets;
    private final Parser<SOURCE_TYPE, JSType> parser;

    public NakedPojo(Parser<SOURCE_TYPE, JSType> parser,
                     Comparator<SOURCE_TYPE> sourceTypeComparator,
                     SOURCE_TYPE ... targets) {
        this.parser = parser;

        this.targets = sourceTypeComparator != null ?
                new TreeSet<SOURCE_TYPE>(sourceTypeComparator)
                : new HashSet<SOURCE_TYPE>();

        scan(targets);
        target(targets);

        for(Map.Entry<SOURCE_TYPE, JSType> e: parser.prototypes().entrySet()) {
            SOURCE_TYPE key = e.getKey();
            target(key);
        }
    }

    public NakedPojo(Parser<SOURCE_TYPE, JSType> parser, SOURCE_TYPE ... targets) {
        this(parser, null, targets);
    }

    public void scan(Iterable<SOURCE_TYPE> targets) {
        if(targets != null)
            for(SOURCE_TYPE target : targets)
                scan(target);
    }

    public void scan(SOURCE_TYPE ... targets) {
        scan(asList(targets));
    }

    public void scan(SOURCE_TYPE clazz) {
        target(clazz);
        this.parser.scan(clazz);
    }

    public String render(SOURCE_TYPE clazz, String templateFilename) {
        target(clazz);

        STGroupFile stGroupFile = new STGroupFile(templateFilename);
        ST template = stGroupFile.getInstanceOf(JS_TEMPLATE);
        template.add(JS_INSTANCE_TEMPLATE, parser.convert(clazz));
        return template.render();
    }

    public void setTargetTypeName(SOURCE_TYPE source, String name) {
        JSType prototype = parser.prototypes().get(source);
        parser.prototypes().put(source, prototype.withTypeName(name));
    }

    private void target(Iterable<SOURCE_TYPE> targets) {
        for(SOURCE_TYPE element: targets) target(element);
    }

    private void target(SOURCE_TYPE ... targets) {
        target(asList(targets));
    }

    private void target(SOURCE_TYPE target) {
        if(!targets.contains(target)) {
            targets.add(target);
        }
    }
}
