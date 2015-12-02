package org.nakedpojo.utils;

import javax.annotation.processing.Messager;
import javax.lang.model.element.*;
import javax.lang.model.type.TypeKind;
import javax.lang.model.type.TypeMirror;
import javax.lang.model.util.ElementFilter;
import javax.lang.model.util.Elements;
import javax.lang.model.util.Types;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

import static org.nakedpojo.utils.Commons.equalsEither;
import static org.nakedpojo.utils.Commons.fieldNameFromGetterOrSetter;
import static org.nakedpojo.utils.Commons.nullOrEmpty;

public class TypeMirrorUtils {

    private final Types types;
    private final Elements elements;
    private final Messager messager;

    // Quazi-constants, since this cannot be a static util class
    private final TypeMirror BYTE_PRIMITIVE_TYPE;
    private final TypeMirror BYTE_TYPE;
    private final TypeMirror BOOLEAN_PRIMITIVE_TYPE;
    private final TypeMirror BOOLEAN_TYPE;
    private final TypeMirror CHAR_PRIMITIVE_TYPE;
    private final TypeMirror CHAR_TYPE;
    private final TypeMirror SHORT_PRIMITIVE_TYPE;
    private final TypeMirror SHORT_TYPE;
    private final TypeMirror INT_PRIMITIVE_TYPE;
    private final TypeMirror INT_TYPE;
    private final TypeMirror LONG_PRIMITIVE_TYPE;
    private final TypeMirror LONG_TYPE;
    private final TypeMirror FLOAT_PRIMITIVE_TYPE;
    private final TypeMirror FLOAT_TYPE;
    private final TypeMirror DOUBLE_PRIMITIVE_TYPE;
    private final TypeMirror DOUBLE_TYPE;

    private final TypeMirror STRING_TYPE;
    private final TypeMirror OBJECT_TYPE;
    private final TypeMirror CLASS_TYPE;
    private final TypeMirror ENUM_TYPE;
    private final TypeMirror ITERABLE_TYPE;

    private final TypeElement ITERABLE_TYPE_ELEMENT;

    public final Comparator<Element> TYPE_NAME_COMPARATOR = new Comparator<Element>() {
        @Override
        public int compare(Element o1, Element o2) {
            return typeName(o1).compareTo(typeName(o2));
        }
    };

    public TypeMirrorUtils(Types types, Elements elements, Messager messager) {
        this.types = types;
        this.elements = elements;
        this.messager = messager;

        this.BYTE_PRIMITIVE_TYPE = types.getPrimitiveType(TypeKind.BYTE);
        this.BYTE_TYPE= typeForClass(Byte.class);
        this.BOOLEAN_PRIMITIVE_TYPE = types.getPrimitiveType(TypeKind.BOOLEAN);
        this.BOOLEAN_TYPE= typeForClass(Boolean.class);
        this.CHAR_PRIMITIVE_TYPE = types.getPrimitiveType(TypeKind.CHAR);
        this.CHAR_TYPE = typeForClass(Character.class);
        this.SHORT_PRIMITIVE_TYPE = types.getPrimitiveType(TypeKind.SHORT);
        this.SHORT_TYPE = typeForClass(Short.class);
        this.INT_PRIMITIVE_TYPE = types.getPrimitiveType(TypeKind.INT);
        this.INT_TYPE = typeForClass(Integer.class);
        this.LONG_PRIMITIVE_TYPE = types.getPrimitiveType(TypeKind.LONG);
        this.LONG_TYPE = typeForClass(Long.class);
        this.FLOAT_PRIMITIVE_TYPE = types.getPrimitiveType(TypeKind.FLOAT);
        this.FLOAT_TYPE = typeForClass(Float.class);
        this.DOUBLE_PRIMITIVE_TYPE = types.getPrimitiveType(TypeKind.DOUBLE);
        this.DOUBLE_TYPE = typeForClass(Double.class);

        this.STRING_TYPE = typeForClass(String.class);
        this.OBJECT_TYPE = typeForClass(Object.class);
        this.CLASS_TYPE = typeForClass(Class.class);
        this.ENUM_TYPE = typeForClass(Enum.class);
        this.ITERABLE_TYPE = typeForClass(Iterable.class);

        this.ITERABLE_TYPE_ELEMENT = elements.getTypeElement(Iterable.class.getCanonicalName());
    }

    public boolean isNumeric(Element element) {
        return sameTypeAsEither(element.asType(),
                SHORT_PRIMITIVE_TYPE,
                SHORT_TYPE,
                INT_PRIMITIVE_TYPE,
                INT_TYPE,
                LONG_PRIMITIVE_TYPE,
                LONG_TYPE,
                FLOAT_PRIMITIVE_TYPE,
                FLOAT_TYPE,
                DOUBLE_PRIMITIVE_TYPE,
                DOUBLE_TYPE)
                ;
    }

    public boolean isPublicField(Element e) {
        return e.getKind().isField()
                && e.getModifiers().contains(Modifier.PUBLIC);
    }

    public boolean isPrimitive(Element element) {
        return element.asType().getKind().isPrimitive()
                || isNumeric(element)
                || types.isSameType(element.asType(), STRING_TYPE);
    }

    public boolean isString(Element element) {
        return element.asType().getKind().equals(TypeKind.CHAR)
                || sameTypeAsEither(element.asType(),
                    CHAR_PRIMITIVE_TYPE,
                    CHAR_TYPE,
                    STRING_TYPE);
    }

    public boolean isBoolean(Element element) {
        return element.asType().getKind().equals(TypeKind.BOOLEAN)
            || sameTypeAsEither(element.asType(),
                BOOLEAN_PRIMITIVE_TYPE,
                BOOLEAN_TYPE);
    }

