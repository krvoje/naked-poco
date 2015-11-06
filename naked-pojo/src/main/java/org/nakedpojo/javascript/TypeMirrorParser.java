package org.nakedpojo.javascript;

import org.nakedpojo.Parser;

import javax.annotation.processing.Messager;
import javax.lang.model.element.Element;
import javax.lang.model.element.ExecutableElement;
import javax.lang.model.element.Modifier;
import javax.lang.model.type.TypeKind;
import javax.lang.model.type.TypeMirror;
import javax.lang.model.util.Elements;
import javax.lang.model.util.Types;
import javax.tools.Diagnostic;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.nakedpojo.javascript.Utils.*;

public class TypeMirrorParser implements Parser<Element>
{
    // TODO: Normalize package path
    // TODO: Constructor elements
    // TODO: Generics

    // Maps fully qualified class names to JSTypes, since TypeMirror is something quite exotic
    public final Map<String, JSType> prototypes = new HashMap<String, JSType>();

    private final Types types;
    private final Elements elements;
    private final Messager messager;

    public TypeMirrorParser(Types types, Elements elements, Messager messager) {
        this.types = types;
        this.elements = elements;
        this.messager = messager;
    }

    public JSType convert(Element clazz) {
        if(clazz == null) throw new NakedParseException(Messages.elementIsNull());
        String fieldName = clazz.getSimpleName().toString();
        return convert(clazz, fieldName);
    }

    public JSType convert(Element clazz, String fieldName) {
        if(clazz == null) throw new NakedParseException(Messages.elementIsNull());
        scanHierarchy(clazz);
        return prototypes.get(typeName(clazz)).withName(fieldName);
    }

    private void scanHierarchy(Element clazz) {
        note("Scanning class: " + clazz);
        if(clazz == null) {
            return; // TODO: deal with this
        }

        String typeName = typeName(clazz);
        if(!prototypes.containsKey(typeName))
            prototypes.put(typeName, new JSType(clazz.getSimpleName().toString()));
        else
            return;

        JSType jsType = prototypes.get(typeName);

        List<JSType> members = new ArrayList<JSType>();

        if(isPrimitive(clazz)) {
            prototypes.put(typeName, convertPrimitive(clazz));
        }
        else if(isEnum(clazz)) {
            // TODO: Handle better
            // TODO: implement this
            //for(Object enumConstant: clazz.getEnumConstants())
//                members.add(new JSType(clazz.getSimpleName().toString(), enumConstant.toString(), Type.OBJECT));

            members.add(new JSType("Fake enum", Type.STRING));

            prototypes.put(typeName,
                    jsType
                        .withType(Type.ENUM)
                        .withMembers(members.toArray(new JSType[members.size()])));
        }
        else if(isArray(clazz)) {
            note(clazz + "is an array");
            // TODO: see whether this needs to be fixed as well, like the Reflections version
            prototypes.put(typeName, jsType.withType(Type.ARRAY));
        }
        else {
            for(ExecutableElement getter: getters(clazz)) {
                Element returnTypeClass = types.asElement(getter.getReturnType());
                if(returnTypeClass == null) note(String.format("Return type of %s is null", getter.toString()));
                members.add(convert(returnTypeClass, returnTypeClass.getSimpleName().toString()));
            }

            for(Element field : publicFields(clazz)) {
                members.add(convert(field, field.getSimpleName().toString()));
            }

            prototypes.put(typeName, jsType
                    .withType(Type.OBJECT)
                    .withMembers(members));
        }
    }

