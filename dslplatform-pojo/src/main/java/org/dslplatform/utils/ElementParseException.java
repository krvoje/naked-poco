package org.dslplatform.utils;

public class ElementParseException extends RuntimeException {

    public ElementParseException(String message) {
        super(message);
    }

    public ElementParseException(Throwable cause) {
        super(cause);
    }

    public ElementParseException(String message, Throwable cause) {
        super(message, cause);
    }

}
