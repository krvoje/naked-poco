package org.dslplatform.utils;

import org.dslplatform.mirror.types.DslPrimitive;
import org.nakedpojo.utils.TypeMirrorUtils;

import javax.annotation.processing.Messager;
import javax.lang.model.element.Element;
import javax.lang.model.util.Elements;
import javax.lang.model.util.Types;

public class DslPlatformTypeMirrorUtils extends TypeMirrorUtils {

    public DslPlatformTypeMirrorUtils(Types types, Elements elements, Messager messager) {
        super(types, elements, messager);
    }



}
