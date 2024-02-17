package com.upwork.expense_tracker.exception;

import java.util.Objects;

/**
 * {@code EntityAlreadyExistsException} is a runtime exception that should be thrown when the user tries
 * to update a not existing entity or provide any other operation.
 * */
public class EntityNotFoundException extends EntityOperationException {

    private static final String MESSAGE = "There is not %s with the %s equals %s";


    public EntityNotFoundException(Class<?> entityClass, String fieldName, Object fieldValue) {
        super(
                String.format(MESSAGE, entityClass.getName(), fieldName, fieldValue.toString()),
                entityClass,
                fieldName,
                fieldValue
        );
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        EntityNotFoundException that = (EntityNotFoundException) o;
        return Objects.equals(entityClass, that.entityClass) && Objects.equals(fieldName, that.fieldName) && Objects.equals(fieldValue, that.fieldValue);
    }

    @Override
    public String toString() {
        return "EntityNotFoundException{" +
                "entityClass=" + entityClass +
                ", fieldName='" + fieldName + '\'' +
                ", fieldValue=" + fieldValue +
                '}';
    }

}
