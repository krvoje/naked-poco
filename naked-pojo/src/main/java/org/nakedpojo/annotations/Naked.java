package org.nakedpojo.annotations;

import org.nakedpojo.configuration.NameClashDetectionStrategy;
import org.nakedpojo.configuration.TemplateType;

public @interface Naked {

    public static final String NAME = "org.nakedpojo.annotations.Naked";

    String targetTypeName() default "";

    String templateFilename() default "";

    TemplateType templateType() default TemplateType.DEFAULT;

    NameClashDetectionStrategy nameClashDetectionStrategy() default NameClashDetectionStrategy.FAIL;
}
