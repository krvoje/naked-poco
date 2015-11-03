package org.nakedpojo;

import org.nakedpojo.javascript.JSType;

public interface Parser<T> {
    public JSType convert(T clazz);
    public JSType convert(T clazz, String fieldName);
}
