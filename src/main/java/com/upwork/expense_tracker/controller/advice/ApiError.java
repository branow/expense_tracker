package com.upwork.expense_tracker.controller.advice;

import com.fasterxml.jackson.annotation.JsonFormat;
import org.springframework.http.HttpStatus;

import java.time.LocalDateTime;
import java.util.Objects;

/**
 * The class aims to wrap thrown exceptions into more user-friendly forms and match
 * them with the HTTP response status codes.
 */
public class ApiError {

    /**
     * The Http Response Status that should be return to user.
     */
    private final HttpStatus status;
    /**
     * The timestamp to fixate when exception was thrown.
     */
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy hh:mm:ss")
    private final LocalDateTime timestamp;
    /**
     * The user-friendly message that describe why error/exception was thrown.
     */
    private final String message;
    /**
     * The debug message that is got from the thrown exception.
     */
    private final String debugMessage;


    /**
     * @see #ApiError(HttpStatus, String, Throwable)
     * */
    public ApiError(HttpStatus status, Throwable cause) {
        this(status, "Unexpected error", cause);
    }

    /**
     * @param status  The Http Response Status
     * @param message The user-friendly message that describe why error/exception was thrown
     * @param cause   The exception that cause this error
     */
    public ApiError(HttpStatus status, String message, Throwable cause) {
        this.status = status;
        this.timestamp = LocalDateTime.now();
        this.message = message;
        this.debugMessage = cause.getLocalizedMessage();
    }


    public HttpStatus getStatus() {
        return status;
    }

    public LocalDateTime getTimestamp() {
        return timestamp;
    }

    public String getMessage() {
        return message;
    }

    public String getDebugMessage() {
        return debugMessage;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ApiError apiError = (ApiError) o;
        return status == apiError.status && Objects.equals(timestamp, apiError.timestamp) &&
                Objects.equals(message, apiError.message) &&
                Objects.equals(debugMessage, apiError.debugMessage);
    }

    @Override
    public int hashCode() {
        return Objects.hash(status, timestamp, message, debugMessage);
    }

    @Override
    public String toString() {
        return "ApiError{" +
                "status=" + status +
                ", timestamp=" + timestamp +
                ", message='" + message + '\'' +
                ", debugMessage='" + debugMessage + '\'' +
                '}';
    }
}
