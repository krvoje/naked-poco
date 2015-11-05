package org.nakedpojo.javascript;

import org.nakedpojo.Parser;

import javax.annotation.processing.ProcessingEnvironment;
import javax.lang.model.element.Element;
import javax.lang.model.element.ExecutableElement;
import javax.lang.model.element.Modifier;
import javax.lang.model.type.TypeKind;
import javax.lang.model.type.TypeMirror;
import javax.lang.model.util.Elements;
import javax.lang.model.util.Types;
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

    public final Map<TypeMirror, JSType> prototypes = new HashMap<TypeMirror, JSType>();

    private final Types types;
    private final Elements elements;

    public TypeMirrorParser(Types types, Elements elements) {
        this.types = types;
        this.elements = elements;
    }

    public JSType convert(Element clazz) {
        String fieldName = clazz.getSimpleName().toString();
        return convert(clazz, fieldName);
    }

    public JSType convert(Element clazz, String fieldName) {
        return convert(clazz, fieldName);
    }

    private void scanHierarchy(Element clazz) {
        if(!prototypes.containsKey(clazz))
            prototypes.put(clazz.asType(), new JSType(clazz.getSimpleName().toString()));
        else
            return;

        JSType jsType = prototypes.get(clazz);

        List<JSType> members = new ArrayList<JSType>();

        if(isPrimitive(clazz)) {
            // Nothing to do here, already done in initialization
            prototypes.put(clazz.asType(), convertPrimitive(clazz));
        }
        else if(isEnum(clazz)) {
            // TODO: Handle better
            //for(Object enumConstant: clazz.getEnumConstants())
//                members.add(new JSType(clazz.getSimpleName().toString(), enumConstant.toString(), Type.OBJECT));

            members.add(new JSType("Fake enum", Type.STRING));

            prototypes.put(clazz.asType(),
                    jsType
                        .withType(Type.ENUM)
                        .withMembers(members.toArray(new JSType[members.size()])));
        }
        else if(isArray(clazz)) {
            // TODO: fix, this renders differently than it should
            prototypes.put(clazz.asType(), jsType.withType(Type.ARRAY));
        }
        else {
            for(ExecutableElement getter: getters(clazz)) {
                Element returnTypeClass = types.asElement(getter.getReturnType());
                members.add(convert(returnTypeClass, returnTypeClass.getSimpleName().toString()));
            }

            for(Element field : publicFields(clazz)) {
                Element fieldType = types.asElement(field.asType());
                members.add(convert(fieldType, field.getSimpleName().toString()));
            }

            prototypes.put(clazz.asType(), jsType
                    .withType(Type.OBJECT)
                    .withMembers(members));
        }
    }

    private static JSType convertPrimitive(Element clazz) {
        if(equalsEither(clazz,
                Boolean.class,
                boolean.class)) {
            return new JSType(clazz.getSimpleName().toString(), Type.BOOLEAN);
        }
        else if(equalsEither(clazz,
                Character.class,
                char.class,
                String.class)) {
            return new JSType(clazz.getSimpleName().toString(), Type.STRING);
        }
        else if(equalsEither(clazz,
                Byte.class,
                byte.class)){
            // TODO: implement
            return new JSType(clazz.getSimpleName().toString(), Type.UNDEFINED);
        }
        else if(equalsEither(clazz,
                Short.class,
                short.class,
                Integer.class,
                int.class,
                Long.class,
                long.class,
                Float.class,
                float.class,
                Double.class,
                double.class)) {
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
                        && typeKind.equals(TypeKind.EXECUTABLE)
                        && element.getModifiers().contains(Modifier.PUBLIC)
                        && !executableElement.getReturnType().getKind().equals(TypeKind.VOID)) {
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
                        && typeKind.equals(TypeKind.EXECUTABLE)
                        && element.getModifiers().contains(Modifier.PUBLIC)
                        && executableElement.getReturnType().getKind().equals(TypeKind.VOID)) {
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
                || types.isSubtype(element.asType(), elements.getTypeElement("java.lang.Iterable").asType());
    }
}
