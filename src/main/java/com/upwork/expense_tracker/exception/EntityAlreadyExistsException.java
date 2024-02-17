package com.upwork.expense_tracker.exception;

import java.util.Objects;

/**
 * {@code EntityAlreadyExistsException} is a runtime exception that should be thrown when the user tries
 * to create or add an entity with the field that already belongs to another one.
 * */
public class EntityAlreadyExistsException extends EntityOperationException {

    private static final String MESSAGE = "%s creation exception: there can not be two "
            + "entities with the same %s = %s";


    public EntityAlreadyExistsException(Class<?> entityClass, String fieldName, Object fieldValue) {
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
        EntityAlreadyExistsException that = (EntityAlreadyExistsException) o;
        return Objects.equals(entityClass, that.entityClass) && Objects.equals(fieldName, that.fieldName) && Objects.equals(fieldValue, that.fieldValue);
    }

    @Override
    public String toString() {
        return "EntityAlreadyExistsException{" +
                "entityClass=" + entityClass +
                ", fieldName='" + fieldName + '\'' +
                ", fieldValue=" + fieldValue +
                '}';
    }

}
