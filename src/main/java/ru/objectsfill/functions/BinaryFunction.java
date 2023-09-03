package ru.objectsfill.functions;

/**
 * binary operator function
 */
@FunctionalInterface
public interface BinaryFunction<T, R> {

    /**
     * binary operator function
     * @param t first argument
     * @param r second argument
     * @throws IllegalAccessException add ex for function
     */
    void apply(T t, R r) throws IllegalAccessException;
}