    private JSType convertPrimitive(Element clazz) {
        TypeMirror theType = clazz.asType();
        if(comparesToEither(theType.getKind(), TypeKind.BOOLEAN)) {
            return new JSType(clazz.getSimpleName().toString(), Type.BOOLEAN);
        }
        else if(comparesToEither(clazz.asType().getKind(),
                TypeKind.CHAR)
                || clazz.getSimpleName().toString().equals(String.class.getCanonicalName())) {
            return new JSType(clazz.getSimpleName().toString(), Type.STRING);
        }
        else if(comparesToEither(clazz.asType().getKind(),
                TypeKind.BYTE)) {
            // TODO: implement
            return new JSType(clazz.getSimpleName().toString(), Type.UNDEFINED);
        }
        else if(comparesToEither(clazz.asType().getKind(),
                TypeKind.SHORT,
                TypeKind.INT,
                TypeKind.LONG,
                TypeKind.FLOAT,
                TypeKind.DOUBLE)) {
            return new JSType(clazz.getSimpleName().toString(), Type.NUMBER);
        }
        else {
            return new JSType(clazz.getSimpleName().toString(), Type.UNDEFINED);
        }
    }

    private List<ExecutableElement> getters(Element element) {
        // TODO: missing functionality
        List<ExecutableElement> getters = new ArrayList<ExecutableElement>();
        for(Element enclosed: element.getEnclosedElements()) {
            if(element instanceof ExecutableElement) {
                ExecutableElement executableElement = (ExecutableElement) element;
                TypeKind typeKind = element.asType().getKind();
                String name = element.getSimpleName().toString();
                element.getModifiers();
                if(isGetterName(name)
                        && typeKind.compareTo(TypeKind.EXECUTABLE)==0
                        && element.getModifiers().contains(Modifier.PUBLIC)
                        // TODO:
                        && executableElement.getReturnType().getKind().compareTo(TypeKind.VOID)!=0) {
                    getters.add((ExecutableElement)enclosed);
                }
            }
        }
        return getters;
    }

    private List<ExecutableElement> setters(Element element) {
        // TODO: missing functionality
        List<ExecutableElement> setters = new ArrayList<ExecutableElement>();
        for(Element enclosed: element.getEnclosedElements()) {
            if(element instanceof ExecutableElement) {
                ExecutableElement executableElement = (ExecutableElement) element;
                TypeKind typeKind = element.asType().getKind();
                String name = element.getSimpleName().toString();
                element.getModifiers();
                if(isSetterName(name)
                        && typeKind.compareTo(TypeKind.EXECUTABLE) == 0
                        && element.getModifiers().contains(Modifier.PUBLIC)
                        && executableElement.getReturnType().getKind().compareTo(TypeKind.VOID)==0) {
                    setters.add((ExecutableElement)enclosed);
                }
            }
        }
        return setters;
    }

    private List<Element> publicFields(Element element) {
        List<Element> publicFields = new ArrayList<Element>();
        for(Element enclosed: element.getEnclosedElements()) {
            if(enclosed.getKind().isField()) {
                if(enclosed.getModifiers().contains(Modifier.PUBLIC))
                    publicFields.add(enclosed);
            }
        }
        return publicFields;
    }

    private boolean isPrimitive(Element element) {
        TypeMirror stringType = elements.getTypeElement("java.lang.String").asType();
        return element.asType().getKind().isPrimitive()
                || types.isSameType(element.asType(), stringType);
    }

    private boolean isEnum(Element element) {
        return types.isSubtype(element.asType(), elements.getTypeElement("java.lang.Enum").asType());
    }

    private boolean isArray(Element element) {
        return element.asType().getKind().equals(TypeKind.ARRAY)
            || types.isSubtype(element.asType(), elements.getTypeElement("java.util.List").asType());
    }

    private String typeName(Element clazz) {
        String typeName;
        if(clazz == null) typeName ="null";
        else if(clazz.asType().getKind().equals(TypeKind.EXECUTABLE)) {
            typeName = ((ExecutableElement)clazz).getReturnType().toString();
        }
        else
            typeName = clazz.toString();
        return typeName;
    }

    private void note(String string) {
        System.out.println(string);
        messager.printMessage(Diagnostic.Kind.NOTE, string);
    }
}
