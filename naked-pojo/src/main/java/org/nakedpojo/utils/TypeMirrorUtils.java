package org.nakedpojo.utils;

import org.nakedpojo.Messages;
import org.nakedpojo.NakedParseException;

import javax.annotation.processing.Messager;
import javax.lang.model.element.*;
import javax.lang.model.type.TypeKind;
import javax.lang.model.type.TypeMirror;
import javax.lang.model.util.Elements;
import javax.lang.model.util.Types;

import java.util.ArrayList;
import java.util.List;

import static org.nakedpojo.utils.Commons.equalsEither;
import static org.nakedpojo.utils.Commons.isGetterName;

public class TypeMirrorUtils {

    private final Types types;
    private final Elements elements;
    private final Messager messager;

    public TypeMirrorUtils(Types types, Elements elements, Messager messager) {
        this.types = types;
        this.elements = elements;
        this.messager = messager;
    }

    public  boolean isNumeric(Element element) {
        return equalsEither(element.asType().getKind(),
                TypeKind.SHORT,
                TypeKind.INT,
                TypeKind.LONG,
                TypeKind.FLOAT,
                TypeKind.DOUBLE);
    }

    public  boolean isPublicField(Element e) {
        return e.getKind().isField()
                && e.getModifiers().contains(Modifier.PUBLIC);
    }

    public boolean isPrimitive(Element element) {
        TypeMirror stringType = elements.getTypeElement(java.lang.String.class.getCanonicalName()).asType();
        return element.asType().getKind().isPrimitive()
                || types.isSameType(element.asType(), stringType);
    }

    public boolean isString(Element element) {
        TypeMirror stringType = elements.getTypeElement(java.lang.String.class.getCanonicalName()).asType();
        return element.asType().getKind().equals(TypeKind.CHAR)
                || types.isSameType(element.asType(), stringType);
    }

    public boolean isBoolean(Element element) {
        return element.asType().getKind().equals(TypeKind.BOOLEAN);
    }

    public boolean isByte(Element element) {
        return element.asType().getKind().equals(TypeKind.BYTE);
    }

    public boolean isEnum(Element element)
    {
        return element.getKind().equals(ElementKind.ENUM);
    }

    public boolean isArray(Element element) {
        TypeElement list = elements.getTypeElement(java.lang.Iterable.class.getCanonicalName());
        return element.asType().getKind().equals(TypeKind.ARRAY)
                || types.isSubtype(element.asType(), types.getDeclaredType(list));
    }

    public String typeName(Element clazz) {
        if(clazz == null) throw new NakedParseException(Messages.elementIsNull());
        return clazz.asType().getKind().equals(TypeKind.EXECUTABLE) ?
                ((ExecutableElement)clazz).getReturnType().toString()
                : clazz.toString();
    }

    public String simpleName(Element clazz) {
        if(clazz == null) throw new NakedParseException(Messages.elementIsNull());
        return clazz.asType().getKind().equals(TypeKind.EXECUTABLE) ?
                ((ExecutableElement)clazz).getSimpleName().toString()
                : clazz.getSimpleName().toString();
    }

    public List<Element> publicFields(Element element) {
        List<Element> publicFields = new ArrayList<Element>();
        for(Element enclosed: element.getEnclosedElements()) {
            if(isPublicField(enclosed)) {
                publicFields.add(enclosed);
            }
        }
        return publicFields;
    }

    public List<Element> nestedClasses(Element element) {
        List<Element> nestedClasses = new ArrayList<Element>();
        for(Element enclosed: element.getEnclosedElements()) {
            if(enclosed.getKind().isClass() || enclosed.getKind().isInterface()) {
                nestedClasses.add(enclosed);
            }
        }
        return nestedClasses;
    }

    public List<ExecutableElement> getters(Element element) {
        List<ExecutableElement> getters = new ArrayList<ExecutableElement>();
        for(Element enclosed: element.getEnclosedElements()) {
            if(element instanceof ExecutableElement) {
                ExecutableElement executableElement = (ExecutableElement) element;
                TypeKind typeKind = element.asType().getKind();
                String name = element.getSimpleName().toString();
                element.getModifiers();
                if(isGetterName(name)
                        && typeKind.equals(TypeKind.EXECUTABLE)
                        && element.getModifiers().contains(Modifier.PUBLIC)
                        && !executableElement.getReturnType().getKind().equals(TypeKind.VOID)
                        && executableElement.getParameters().isEmpty()
                        ) {
                    getters.add((ExecutableElement)enclosed);
                }
            }
        }
        return getters;
    }
}
