package org.nakedpojo.interfaces;

import java.util.Map;

public interface Parser<SOURCE_TYPE,METADATA_TYPE> {
    public METADATA_TYPE convert(SOURCE_TYPE clazz);
    public METADATA_TYPE convert(SOURCE_TYPE clazz, String fieldName);
    public void scan(SOURCE_TYPE clazz);
    public Map<SOURCE_TYPE, METADATA_TYPE> prototypes();
}
