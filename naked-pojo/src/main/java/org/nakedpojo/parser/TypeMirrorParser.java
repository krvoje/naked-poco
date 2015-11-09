package org.nakedpojo.parser;

import org.nakedpojo.Messages;
import org.nakedpojo.interfaces.Parser;
import org.nakedpojo.model.javascript.JSType;
import org.nakedpojo.NakedParseException;
import org.nakedpojo.model.javascript.Type;
import org.nakedpojo.utils.TypeMirrorUtils;

import javax.annotation.processing.Messager;
import javax.lang.model.element.*;
import javax.lang.model.util.Elements;
import javax.lang.model.util.Types;
import javax.tools.Diagnostic;
import java.util.*;

public class TypeMirrorParser implements Parser<Element, JSType>
{
    // TODO: Normalize package path
    // TODO: Constructor elements
    // TODO: Generics

    // Maps fully qualified class names to JSTypes, since TypeMirror is something quite exotic
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
        this.prototypes = new TreeMap<Element, JSType>(new Comparator<Element>() {
            @Override
            public int compare(Element left, Element right) {
                return utils.typeName(left).compareTo(utils.typeName(right));
            }
        });
    }

    public JSType convert(Element clazz) {
        if(clazz == null)
            throw new NakedParseException(Messages.elementIsNull());
        String fieldName = clazz.getSimpleName().toString();
        return convert(clazz, fieldName);
    }

    public JSType convert(Element element, String fieldName) {
        if(element == null)
            throw new NakedParseException(Messages.elementIsNull());
        scan(element);
        return prototypes.get(element).withName(fieldName);
    }

    public void scan(Element element) {
        if(!prototypes.containsKey(element))
            prototypes.put(element, new JSType(element.getSimpleName().toString()));
        else
            return;

        JSType jsType = prototypes.get(element);

        List<JSType> members = new ArrayList<JSType>();

        if(utils.isPrimitive(element)) {
            prototypes.put(element, convertPrimitive(element));
        }
        else if(utils.isEnum(element)) {
            for(Element enumMeber : element.getEnclosedElements()) {
                if(enumMeber.getKind().equals(ElementKind.ENUM_CONSTANT))
                    members.add(new JSType(utils.typeName(enumMeber), Type.ENUM_MEMBER));
            }

            prototypes.put(element,
                    jsType
                        .withType(Type.ENUM)
                        .withMembers(members));
        }
        else if(utils.isArray(element)) {
            // TODO: see whether this needs to be fixed as well, like the Reflections version
            prototypes.put(element, jsType.withType(Type.ARRAY));
        }
        else {
            for(ExecutableElement getter: utils.getters(element)) {
                Element returnTypeClass = types.asElement(getter.getReturnType());
                members.add(convert(returnTypeClass, utils.simpleName(returnTypeClass)));
            }

            for (Element field : utils.publicFields(element)) {
                members.add(convert(field, utils.simpleName(field));
            }

            for (Element nestedClass : utils.nestedClasses(element)) {
                scan(nestedClass);
            }

            prototypes.put(element, jsType
                    .withType(Type.OBJECT)
                    .withMembers(members));
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
}
