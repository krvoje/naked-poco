package org.nakedpojo.javascript;

import javax.lang.model.element.Element;

class Messages {
    static String unexpectedElementKind(Element element) {
        return String.format(
                "Unexpected element kind. Should be a class, actually is a %s",
                element.getKind().toString());
    }
    static String elementIsNull() {
        return "Element is null.";
    }
}
