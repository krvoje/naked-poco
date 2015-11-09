package org.nakedpojo.parser;

import static java.util.Arrays.asList;

import org.nakedpojo.interfaces.Parser;
import org.nakedpojo.model.javascript.JSType;
import org.nakedpojo.model.javascript.Type;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.*;

import static org.nakedpojo.utils.ReflectionUtils.*;
import static org.nakedpojo.utils.Commons.*;

public class ReflectionsParser implements Parser<Class, JSType>
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
        scan(clazz);
        return prototypes.get(clazz).withName(fieldName);
    }

    public void scan(Class clazz) {
        if(!prototypes.containsKey(clazz))
            prototypes.put(clazz, new JSType(clazz.getSimpleName()));
        else
            return;
        JSType jsType = prototypes.get(clazz);

        List<JSType> members = new ArrayList<JSType>();

        if(either(clazz.isPrimitive(), clazz.equals(String.class))) {
            prototypes.put(clazz, convertPrimitive(clazz));
        }
        else if(clazz.isEnum()) {
            // TODO: Handle better
            for(Object enumConstant: clazz.getEnumConstants())
                members.add(new JSType(clazz.getSimpleName(), enumConstant.toString(), Type.OBJECT));

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

            for(Class field : nestedClasses(clazz)) {
                scan(field);
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
            return new JSType(clazz.getSimpleName(), Type.BOOLEAN);
        }
        else if(equalsEither(clazz,
                java.lang.Character.class,
                char.class,
                String.class)) {
            return new JSType(clazz.getSimpleName(), Type.STRING);
        }
        else if(equalsEither(clazz,
                java.lang.Byte.class,
                byte.class)){
            // TODO: implement
            return new JSType(clazz.getSimpleName(), Type.UNDEFINED);
        }
        else if(isNumeric(clazz)) {
            return new JSType(clazz.getSimpleName(), Type.NUMBER);
        }
        else {
            return new JSType(clazz.getSimpleName(), Type.UNDEFINED);
        }
    }

    private List<Class> nestedClasses(Class clazz) {
        return asList(clazz.getClasses());
    }

    public Map<Class, JSType> prototypes() {
        return this.prototypes;
    }
}
