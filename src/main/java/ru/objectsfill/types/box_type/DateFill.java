package ru.objectsfill.types.box_type;

import ru.objectsfill.object_param.Fill;

import java.util.stream.Stream;

import static ru.objectsfill.utils.RandomGenerator.generateRandomDate;

/**
 * The `DateFill` class implements the `BoxTypeFill` interface to generate `Date` values.
 */
public class DateFill implements BoxTypeFill {

    /**
     * Generates a random `Date` value.
     *
     * @param fill the `Fill` object containing the generation parameters
     * @return the generated `Date` value
     */
    @Override
    public Object generate(Fill fill) {
        return generateRandomDate();
    }

    /**
     * Creates a stream of `Date` values based on the provided `Fill` object.
     *
     * @param fill the `Fill` object containing the generation parameters
     * @return a stream of `Date` values
     */
    @Override
    public Stream<Object> fillStream(Fill fill) {
        return createStreamWithVal.apply(fill).apply(generate(fill));
    }
}
