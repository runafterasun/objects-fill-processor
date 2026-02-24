package ru.objectsfill.functions;

/**
 * A two-argument functional interface that performs an operation and returns no result.
 * Used for field-level operations that may require reflective access.
 *
 * @param <T> the type of the first argument
 * @param <R> the type of the second argument
 */
@FunctionalInterface
public interface BinaryFunction<T, R> {

    /**
     * Applies this function to the given arguments.
     *
     * @param t the first argument
     * @param r the second argument
     * @throws IllegalAccessException if reflective field access is denied
     */
    void apply(T t, R r) throws IllegalAccessException;
}
