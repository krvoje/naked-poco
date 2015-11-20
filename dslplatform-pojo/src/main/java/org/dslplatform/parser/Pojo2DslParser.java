package org.dslplatform.parser;

import org.dslplatform.mirror.elements.AggregateRoot;
import org.dslplatform.mirror.elements.DslField;
import org.dslplatform.mirror.elements.DslPlatformElement;
import org.dslplatform.mirror.elements.ModuleElement;
import org.nakedpojo.utils.Commons.*;
import org.nakedpojo.utils.TypeMirrorUtils;

import javax.lang.model.element.Element;
import java.util.Comparator;
import java.util.Map;
import java.util.TreeMap;

import static org.dslplatform.collections.Stream.with;

public class Pojo2DslParser {

    private static Map<Element, DslPlatformElement> prototypes = new TreeMap<Element, DslPlatformElement>(new Comparator<Element>(){
        public int compare(Element o1, Element o2) {
            return o1.toString().compareTo(o2.toString());
        }
    });

    private final TypeMirrorUtils utils;

    public Pojo2DslParser(TypeMirrorUtils utils) {
        this.utils = utils;
    }

    public DslField convert(Element element) {
        String fieldName = utils.fieldName(element);
        return convert(element, fieldName);
    }

    public DslField convert(Element element, String fieldName) {
        scan(element);
        return prototypes.get(element).withName(fieldName);
    }

    public void scan(Element element) {
        // TODO: Detect whether this si an aggregate root, value or entity
        if(!prototypes.containsKey(element))
            prototypes.put(element, new AggregateRoot(utils.fieldName(element)));
        else
            return;

        DslPlatformElement prototype = prototypes.get(element);

        if(utils.isPrimitive(element)) {
            processPrimitive(element, prototype);
        } else if(utils.isIterable(element)) {
            processIterable(element, prototype);
        } else {
            processClass(element, prototype);
        }
    }

    private void processPrimitive(Element element, DslPlatformElement prototype) {

    }

    private void processIterable(Element element, DslPlatformElement prototype) {

    }

    private void processClass(Element element, DslPlatformElement prototype) {

    }

}
