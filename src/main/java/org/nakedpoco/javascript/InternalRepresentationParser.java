package org.nakedpoco.javascript;

import org.nakedpoco.javascript.JSObj;
import org.nakedpoco.javascript.Type;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

import static org.nakedpoco.utils.Utils.equalsEither;

public class InternalRepresentationParser
{

    private static final String GET = "get";
    private static final String SET = "set";
    private static final String IS = "is";

    public JSObj convert(Class clazz) {
        Package pakage = clazz.getPackage();
        String name = clazz.getName();
        return convertField(clazz, name);
    }

    private JSObj convertField(Class clazz, String fieldName) {
        // TODO: Normalize package path
        // TODO: Arrays
        // TODO: Cyclic references
        // TODO: Constructor elements
        List<JSObj> members = new ArrayList<JSObj>();

        if(clazz.isPrimitive() || clazz.equals(String.class)) {
            return convertPrimitive(clazz, fieldName);
        } else {
            Method[] setters = setters(clazz);

            for(Method getter: getters(clazz)) {
                members.add(convertField(getter.getReturnType(), fieldName(getter)));
            }

            for(Field publicField: publicFields(clazz)) {
                members.add(convertField(publicField.getType(), publicField.getName()));
            }

            return new JSObj(fieldName, Type.OBJECT, members.toArray(new JSObj[members.size()]));
        }

    }

    private static JSObj convertPrimitive(Class clazz, String name) {
        if(clazz.equals(java.lang.Boolean.class)) {
            return new JSObj(name, Type.BOOLEAN);
        }
        else if(clazz.equals(java.lang.Character.class)
                || clazz.equals(String.class)) {
            return new JSObj(name, Type.STRING);
        }
        // TODO: later if(clazz.equals(java.lang.Byte.class));
        else if(equalsEither(clazz,
                java.lang.Short.class,
                java.lang.Integer.class,
                java.lang.Long.class,
                java.lang.Float.class,
                java.lang.Double.class)) {
            return new JSObj(name, Type.NUMBER);
        }
        else {
            return new JSObj(name, Type.NUMBER);
        }
    }

    private static Field[] publicFields(Class clazz) {
        Field[] fields = clazz.getFields();
        List<Field> fs = new ArrayList<Field>();
        for(Field field: fields) {
                fs.add(field);
        }
        return fs.toArray(new Field[fs.size()]);
    }

    private static Method[] getters(Class clazz) {
        List<Method> getters = new ArrayList<Method>();
        for(Method method: clazz.getDeclaredMethods()) {
            if(isGetter(method))
                getters.add(method);
        }
        return getters.toArray(new Method[getters.size()]);
    }

    private static Method[] setters(Class clazz) {
        List<Method> setters = new ArrayList<Method>();
        for(Method method: clazz.getDeclaredMethods()) {
            if(isSetter(method))
                setters.add(method);
        }
        return setters.toArray(new Method[setters.size()]);
    }

    private static String fieldName(Method getterOrSetter) {
        String name = getterOrSetter.getName();
        if(name.startsWith(GET)) {
            return lowercaseFirst(name.substring(GET.length()));
        } else if(name.startsWith(IS)) {
            return lowercaseFirst(name.substring(IS.length()));
        } else if(name.startsWith(SET)) {
            return lowercaseFirst(name.substring(SET.length()));
        } else {
            return name;
        }
    }

    private static boolean isGetter(Method method) {
        String name = method.getName();
        return (name.startsWith(GET) || name.startsWith(IS))
                && !method.getReturnType().equals(Void.class)
                && !name.equals(GET) && !name.equals(IS);
    }

    private static boolean isSetter(Method method) {
        String name = method.getName();
        return name.startsWith(SET)
                && method.getReturnType().equals(Void.class)
                && !name.equals(SET);
    }

    private static String lowercaseFirst(String string) {
        // TODO: Checks
        return string.charAt(0) + string.substring(1);
    }
}