    public boolean isByte(Element element) {
        return element.asType().getKind().equals(TypeKind.BYTE)
            || sameTypeAsEither(element.asType(),
                BYTE_PRIMITIVE_TYPE,
                BYTE_TYPE) ;
    }

    public boolean isEnum(Element element) {
        if(element.getKind().equals(ElementKind.ENUM)) {
            return true;
        }
        for(Element superType : supertypeElements(element)) {
            if(types.isSameType(superType.asType(), ENUM_TYPE)) {
                return true;
            }
        }
        return false;
    }

    public boolean isIterable(Element element) {
        return element.asType().getKind().equals(TypeKind.ARRAY)
                || types.isSubtype(element.asType(), types.getDeclaredType(ITERABLE_TYPE_ELEMENT));
    }

    public boolean isInterface(Element element) {
        return element.getKind().isInterface();
    }

    public boolean isClass(Element element) {
        return element.getKind().isClass();
    }

    public String fieldName(Element element) {
        return element.getSimpleName().toString();
    }

    public String fieldNameFromAccessor(ExecutableElement accessor) {
        String fullName = accessor.getSimpleName().toString();
        return fieldNameFromGetterOrSetter(fullName);
    }

    public String typeName(Element element) {
        return element instanceof ExecutableElement ?
                ((ExecutableElement)element).getReturnType().toString()
                : element.asType().toString();
    }

    public List<Element> supertypeElements(Element element) {
        List<Element> supertypeElements = new ArrayList<>();
        for(TypeMirror supertype : types.directSupertypes(element.asType())) {
            // Upon encountering Object, stop
            if(types.isSameType(supertype, OBJECT_TYPE))
                break;

            Element supertypeElement = types.asElement(supertype);
            if(supertypeElement == null || isInterface(supertypeElement))
                continue;

            supertypeElements.add(supertypeElement);
        }
        return supertypeElements;
    }

    public List<Element> publicFields(Element element) {
        List<Element> publicFields = new ArrayList<Element>();
        for(VariableElement enclosed: ElementFilter.fieldsIn(element.getEnclosedElements())) {
            if(isPublicField(enclosed)) {
                publicFields.add(enclosed);
            }
        }
        return publicFields;
    }

    public List<Element> nestedClasses(Element element) {
        List<Element> nestedClasses = new ArrayList<Element>();
        for(Element enclosed: element.getEnclosedElements()) {
            if(isClass(enclosed) || isInterface(enclosed)) {
                nestedClasses.add(enclosed);
            }
        }
        return nestedClasses;
    }

    public boolean isGetter(Element element) {
        return Commons.isGetterName(fieldName(element))
                && element instanceof ExecutableElement
                && element.getModifiers().contains(Modifier.PUBLIC)
                && !((ExecutableElement) element).getReturnType().getKind().equals(TypeKind.VOID)
                && ((ExecutableElement) element).getParameters().isEmpty();
    }

    public boolean isSetter(Element element) {
        return Commons.isSetterName(fieldName(element))
                && element.asType().getKind().equals(TypeKind.EXECUTABLE)
                && element.getModifiers().contains(Modifier.PUBLIC)
                && ((ExecutableElement) element).getReturnType().getKind().equals(TypeKind.VOID)
                && ((ExecutableElement) element).getParameters().size() == 1;
    }

    public List<ExecutableElement> getters(Element element) {
        List<ExecutableElement> getters = new ArrayList<>();
        for(ExecutableElement enclosed: ElementFilter.methodsIn(element.getEnclosedElements())) {
            if(isGetter(enclosed)) getters.add(enclosed);
        }
        return getters;
    }

    public List<ExecutableElement> setters(Element element) {
        List<ExecutableElement> setters = new ArrayList<>();
        for(ExecutableElement enclosed: ElementFilter.methodsIn(element.getEnclosedElements())) {
            if(isSetter(enclosed)) setters.add(enclosed);
        }
        return setters;
    }

    public boolean sameTypeAsEither(TypeMirror typeMirror, TypeMirror ... typeMirrors) {
        if(!nullOrEmpty(typeMirrors)) {
            for (TypeMirror otherTypeMirror : typeMirrors) {
                if(types.isSameType(typeMirror, otherTypeMirror))
                    return true;
            }
        }
        return false;
    }

    private TypeMirror typeForClass(Class clazz) {
        // FIXME: Returns null for primitive types
        if(clazz.isPrimitive()){
            return typeForPrimitive(clazz);
        } else {
            return elements.getTypeElement(clazz.getCanonicalName()).asType();
        }
    }

    private TypeMirror typeForPrimitive(Class clazz) {
        if(!clazz.isPrimitive()) return typeForClass(clazz);
        String canonicalName = clazz.getCanonicalName();

        if(canonicalName.equals(byte.class.getCanonicalName()))
            return BYTE_PRIMITIVE_TYPE;
        else if(canonicalName.equals(boolean.class.getCanonicalName()))
            return BOOLEAN_PRIMITIVE_TYPE;
        else if(canonicalName.equals(char.class.getCanonicalName()))
            return CHAR_PRIMITIVE_TYPE;
        else if(canonicalName.equals(short.class.getCanonicalName()))
            return SHORT_PRIMITIVE_TYPE;
        else if(canonicalName.equals(int.class.getCanonicalName()))
            return INT_PRIMITIVE_TYPE;
        else if(canonicalName.equals(long.class.getCanonicalName()))
            return LONG_PRIMITIVE_TYPE;
        else if(canonicalName.equals(float.class.getCanonicalName()))
            return FLOAT_PRIMITIVE_TYPE;
        else if(canonicalName.equals(double.class.getCanonicalName()))
            return DOUBLE_PRIMITIVE_TYPE;
        else
            return null;
    }
}
