package com.upwork.expense_tracker.exception;

/**
 * {@code IllegalEntityAccessException} is a runtime exception that should be thrown if the user
 * want to perform the operation on an entity that does not belong to him, or he does not have
 * the required access.
 */
public class IllegalEntityAccessException extends RuntimeException {

    private static final String MESSAGE =
            "Access Not Allowed: %s do not have access to perform the operation on %s";

    public IllegalEntityAccessException(String subject, String object) {
        super(String.format(MESSAGE, subject, object));
    }

}
