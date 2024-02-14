package com.upwork.expense_tracker.validation;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.*;

/**
 * The string has to be equal to one of the transaction types.
 * */
@Documented
@Constraint(validatedBy = TransactionTypeValidator.class)
@Target({ ElementType.FIELD })
@Retention(RetentionPolicy.RUNTIME)
public @interface TransactionType {
    String message() default "The type should be one of [earned, spent]";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}
