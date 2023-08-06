package ru.objectsfill.types.interfaces;

import ru.objectsfill.object_param.Fill;

import java.util.stream.Stream;

public interface BoxCollectionFillExtension {

    /**
     * Fills a stream with generated values based on the provided fill parameters.
     *
     * @param fill the `Fill` object containing the generation parameters
     * @return a stream of filled values
     */
    Stream<Object> fillStream(Fill fill);

}