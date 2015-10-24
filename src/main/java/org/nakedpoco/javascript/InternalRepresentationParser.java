package org.nakedpoco.javascript;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.*;

import static org.nakedpoco.javascript.Utils.*;
import static org.nakedpoco.javascript.ReflectionUtils.*;

public class InternalRepresentationParser
{
    // TODO: Normalize package path
    // TODO: Constructor elements
    // TODO: Generics

    public final Map<Class, JSType> prototypes = new HashMap<Class, JSType>();

    public JSType convert(Class clazz) {
        String fieldName = clazz.getSimpleName();
        return convert(clazz, fieldName);
    }

    public JSType convert(Class clazz, String fieldName) {
        scanHierarchy(clazz);
        return prototypes.get(clazz).withName(fieldName);
    }

    private void scanHierarchy(Class clazz) {
        if(!prototypes.containsKey(clazz))
            prototypes.put(clazz, new JSType(clazz));
        else
            return;
        JSType jsType = prototypes.get(clazz);

        List<JSType> members = new ArrayList<JSType>();

        if(either(clazz.isPrimitive(), clazz.equals(String.class))) {
            // Nothing to do here, already done in initialization
            prototypes.put(clazz, convertPrimitive(clazz));
        }
        else if(clazz.isEnum()) {
            // TODO: Handle better
            for(Object enumConstant: clazz.getEnumConstants())
                members.add(new JSType(clazz, enumConstant.toString(), Type.OBJECT));

            prototypes.put(clazz,
                    jsType
                        .withType(Type.ENUM)
                        .withMembers(members.toArray(new JSType[members.size()])));
        }
        else if(clazz.isArray() || isSubclassOf(clazz, Iterable.class)) {
            // TODO: fix, this renders differently than it should
            prototypes.put(clazz, jsType.withType(Type.ARRAY));
        }
        else {
            for(Method getter: getters(clazz)) {
                Class getterClazz = getter.getReturnType();
                members.add(convert(getterClazz, fieldName(getter)));
            }

            for(Field field : publicFields(clazz)) {
                Class fieldType = field.getType();
                members.add(convert(fieldType, field.getName()));
            }

            prototypes.put(clazz, jsType
                    .withType(Type.OBJECT)
                    .withMembers(members));
        }
    }

    private static JSType convertPrimitive(Class clazz) {
        if(equalsEither(clazz,
                java.lang.Boolean.class,
                boolean.class)) {
            return new JSType(clazz, clazz.getSimpleName(), Type.BOOLEAN);
        }
        else if(equalsEither(clazz,
                java.lang.Character.class,
                char.class,
                String.class)) {
            return new JSType(clazz, clazz.getSimpleName(), Type.STRING);
        }
        else if(equalsEither(clazz,
                java.lang.Byte.class,
                byte.class)){
            // TODO: implement
            return new JSType(clazz, clazz.getSimpleName(), Type.UNDEFINED);
        }
        else if(equalsEither(clazz,
                java.lang.Short.class,
                short.class,
                java.lang.Integer.class,
                int.class,
                java.lang.Long.class,
                long.class,
                java.lang.Float.class,
                float.class,
                java.lang.Double.class,
                double.class)) {
            return new JSType(clazz, clazz.getSimpleName(), Type.NUMBER);
        }
        else {
            return new JSType(clazz, clazz.getSimpleName(), Type.UNDEFINED);
        }
    }
}
