package org.nakedpojo.model;

import org.nakedpojo.annotations.Naked;
import org.nakedpojo.annotations.TemplateType;

@Naked
public class PrimitiveFieldsOnly {
    public Short shortNullable;
    public Integer integerNullable;
    public Long longNullable;
    public Double doubleNullable;
    public Float floatNullable;

    public short shortPrimitive;
    public int integerPrimitive;
    public long longPrimitive;
    public double doublePrimitive;
    public float floatPrimitive;
}
