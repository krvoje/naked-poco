package org.nakedpojo;

public class NakedParseException extends RuntimeException {

    public NakedParseException(String message) {
        super(message);
    }

    public NakedParseException(Throwable cause) {
        super(cause);
    }

    public NakedParseException(String message, Throwable cause) {
        super(message, cause);
    }

}
