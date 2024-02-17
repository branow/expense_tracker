package com.upwork.expense_tracker.exception;

/**
 * {@code AuthorizationHeaderParsingException} is a runtime exception that should be thrown when there is not
 * a required authorization header or the header can not be parsed.
 */
public class AuthorizationHeaderParsingException extends RuntimeException {

    public AuthorizationHeaderParsingException() {
        this("", null);
    }

    public AuthorizationHeaderParsingException(String message) {
        this(message, null);
    }

    public AuthorizationHeaderParsingException(String message, Throwable cause) {
        super("Illegal Authorization header: " + message, cause);
    }

    public AuthorizationHeaderParsingException(Throwable cause) {
        super(cause);
    }

}
