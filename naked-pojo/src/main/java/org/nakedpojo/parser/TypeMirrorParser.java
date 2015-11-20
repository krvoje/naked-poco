package org.nakedpojo.parser;

import org.nakedpojo.Messages;
import org.nakedpojo.interfaces.Parser;
import org.nakedpojo.model.javascript.JSType;
import org.nakedpojo.NakedParseException;
import org.nakedpojo.model.javascript.Type;
import org.nakedpojo.utils.TypeMirrorUtils;

import javax.annotation.processing.Messager;
import javax.lang.model.element.Element;
import javax.lang.model.element.ElementKind;
import javax.lang.model.element.ExecutableElement;
import javax.lang.model.util.Elements;
import javax.lang.model.util.Types;
import javax.tools.Diagnostic;
import java.util.*;

public class TypeMirrorParser implements Parser<Element, JSType>
{
    // TODO: Package path, currently classes with same simple name in different packages will overwrite each other
    // TODO: Generics

    // Note: Whether to take constructors into consideration?

    private final Map<Element, JSType> prototypes;

    private final Types types;
    private final Elements elements;
    private final Messager messager;
    private final TypeMirrorUtils utils;

    public TypeMirrorParser(Types types, Elements elements, Messager messager) {
        this.types = types;
        this.elements = elements;
        this.messager = messager;
        this.utils = new TypeMirrorUtils(types, elements, messager);
        this.prototypes = new TreeMap<>(utils.TYPE_NAME_COMPARATOR);
    }

    public JSType convert(Element element) {
        assertNotNull(element);
        String fieldName = utils.fieldName(element);
        return convert(element, fieldName);
    }

    public JSType convert(Element element, String fieldName) {
        assertNotNull(element);
        scan(element);
        return prototypes.get(element).withName(fieldName);
    }

    public void scan(Element element) {
        assertNotNull(element);
        if(!prototypes.containsKey(element))
            prototypes.put(element, new JSType(utils.fieldName(element)));
        else
            return;

        JSType prototype = prototypes.get(element);

        List<JSType> members = new ArrayList<>();

        if(utils.isPrimitive(element)) {
            prototypes.put(element, convertPrimitive(element));
        }
        else if(utils.isEnum(element)) {
            for(Element enumMeber : element.getEnclosedElements()) {
                if(enumMeber.getKind().equals(ElementKind.ENUM_CONSTANT))
                    members.add(new JSType(utils.typeName(enumMeber), Type.ENUM_MEMBER));
            }

            prototypes.put(element,
                    prototype
                            .withType(Type.ENUM)
                            .withMembers(members));
        }
        else if(utils.isIterable(element)) {
            prototypes.put(element, prototype.withType(Type.ARRAY));
        }
        else if(utils.isClass(element)) {
            prototype = prototype.withType(Type.OBJECT);
            prototypes.put(element, prototype);

            // Scan all supertype elements and add them to this class' prototype
            for(Element superTypeElement : utils.supertypeElements(element)) {
                scan(superTypeElement);

                processGetters(superTypeElement, prototype);
                processSetters(superTypeElement, prototype);
                processPublicFields(superTypeElement, prototype);
                processNestedClasses(superTypeElement, prototype);
            }

            processGetters(element, prototype);
            processSetters(element, prototype);
            processPublicFields(element, prototype);
            processNestedClasses(element, prototype);

            prototypes.put(element, prototype
                    .withMembers(members));
        }
        // Any other type of class?
    }

    private void processGetters(Element element, JSType prototype) {
        List<JSType> members = prototype.getMembers();
        for(ExecutableElement getter: utils.getters(element)) {
            Element returnTypeClass = types.asElement(getter.getReturnType());
            members.add(convert(returnTypeClass, utils.simpleName(returnTypeClass)));
        }
    }

    private void processSetters(Element element, JSType prototype) {
        List<JSType> members = prototype.getMembers();
        for(ExecutableElement setter : utils.setters(element)) {
            Element returnTypeClass = types.asElement(setter.getReturnType());
            members.add(convert(returnTypeClass, utils.simpleName(returnTypeClass)));
        }
    }

    private void processPublicFields(Element element, JSType prototype) {
        List<JSType> members = prototype.getMembers();
        for (Element field : utils.publicFields(element)) {
            members.add(convert(field, utils.simpleName(field)));
        }
    }

    private void processNestedClasses(Element element, JSType prototype) {
        List<JSType> members = prototype.getMembers();
        for (Element nestedClass : utils.nestedClasses(element)) {
            scan(nestedClass);
        }
    }

    private JSType convertPrimitive(Element element) {
        String simpleName = utils.simpleName(element);
        if(utils.isBoolean(element)) {
            return new JSType(simpleName, Type.BOOLEAN);
        }
        else if(utils.isString(element)) {
            return new JSType(simpleName, Type.STRING);
        }
        else if(utils.isByte(element)) {
            // TODO: implement
            return new JSType(simpleName, Type.UNDEFINED);
        }
        else if(utils.isNumeric(element)) {
            return new JSType(simpleName, Type.NUMBER);
        }
        else {
            return new JSType(simpleName, Type.UNDEFINED);
        }
    }

    private void note(String string) {
        System.out.println(string);
        messager.printMessage(Diagnostic.Kind.NOTE, string);
    }

    public Map<Element, JSType> prototypes() {
        return this.prototypes;
    }

    private static void assertNotNull(Element obj) {
        if(obj == null) throw new NakedParseException(Messages.elementIsNull());
    }
}
