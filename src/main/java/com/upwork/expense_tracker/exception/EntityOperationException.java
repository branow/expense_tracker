package com.upwork.expense_tracker.exception;

import java.util.Objects;

/**
 * {@code EntityOperationException} is a runtime exception that should be thrown when some
 * illegal operation is going to be done on the entity. <br/>
 * It is recommended to use descendants of that class:
 * @see EntityAlreadyExistsException
 * @see EntityNotFoundException
 * */
public class EntityOperationException extends RuntimeException {

    protected final Class<?> entityClass;
    protected final String fieldName;
    protected final Object fieldValue;

    public EntityOperationException(String message, Class<?> entityClass, String fieldName, Object fieldValue) {
        super(message);
        this.entityClass = entityClass;
        this.fieldName = fieldName;
        this.fieldValue = fieldValue;
    }

    public Class<?> getEntityClass() {
        return entityClass;
    }

    public String getFieldName() {
        return fieldName;
    }

    public Object getFieldValue() {
        return fieldValue;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        EntityOperationException that = (EntityOperationException) o;
        return Objects.equals(entityClass, that.entityClass) && Objects.equals(fieldName, that.fieldName) && Objects.equals(fieldValue, that.fieldValue);
    }

    @Override
    public int hashCode() {
        return Objects.hash(entityClass, fieldName, fieldValue);
    }

    @Override
    public String toString() {
        return "EntityOperationException{" +
                "entityClass=" + entityClass +
                ", fieldName='" + fieldName + '\'' +
                ", fieldValue='" + fieldValue + '\'' +
                '}';
    }

}
