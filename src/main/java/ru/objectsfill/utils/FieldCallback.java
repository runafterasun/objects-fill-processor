package ru.objectsfill.utils;

import java.lang.reflect.Field;

/**
 A necessary interface for reflection to traverse fields.
 */
@FunctionalInterface
public interface FieldCallback {
    /**
     * Performs an operation on the given field.
     *
     * @param field the field on which the operation is performed
     * @throws IllegalArgumentException  if the argument is illegal or inappropriate
     * @throws IllegalAccessException  if access to the field is denied
     */
    void doWith(Field field) throws IllegalArgumentException, IllegalAccessException;
}

