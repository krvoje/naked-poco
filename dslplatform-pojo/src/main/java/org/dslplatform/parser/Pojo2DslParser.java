package org.dslplatform.parser;

import org.dslplatform.mirror.elements.AggregateRoot;
import org.dslplatform.mirror.elements.DslEnum;
import org.dslplatform.mirror.elements.DslField;
import org.dslplatform.mirror.elements.DslPlatformElement;
import org.dslplatform.mirror.types.DslPrimitive;
import org.nakedpojo.utils.TypeMirrorUtils;

import javax.lang.model.element.Element;
import java.util.Comparator;
import java.util.Map;
import java.util.TreeMap;

public class Pojo2DslParser {

    private static Map<Element, DslPlatformElement> prototypes =
            new TreeMap<Element, DslPlatformElement>(new Comparator<Element>(){
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
        if(isDslPrimitive(element)) {
            processPrimitive(element);
        } else if(utils.isEnum(element)) {
            processEnum(element);
        } else if(utils.isIterable(element)) {
            processIterable(element);
        } else {
            processClass(element);
        }
    }

    private void processPrimitive(Element element) {
        DslPlatformElement prototype = fetchPrototypeFor(element);
    }

    private void processEnum(Element element) {
        DslPlatformElement prototype = fetchPrototypeFor(element);
    }

    private void processIterable(Element element) {
        DslPlatformElement prototype = fetchPrototypeFor(element);
    }

    private void processClass(Element element) {
        DslPlatformElement prototype = fetchPrototypeFor(element);

        processGetters(element);
        processSetters(element);
        processPublicFields(element);
        processNestedClasses(element);

        // Scan all supertype elements and add them to this class' prototype
        for(Element superTypeElement : utils.supertypeElements(element)) {
            scan(superTypeElement);

            processGetters(superTypeElement);
            processSetters(superTypeElement);
            processPublicFields(superTypeElement);
            processNestedClasses(superTypeElement);
        }

        prototypes.put(element, prototype);
    }

    private void processGetters(Element element) {

    }

    private void processSetters(Element element) {

    }

    private void processPublicFields(Element element) {

    }

    private void processNestedClasses(Element element) {

    }

    private DslPlatformElement fetchPrototypeFor(Element element) {
        // The default is aggregate root, but in the end the actual type needs to be inferred
        if (!prototypes.containsKey(element)) {
            if (isDslPrimitive(element)) {
                prototypes.put(element, new DslField(utils.fieldName(element),
                        DslPrimitive.forCanonicalName(utils.typeName(element))));
            } else if (utils.isEnum(element)) {
                prototypes.put(element, new DslEnum(utils.fieldName(element)));
            } else if (utils.isIterable(element)) {
                //prototypes.put(element, new DslEnum(utils.fieldName(element)));
            } else if (utils.isIterable(element)) {
                //prototypes.put(element, new DslEnum(utils.fieldName(element)));
            } else if (utils.isClass(element)) {
                prototypes.put(element, new AggregateRoot(utils.fieldName(element)));
            }
        }
        return prototypes.get(element);
    }

    public boolean isDslPrimitive(Element element) {
        for(DslPrimitive primitive : DslPrimitive.values()) {
            if(primitive.isMappedBy(utils.typeName(element)))
                return true;
        }
        return false;
    }
}
