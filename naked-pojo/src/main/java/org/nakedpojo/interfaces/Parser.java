package org.nakedpojo.interfaces;

import java.util.Map;

public interface Parser<SOURCE_TYPE,METADATA_TYPE> {
    METADATA_TYPE convert(SOURCE_TYPE clazz);
    METADATA_TYPE convert(SOURCE_TYPE clazz, String fieldName);
    void scan(SOURCE_TYPE clazz);
    Map<SOURCE_TYPE, METADATA_TYPE> prototypes();
}
