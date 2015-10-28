package org.nakedpojo;

import org.nakedpojo.javascript.InternalRepresentationParser;
import org.reflections.Reflections;
import org.reflections.scanners.SubTypesScanner;
import org.reflections.util.ClasspathHelper;
import org.stringtemplate.v4.ST;
import org.stringtemplate.v4.STGroupFile;

import java.util.HashSet;
import java.util.Set;

public class NakedPojo {

    private final Set<Class<?>> targets = new HashSet<Class<?>>();

    private final InternalRepresentationParser parser = new InternalRepresentationParser();
    private final STGroupFile stGroupFile = new STGroupFile(DEFAULT_TEMPLATE_FILE);

    private static String DEFAULT_TEMPLATE_FILE = "NakedPojoTemplates.stg";

    /**
     * For tests
     */
    NakedPojo(Reflections reflections) {
        targets.addAll(reflections.getSubTypesOf(Object.class));
    }

    public NakedPojo() {

    }

    public NakedPojo(Package ... packages) {
        for(String name: packageNames(packages)) {
            Reflections reflections = new Reflections(
                    ClasspathHelper.forPackage(name),
                    new SubTypesScanner(false),
                    ClasspathHelper.forClass(this.getClass()));
            targets.addAll(reflections.getSubTypesOf(Object.class));
        }
    }

    public NakedPojo(Class ... classes) {
        for(Class clazz : classes)
            targets.add(clazz);
    }

    public String render(Class clazz) {
        if(!targets.contains(clazz)) targets.add(clazz);
        ST template = stGroupFile.getInstanceOf("JavaScriptObject");
        template.add("jsObj", parser.convert(clazz));
        return template.render();
    }

    public String renderAll() {
        StringBuilder sb = new StringBuilder();
        for(Class clazz: targets) {
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
