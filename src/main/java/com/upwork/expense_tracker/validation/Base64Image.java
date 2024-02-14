package com.upwork.expense_tracker.validation;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.*;

/**
 * The string has to be a base64 image.
 * */
@Documented
@Constraint(validatedBy = Base64ImageValidator.class)
@Target({ ElementType.FIELD })
@Retention(RetentionPolicy.RUNTIME)
public @interface Base64Image {
    String message() default "The string must be base 64 image.";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}
