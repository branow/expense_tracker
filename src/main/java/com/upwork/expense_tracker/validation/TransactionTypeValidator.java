package com.upwork.expense_tracker.validation;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

import java.util.List;

/**
 * The validator checks whether the string equals one of the transaction types.
 * */
public class TransactionTypeValidator implements ConstraintValidator<TransactionType, String> {

    public static final List<String> TYPES = List.of("earned", "spent");

    @Override
    public void initialize(TransactionType constraintAnnotation) {
        ConstraintValidator.super.initialize(constraintAnnotation);
    }

    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        return TYPES.contains(value);
    }

}
