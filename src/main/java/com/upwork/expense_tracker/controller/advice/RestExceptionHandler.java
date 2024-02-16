package com.upwork.expense_tracker.controller.advice;

import com.upwork.expense_tracker.exception.*;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.util.stream.Collectors;

/**
 * The class handles thrown exceptions and returns appropriating response entities.
 */
@ControllerAdvice
public class RestExceptionHandler extends ResponseEntityExceptionHandler {


    @ExceptionHandler(TokenRefreshException.class)
    protected ResponseEntity<Object> handleTokenRefresh(TokenRefreshException ex) {
        String error = ex.getCauseMessage();
        return buildResponseEntityForbidden(ex, error);
    }

    @ExceptionHandler(AuthorizationHeaderParsingException.class)
    protected ResponseEntity<Object> handleAuthorizationHeaderParsing(AuthorizationHeaderParsingException ex) {
        String error = "Illegal format of authorization header.";
        return buildResponseEntityBadRequest(ex, error);
    }

    @ExceptionHandler(IllegalEntityAccessException.class)
    protected ResponseEntity<Object> handleIllegalEntityAccess(IllegalEntityAccessException ex) {
        String error = "You cannot perform this operation on this resource.";
        return buildResponseEntityForbidden(ex, error);
    }

    @ExceptionHandler(EntityNotFoundException.class)
    protected ResponseEntity<Object> handleEntityNotFound(EntityNotFoundException ex) {
        String error = String.format("%s with such %s not found: %s",
                ex.getEntityClass().getSimpleName(), ex.getFieldName(), ex.getFieldValue().toString());
        return buildResponseEntityBadRequest(ex, error);
    }

    @ExceptionHandler(EntityAlreadyExistsException.class)
    protected ResponseEntity<Object> handleEntityAlreadyExists(EntityAlreadyExistsException ex) {
        String error = String.format("%s with the same %s already exists: %s",
                ex.getEntityClass().getSimpleName(), ex.getFieldName(), ex.getFieldValue().toString());
        return buildResponseEntityBadRequest(ex, error);
    }

    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex,
                                                                  HttpHeaders headers,
                                                                  HttpStatusCode status,
                                                                  WebRequest request) {
        //gets error messages about all invalid fields and joins them to one
        String error = ex.getBindingResult()
                .getFieldErrors()
                .stream()
                .map(FieldError::getDefaultMessage)
                .collect(Collectors.joining(" "));
        return buildResponseEntityBadRequest(ex, error);
    }

    @Override
    protected ResponseEntity<Object> handleHttpMessageNotReadable(HttpMessageNotReadableException ex,
                                                                  HttpHeaders headers,
                                                                  HttpStatusCode status,
                                                                  WebRequest request) {
        String error = "Malformed JSON request";
        return buildResponseEntityBadRequest(ex, error);
    }

    private ResponseEntity<Object> buildResponseEntityBadRequest(Exception ex, String message) {
        return buildResponseEntity(new ApiError(HttpStatus.BAD_REQUEST, message, ex));
    }

    private ResponseEntity<Object> buildResponseEntityForbidden(Exception ex, String message) {
        return buildResponseEntity(new ApiError(HttpStatus.FORBIDDEN, message, ex));
    }

    private ResponseEntity<Object> buildResponseEntity(ApiError apiError) {
        return new ResponseEntity<>(apiError, apiError.getStatus());
    }

}
