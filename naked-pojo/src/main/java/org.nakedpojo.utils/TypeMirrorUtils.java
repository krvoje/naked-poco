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

import static org.nakedpojo.utils.Commons.fieldNameFromGetterOrSetter;
import static org.nakedpojo.utils.Commons.nullOrEmpty;

public class TypeMirrorUtils {

    private final Types types;
    private final Elements elements;
    private final Messager messager;

    // Quazi-constants, since this cannot be a static util class
    private final TypeMirror STRING_TYPE;
    private final TypeMirror OBJECT_TYPE;
    private final TypeMirror CLASS_TYPE;
    private final TypeMirror ENUM_TYPE;

    private final TypeMirror SHORT_TYPE;
    private final TypeMirror INT_TYPE;
    private final TypeMirror LONG_TYPE;
    private final TypeMirror FLOAT_TYPE;
    private final TypeMirror DOUBLE_TYPE;

    private final TypeMirror SHORT_PRIMITIVE_TYPE;
    private final TypeMirror INT_PRIMITIVE_TYPE;
    private final TypeMirror LONG_PRIMITIVE_TYPE;
    private final TypeMirror FLOAT_PRIMITIVE_TYPE;
    private final TypeMirror DOUBLE_PRIMITIVE_TYPE;

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

        this.STRING_TYPE = typeForClass(java.lang.String.class);
        this.OBJECT_TYPE = typeForClass(java.lang.Object.class);
        this.CLASS_TYPE = typeForClass(java.lang.Class.class);
        this.ENUM_TYPE = typeForClass(java.lang.Enum.class);

        this.SHORT_TYPE = typeForClass(java.lang.Short.class);
        this.INT_TYPE = typeForClass(java.lang.Integer.class);
        this.LONG_TYPE = typeForClass(java.lang.Long.class);
        this.FLOAT_TYPE = typeForClass(java.lang.Float.class);
        this.DOUBLE_TYPE = typeForClass(java.lang.Double.class);

        this.SHORT_PRIMITIVE_TYPE = types.getPrimitiveType(TypeKind.SHORT);
        this.INT_PRIMITIVE_TYPE = types.getPrimitiveType(TypeKind.INT);
        this.LONG_PRIMITIVE_TYPE = types.getPrimitiveType(TypeKind.LONG);
        this.FLOAT_PRIMITIVE_TYPE = types.getPrimitiveType(TypeKind.FLOAT);
        this.DOUBLE_PRIMITIVE_TYPE = types.getPrimitiveType(TypeKind.DOUBLE);
    }

    public boolean isNumeric(Element element) {
        return sameTypeAsEither(element.asType(),
                SHORT_PRIMITIVE_TYPE,
                INT_PRIMITIVE_TYPE,
                LONG_PRIMITIVE_TYPE,
                FLOAT_PRIMITIVE_TYPE,
                DOUBLE_PRIMITIVE_TYPE,
                SHORT_TYPE,
                INT_TYPE,
                LONG_TYPE,
                FLOAT_TYPE,
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
                || types.isSameType(element.asType(), STRING_TYPE);
    }

    public boolean isBoolean(Element element) {
        return element.asType().getKind().equals(TypeKind.BOOLEAN);
    }

    public boolean isByte(Element element) {
        return element.asType().getKind().equals(TypeKind.BYTE);
    }

    public boolean isEnum(Element element) {
        if(element.getKind().equals(ElementKind.ENUM)) return true;
        for(Element st : supertypeElements(element)) {
            if(st == null || st.asType() == null) continue;
            if(types.isSameType(st.asType(), ENUM_TYPE)) return true;
        }
        return false;
    }

    public boolean isIterable(Element element) {
        TypeElement list = elements.getTypeElement(java.lang.Iterable.class.getCanonicalName());
        return element.asType().getKind().equals(TypeKind.ARRAY)
                || types.isSubtype(element.asType(), types.getDeclaredType(list));
    }

    public boolean isClass(Element element) {
        return element.getKind().isClass()
            || element.getKind().isInterface();
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
        for(TypeMirror tm: types.directSupertypes(element.asType())) {
            // Upon encountering Object, stop (this will ignore interfaces)
            if(types.isSameType(tm, OBJECT_TYPE))
                break;
            Element te = types.asElement(tm);
            if(te != null && !te.getKind().equals(ElementKind.INTERFACE))
                supertypeElements.add(te);
        }
        return supertypeElements;
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

    public boolean isGetter(Element element) {
        return Commons.isGetterName(fieldName(element))
                && element.asType().getKind().equals(TypeKind.EXECUTABLE)
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
        for(Element enclosed: ElementFilter.methodsIn(element.getEnclosedElements())) {
            if(isGetter(enclosed)) getters.add((ExecutableElement)enclosed);
        }
        return getters;
    }

    public List<ExecutableElement> setters(Element element) {
        List<ExecutableElement> setters = new ArrayList<>();
        for(Element enclosed: ElementFilter.methodsIn(element.getEnclosedElements())) {
            if(isSetter(enclosed)) setters.add((ExecutableElement)enclosed);
        }
        return setters;
    }

    public boolean sameTypeAsEither(TypeMirror tm, TypeMirror ... tms) {
        if(!nullOrEmpty(tms)) {
            for (TypeMirror tm_ : tms) {
                if(types.isSameType(tm, tm_))
                    return true;
            }
        }
        return false;
    }

    private TypeMirror typeForClass(Class clazz) {
        // FIXME: Returns null for primitive types
        return elements.getTypeElement(clazz.getCanonicalName()).asType();
    }
}
