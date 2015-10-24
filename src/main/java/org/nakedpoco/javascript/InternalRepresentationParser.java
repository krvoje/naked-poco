package org.nakedpoco.javascript;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.*;

import static org.nakedpoco.utils.Utils.*;
import static org.nakedpoco.utils.ReflectionUtils.*;

public class InternalRepresentationParser
{
    // TODO: Normalize package path
    // TODO: Arrays
    // TODO: Cyclic references
    // TODO: Constructor elements
    // TODO: Generics

    public final Map<Class, JSClass> mappings = new HashMap<Class, JSClass>();
    private final Set<Class> encountered = new HashSet<Class>();

    public JSClass convert(Class clazz) {
        String name = clazz.getSimpleName();
        return convertField(clazz, name);
    }

    public JSClass convertField(Class clazz, String fieldName) {
        if(mappings.containsKey(clazz))
            return new JSClass(mappings.get(clazz), fieldName);

        List<JSClass> members = new ArrayList<JSClass>();
        encountered.add(clazz);

        if(clazz.isPrimitive() || clazz.equals(String.class)) {
            JSClass primitive = convertPrimitive(clazz, fieldName);
            mappings.put(clazz, primitive);
            return primitive;
        }
        else if(clazz.isEnum()) {
            // TODO: Handle better
            for(Object enumConstant: clazz.getEnumConstants())
                members.add(new JSClass(clazz.getSimpleName(), enumConstant.toString(), Type.OBJECT));

            JSClass _enum = new JSClass(clazz.getSimpleName(), fieldName, Type.OBJECT, members.toArray(new JSClass[members.size()]));
            mappings.put(clazz, _enum);
            return _enum;
        }
        else if(clazz.isArray() || clazz.isInstance(Iterable.class)) {
            JSClass arr = new JSClass(clazz.getSimpleName(), fieldName, Type.ARRAY);
            mappings.put(clazz, arr);
            return arr;
        }
        else {
            for(Method getter: getters(clazz)) {
                Class getterClazz = getter.getReturnType();
                if(encountered(getterClazz)) continue;
                members.add(convertField(getterClazz, fieldName(getter)));
            }

            for(Field field : publicFields(clazz)) {
                Class fieldType = field.getType();
                if(encountered(fieldType)) continue;
                members.add(convertField(fieldType, field.getName()));
            }

            JSClass type = new JSClass(clazz.getSimpleName(), fieldName, Type.OBJECT, members.toArray(new JSClass[members.size()]));
            mappings.put(clazz, type);
            return type;
        }

    }

    private JSClass convertPrimitive(Class clazz, String name) {
        if(equalsEither(clazz,
                java.lang.Boolean.class,
                boolean.class)) {
            return new JSClass(clazz.getSimpleName(), name, Type.BOOLEAN);
        }
        else if(equalsEither(clazz,
                java.lang.Character.class,
                char.class,
                String.class)) {
            return new JSClass(clazz.getSimpleName(), name, Type.STRING);
        }
        else if(equalsEither(clazz,
                java.lang.Byte.class,
                byte.class)){
            // TODO: implement
            return new JSClass(clazz.getSimpleName(), name, Type.UNDEFINED);
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
            return new JSClass(clazz.getSimpleName(), name, Type.NUMBER);
        }
        else {
            return new JSClass(clazz.getSimpleName(), name, Type.UNDEFINED);
        }
    }

    private boolean encountered(Class clazz) {
        return encountered.contains(clazz) || mappings.containsKey(clazz);
    }
}
