package org.nakedpojo.annotations;

public @interface Naked {

    /**
     * The FQCN of this annotation, for convenience
     */
    public static final String NAME = "org.nakedpojo.annotations.Naked";

    /**
     * The fieldName of the target type in the generated code
     */
    // TODO: implement this
    String targetTypeName() default "";

    String templateFilename() default "";

    TemplateType templateType() default TemplateType.DEFAULT;
}
