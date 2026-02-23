package ru.objectsfill.types.interfaces;

import ru.objectsfill.object_param.Fill;

import java.util.stream.Stream;

/**
 * Extension interface providing stream-based generation for complex object types.
 * Implemented by {@link ru.objectsfill.types.object_type.ObjectTypeFill}.
 */
public interface ObjectCollectionFillExtension {

    /**
     * Generates a stream of objects for the given collection generic type and fill parameters.
     *
     * @param collectionGenericType the generic type of the collection
     * @param fill                  the fill parameters for object generation
     * @return a stream of objects for the collection
     */
    Stream<Object> fillStream(Class<?> collectionGenericType, Fill fill);
}