package org.nakedpojo.configuration;

/**
 * The strategy for target namespace clashes in generated code.
 */
public enum NameClashDetectionStrategy {

    // TODO: Take AMD modules into account

    /**
     * Ignore clashes, just throw a warning.
     */
    IGNORE,

    /**
     * Try to correct the clash automatically.
     */
    AUTOCORRECT,

    /**
     * On namespace clash, throw an exception.
     */
    FAIL;
}