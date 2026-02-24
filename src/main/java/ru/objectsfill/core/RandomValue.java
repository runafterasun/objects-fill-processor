package ru.objectsfill.core;

import ru.objectsfill.object_param.Fill;
import ru.objectsfill.service.ElementCreationService;
import ru.objectsfill.types.array.FillArray;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.function.UnaryOperator;
import java.util.stream.Stream;

import static ru.objectsfill.utils.FieldUtils.doWithFields;
import static ru.objectsfill.utils.FieldUtils.getObjectUnaryOperator;

/**
 * Main entry point for populating POJO classes with random data.
 * Provides static methods for filling single objects, collections, arrays, and streams.
 *
 * <p>Basic usage:
 * <pre>{@code
 * MyClass obj = RandomValue.fill(MyClass.class);
 * }</pre>
 *
 * <p>Advanced usage with {@link Fill} builder:
 * <pre>{@code
 * MyClass obj = RandomValue.fill(
 *     Fill.object(MyClass.class)
 *         .collectionSize(10)
 *         .valueLength(15)
 *         .gen());
 * }</pre>
 */
public final class RandomValue {

    private RandomValue() {
        throw new IllegalStateException("Utility class");
    }

    /**
     * Fills the given object with random values using the provided configuration.
     *
     * @param fill the configuration object describing the target object and generation parameters
     * @param <T>  the type of the target object
     * @return the filled object
     */
    @SuppressWarnings("unchecked")
    public static <T> T fill(Fill fill) {
        doWithFields(fill);
        return ((T) fill.getObjectz());
    }

    /**
     * Fills the given object instance with random values using default parameters.
     *
     * @param object the object instance to fill
     * @param <T>    the type of the target object
     * @return the filled object
     */
    public static <T> T fill(Object object) {
        Fill gen = Fill.object(object).gen();
       return fill(gen);
    }

    /**
     * Creates a new instance of the given class and fills it with random values using default parameters.
     *
     * @param clazz the class to instantiate and fill
     * @param <T>   the type of the target object
     * @return the filled object
     */
    public static <T> T fill(Class<?> clazz) {
        Fill gen = Fill.object(clazz).gen();
        return fill(gen);
    }

    /**
     * Populates the given collection with randomly generated elements.
     * The number of elements is determined by {@link Fill#getCollectionSize()}.
     * If a mutation function is configured via {@link ru.objectsfill.object_param.Extend#wrapByFunction},
     * each generated element is transformed through it before being added.
     *
     * @param collection the collection to populate
     * @param fill       the configuration describing the element type and generation parameters
     * @param <T>        the collection type
     * @param <K>        the type of elements in the collection
     */
    @SuppressWarnings({"unchecked", "unused"})
    public static <T extends Collection<K>, K> void fillCollection(T collection, Fill fill) {

        UnaryOperator<Object> mutationFunction = getObjectUnaryOperator(fill);

        for (int i = 0; i < fill.getCollectionSize(); i++) {
            Object o = mutationFunction.apply(new ElementCreationService().generateSingleValue(fill.getClazz(), fill));

            if (fill.getClazz().isAssignableFrom(o.getClass())) {
                collection.add((K) o);
            }
        }
    }

    /**
     * Creates a stream of randomly generated elements.
     * The stream size is determined by {@link Fill#getCollectionSize()}.
     * If a mutation function is configured, each element is transformed through it.
     *
     * @param fill the configuration describing the element type and generation parameters
     * @param <K>  the type of stream elements
     * @return a finite stream of randomly generated elements
     */
    @SuppressWarnings({"unchecked", "unused"})
    public static <K> Stream<K> fillStream(Fill fill) {

        UnaryOperator<Object> mutationFunction = getObjectUnaryOperator(fill);
        List<K> listForStream = new ArrayList<>();

        for (int i = 0; i < fill.getCollectionSize(); i++) {
            Object o = mutationFunction.apply(new ElementCreationService().generateSingleValue(fill.getClazz(), fill));

            if (fill.getClazz().isAssignableFrom(o.getClass())) {
                listForStream.add((K) o);
            }
        }
        return listForStream.stream();
    }

    /**
     * Creates and fills an array with randomly generated elements.
     * The array size is determined by {@link Fill#getCollectionSize()}.
     *
     * @param fill the configuration describing the element type and generation parameters
     * @param <T>  the component type of the array
     * @return the filled array
     */
    @SuppressWarnings({"unchecked", "unused"})
    public static <T> T[] fillArray(Fill fill) {
        return (T[]) new FillArray().createArray(fill.getClazz(), fill);
    }

    /**
     * Generates a single random value of the type specified in the {@link Fill} configuration.
     * If a mutation function is configured, the generated value is transformed through it.
     *
     * @param fill the configuration describing the value type and generation parameters
     * @param <K>  the type of the generated value
     * @return the generated value
     */
    @SuppressWarnings({"unchecked", "unused"})
    public static <K> K fillSingleVal(Fill fill) {
        UnaryOperator<Object> mutationFunction = getObjectUnaryOperator(fill);
        Object k = mutationFunction.apply(new ElementCreationService().generateSingleValue(fill.getClazz(), fill));
        return (K) k;
    }


}
