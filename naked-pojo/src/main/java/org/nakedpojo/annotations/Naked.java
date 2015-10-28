package org.nakedpojo.annotations;

public @interface Naked {

    /**
     * The FQCN of this annotation, for convenience
     */
    public static final String NAME = "org.nakedpojo.annotations.Naked";

    /**
     * The name of the target type in the generated code
     */
    String targetTypeName() default "";
}
