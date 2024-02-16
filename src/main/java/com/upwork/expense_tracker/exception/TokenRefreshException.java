package com.upwork.expense_tracker.exception;

import java.util.Objects;

/**
 * {@code TokenRefreshException} is a runtime exception class that should be used
 * to handle any errors or exceptions that occur during this token refresh process.
 */
public class TokenRefreshException extends RuntimeException {

    private static final String MESSAGE = "Failed for [%s]: %s";

    protected final String causeMessage;
    protected final String token;


    public TokenRefreshException(String causeMessage, String token) {
        super(String.format(MESSAGE, token, causeMessage));
        this.causeMessage = causeMessage;
        this.token = token;
    }

    public String getCauseMessage() {
        return causeMessage;
    }

    public String getToken() {
        return token;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        TokenRefreshException that = (TokenRefreshException) o;
        return Objects.equals(causeMessage, that.causeMessage) && Objects.equals(token, that.token);
    }

    @Override
    public int hashCode() {
        return Objects.hash(causeMessage, token);
    }

    @Override
    public String toString() {
        return "TokenRefreshException{" +
                "causeMessage='" + causeMessage + '\'' +
                ", token='" + token + '\'' +
                '}';
    }
}
