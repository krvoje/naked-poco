package org.nakedpojo;

import javax.lang.model.element.Element;

public class Messages {

    public static String unexpectedElementKind(Element element) {
        return String.format(
                "Unexpected element kind. Should be a class, actually is a %s",
                element.getKind().toString());
    }

    public static String elementIsNull() {
        return "Element is null.";
    }
}
