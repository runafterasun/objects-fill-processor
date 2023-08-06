package ru.objectsfill.types.box_type;

import ru.objectsfill.object_param.Fill;

import java.util.stream.Stream;

/**
 * The `BooleanFill` class implements the `BoxTypeFill` interface to generate `Boolean` values.
 */
public class BooleanFill implements BoxTypeFill {

    /**
     * Generates a random `Boolean` value.
     *
     * @param fill the `Fill` object containing the generation parameters
     * @return the generated `Boolean` value
     */
    @Override
    public Object generate(Fill fill) {
        return Math.random() < 0.5;
    }

    /**
     * Creates a stream of `Boolean` values based on the provided `Fill` object.
     *
     * @param fill the `Fill` object containing the generation parameters
     * @return a stream of `Boolean` values
     */
    @Override
    public Stream<Object> fillStream(Fill fill) {
        return createStreamWithVal.apply(fill).apply(generate(fill));
    }
}
