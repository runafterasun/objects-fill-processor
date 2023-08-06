package ru.objectsfill.types.box_type;

import ru.objectsfill.object_param.Fill;
import ru.objectsfill.utils.RandomGenerator;

import java.math.BigDecimal;
import java.util.stream.Stream;

/**
 * The `BigDecimalFill` class implements the `BoxTypeFill` interface to generate `BigDecimal` values.
 */
public class BigDecimalFill implements BoxTypeFill {
    /**
     * Generates a `BigDecimal` value based on the provided `Fill` object.
     *
     * @param fill the `Fill` object containing the generation parameters
     * @return the generated `BigDecimal` value
     */
    @Override
    public Object generate(Fill fill) {
        return BigDecimal.valueOf(Long.parseLong(RandomGenerator.randomNum(fill)));
    }
    /**
     * Creates a stream of `BigDecimal` values based on the provided `Fill` object.
     *
     * @param fill the `Fill` object containing the generation parameters
     * @return a stream of `BigDecimal` values
     */
    @Override
    public Stream<Object> fillStream(Fill fill) {
        return createStreamWithVal.apply(fill).apply(generate(fill));
    }

}
