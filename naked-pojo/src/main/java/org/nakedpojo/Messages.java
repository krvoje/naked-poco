package org.nakedpojo;

import javax.lang.model.element.Element;

public class Messages {

    public static String unexpectedElementKind(Element element) {
        return String.format(
                "Unexpected element kind. The element: '%s' should not be a %s",
                element, element.getKind().toString());
    }

    public static String elementIsNull() {
        return "Element is null.";
    }
}
